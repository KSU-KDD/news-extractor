/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the  "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.emje.xtcp.reader;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.NodeList;

/**
 *
 * @author shadiq
 */
public class ConfResult {

    // Ada tiga jenis Object yang mungkin disini :
    // Jenis Pertama adalah jenis String biasa (Untuk Continous), yang artinya satu field hanya memiliki satu buah nilai, yaitu string
    // Jenis kedua adalah ArrayList (Untuk Segmented), yang artinya satu field memiliki berbagai jenis Object
    // Jenis ketiga adalah jenis TreeMap (Untuk Outlink) dengan URL sebagai Key dan Anchor sebagai Value

    // Jenis Kedua dapat diperluas lagi, Jika object pada ArrayList tersebut adalah : 
    // String biasa,
    // ArrayList of String (Untuk Nodeset) dan
    // TreeMap (Untuk Outlink)

    private XPathEvaluator xPathEvaluator;
    private String configName;

    public ConfResult(XPathEvaluator xPathEvaluator){
        this.xPathEvaluator = xPathEvaluator;
    }

    public Map<String, Object> getResult(Config config){

        this.configName = config.getName();
        Map<String, Object> fieldMap = new TreeMap();

        ArrayList<Field> fields = config.getFields();
        for (Field field : fields) {


            ArrayList<Entry> entries = field.getEntries();

            if (field.getFieldType().equals(Field.CONTINUOUS_TEXT)) {
                StringBuilder sb = new StringBuilder();
                for (Entry entry : entries) {
                    sb.append((String) evaluateXPath(xPathEvaluator, entry.getxPathExpression(), entry.getExpressionType(),
                                field.getFieldType(), field.getEntryDelimiter()));
                }

                if(sb.length() != 0){
                    sb.delete(sb.length() - field.getEntryDelimiter().length(), sb.length()); // remove the last delimiter
                    fieldMap.put(field.getFieldName(), sb.toString().trim());
                }

            } else if (field.getFieldType().equals(Field.SEGMENTED_TEXT)) {
                ArrayList al = new ArrayList();
                for (Entry entry : entries) {
                    al.add(evaluateXPath(xPathEvaluator, entry.getxPathExpression(), entry.getExpressionType(),
                                field.getFieldType(), field.getEntryDelimiter()));
                }
                fieldMap.put(field.getFieldName(), al);

            } else if (field.getFieldType().equals(Field.OUTLINKS)) {
                Map<String, String> outlinkMap = new TreeMap();
                for (Entry entry : entries) {
                    //temporary map for later merged with the outlinkMap
                    Map<String, String> tempMap = new TreeMap();
                    tempMap = (Map<String, String>) evaluateXPath(xPathEvaluator, entry.getxPathExpression(), entry.getExpressionType(),
                            field.getFieldType(), field.getEntryDelimiter()); // get the outlinkMap of this entry
                    //iterati through all the tempMap and merge it into outlinkMap
                    Iterator tempMapIterator = tempMap.keySet().iterator();
                    while (tempMapIterator.hasNext()) {
                        String term = (String) tempMapIterator.next();
                        outlinkMap.put(term, tempMap.get(term));
                    }
                }
                fieldMap.put(field.getFieldName(), outlinkMap);
            }
        }

        return fieldMap;
    }

    private Object evaluateXPath(XPathEvaluator xPathEvaluator, String expression,
            String expressionType, String fieldType, String delimiter){

        // Evaluate XPath Expression with different returnType
        String XPathExpression = expression;
        QName returnType = null;
        Object xPathResult = null;
        Object xPathResultOutlinkAnchor = null;
        // <editor-fold defaultstate="collapsed" desc="Switch XPath Expression Return Type">
        try{
            if (expressionType.equals("xpath.string")) {
                returnType = XPathConstants.STRING;
                xPathResult = xPathEvaluator.evaluateXPathExpression(XPathExpression, returnType);
            } else if (expressionType.equals("xpath.node")) {
                returnType = XPathConstants.NODE;
                xPathResult = xPathEvaluator.evaluateXPathExpression(XPathExpression, returnType);
            } else if (expressionType.equals("xpath.nodeset")) {
                returnType = XPathConstants.NODESET;
                xPathResult = xPathEvaluator.evaluateXPathExpression(XPathExpression, returnType);
            } else if (expressionType.equals("xpath.number")) {
                returnType = XPathConstants.NUMBER;
                xPathResult = xPathEvaluator.evaluateXPathExpression(XPathExpression, returnType);
            } else if (expressionType.equals("xpath.boolean")) {
                returnType = XPathConstants.BOOLEAN;
                xPathResult = xPathEvaluator.evaluateXPathExpression(XPathExpression, returnType);
            } else if (expressionType.equals("xpath.string.normalized")) {
                returnType = XPathConstants.STRING;
                xPathResult = xPathEvaluator.evaluateXPathExpression("normalize-space(" + XPathExpression + ")", returnType);
            } else if (expressionType.equals("xpath.outlink.nodeset")) {
                returnType = XPathConstants.NODESET;
                if (XPathExpression.endsWith("/@href")) {
                    XPathExpression = XPathExpression.substring(0, XPathExpression.length() - 6);
                }
                xPathResult = xPathEvaluator.evaluateXPathExpression(XPathExpression + "/@href", returnType);
                xPathResultOutlinkAnchor = xPathEvaluator.evaluateXPathExpression(XPathExpression + "/text()", returnType);
            } else if (expressionType.equals("plain.string")) {
                xPathResult = expression;
            }
        } catch (XPathExpressionException ex) {
            Logger.getLogger(ConfResult.class.getName()).log(Level.SEVERE, null, ex);
        }// </editor-fold>
        if (xPathResult != null) {
            // <editor-fold defaultstate="collapsed" desc="XPATH_OUTLINK_NODESET_ENTRY">
            if (expressionType.equals(Entry.XPATH_OUTLINK_NODESET_ENTRY)) {
                NodeList list = (NodeList) xPathResult;

                ArrayList<String> urlArrayList = new ArrayList<String>();
                ArrayList<String> anchorArrayList = new ArrayList<String>();

                // fill data
                if (xPathResultOutlinkAnchor != null) {
                    NodeList anchorList = (NodeList) xPathResultOutlinkAnchor;
                    for (int i = 0; i < list.getLength(); i++) {
                        String value = list.item(i).getNodeValue();
                        String anchorValue = "  ";
                        if (i < anchorList.getLength()) {
                            anchorValue = anchorList.item(i).getNodeValue().isEmpty() ? anchorList.item(i).getNodeValue() : "";

                        }
                        if (!value.matches("\\s+")) {
                            value = list.item(i).getNodeValue().replaceAll("\\s+", " "); // normalize space
                            if (!anchorValue.matches("\\s+")) {
                                anchorValue = anchorList.item(i).getNodeValue().replaceAll("\\s+", " "); // normalize space
                                urlArrayList.add(value);
                                anchorArrayList.add(anchorValue);
                            } else {
                                urlArrayList.add(value);
                                anchorArrayList.add("");
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < list.getLength(); i++) {
                        String value = list.item(i).getNodeValue();
                        if (!value.matches("\\s+")) {
                            value = list.item(i).getNodeValue().replaceAll("\\s+", " "); // normalize space
                            urlArrayList.add(value);
                            anchorArrayList.add("");
                        }
                    }
                }
                Map<String, String> outlinkMap = new TreeMap();
                for (int k = 0; k < urlArrayList.size(); k++) {
                    outlinkMap.put((String) urlArrayList.get(k), (String) anchorArrayList.get(k));
                }
                return outlinkMap;
            }// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="XPATH_NODESET_ENTRY">
            else if (expressionType.equals(Entry.XPATH_NODESET_ENTRY)) {
                NodeList list = (NodeList) xPathResult;
                ArrayList<String> urlArrayList = new ArrayList<String>();

                // fill data
                for (int i = 0; i < list.getLength(); i++) {
                    String value = list.item(i).getNodeValue();
                    if (value != null && !value.matches("\\s+")) {
                        value = list.item(i).getNodeValue().replaceAll("\\s+", " "); // normalize space
                        urlArrayList.add(value);
                    }
                }

                if (fieldType.equals(Field.CONTINUOUS_TEXT)) {
                    StringBuilder esbe = new StringBuilder();
                    for (int k = 0; k < urlArrayList.size(); k++) {
                        esbe.append((String) urlArrayList.get(k)).append(delimiter);
                    }
                    return esbe.toString();
                } else if (fieldType.equals(Field.SEGMENTED_TEXT)) {
                    ArrayList<String> al = new ArrayList();
                    for (int k = 0; k < urlArrayList.size(); k++) {
                        al.add((String) urlArrayList.get(k));
                    }
                    return al;
                }
            }// </editor-fold>
            else {
                if (fieldType.equals(Field.CONTINUOUS_TEXT)) {
                    return xPathResult.toString() + delimiter;
                } else if (fieldType.equals(Field.SEGMENTED_TEXT)) {
                    return xPathResult.toString();
                }
            }
        }

        return null;
    }

    public String getConfigName(){
        return this.configName;
    }
}
