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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This class load the xml config file.
 * @author Ammar Shadiq
 * adapted from source created by Rida Benjelloun (rida.benjelloun@doculibre.com) for DOM related code
 */
public class ConfigFileParser {

    private String configURL;
    private Document dom;
    private Config conf;

    public ConfigFileParser() {
        // do nothing
    }

    public ConfigFileParser(String configURL) {
        this.configURL = configURL;
    }

    public void parse(){
        //parse the xml file and get the dom object
        createDomObject();
        if(dom != null){
            parseDocument();

        }
    }

    private void createDomObject(){
        try {
            //get the factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false); // ignore namespace
            factory.setIgnoringElementContentWhitespace(true);
            //Using factory get an instance of document builder
            DocumentBuilder db = factory.newDocumentBuilder();
            //parse using builder to get DOM representation of the XML file
            dom = db.parse(this.configURL);
        } catch (Exception ex) {
            Logger.getLogger(ConfigFileParser.class.getName()).log(Level.SEVERE, "Couldn't create DOM object", ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="DOM Related methods">

    /**
     * I take a xml element and the tag name, look for the tag and get
     * the text content
     * i.e for <employee><name>John</name></employee> xml snippet if
     * the Element points to employee node and tagName is name I will return John
     * @param ele
     * @param tagName
     * @return
     */
    private String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }

        return textVal;
    }

    /**
     * Calls getTextValue and returns a int value
     * @param ele
     * @param tagName
     * @return
     */
    private int getIntValue(Element ele, String tagName) {
        //in production application you would catch the exception
        return Integer.parseInt(getTextValue(ele, tagName));
    }

    /**
     * Calls getTextValue and returns a double value
     * @param ele
     * @param tagName
     * @return
     */
    private double getDoubleValue(Element ele, String tagName) {
        //in production application you would catch the exception
        return Double.parseDouble(getTextValue(ele, tagName));
    }// </editor-fold>

    private void parseDocument() {
        //get the root <config> element
        Element configElement = dom.getDocumentElement();

        Config c = new Config(getTextValue(configElement, "name"),
                getTextValue(configElement, "source"),
                getTextValue(configElement, "host"),
                getTextValue(configElement, "parser"));
        this.conf = c;

        //get nodelist of <replace> elements
        NodeList replaceNL = configElement.getElementsByTagName("replace");
        if (replaceNL != null && replaceNL.getLength() > 0) {
            for(int i = 0; i < replaceNL.getLength(); i++) {
                Element replaceElement = (Element) replaceNL.item(i);
                c.getReplaceDataMap().put(replaceElement.getAttribute("text"), replaceElement.getAttribute("with"));
            }
        }
        //get nodelist of <remove> elements
        NodeList removeNL = configElement.getElementsByTagName("remove");
        if (removeNL != null && removeNL.getLength() > 0) {
            // edit remove configuration on XPathTreeConfigNode class
            c.getRemoveDataList().clear();
            for (int i = 0; i < removeNL.getLength(); i++) {
                Element removeElement = (Element) removeNL.item(i);
                c.getRemoveDataList().add(removeElement.getAttribute("text"));
            }
        }

        //get nodelist of <accepturl> elements
        NodeList acceptURLNL = configElement.getElementsByTagName("accepturl");
        if (acceptURLNL != null && acceptURLNL.getLength() > 0) {
            // edit remove configuration on XPathTreeConfigNode class
            c.getAcceptedURLList().clear();
            for (int i = 0; i < acceptURLNL.getLength(); i++) {
                Element acceptURLElement = (Element) acceptURLNL.item(i);
                c.getAcceptedURLList().add(acceptURLElement.getAttribute("value"));
            }
        }
        //get nodelist of <skipurl> elements
        NodeList skipURLNL = configElement.getElementsByTagName("skipurl");
        if (skipURLNL != null && skipURLNL.getLength() > 0) {
            // edit remove configuration on XPathTreeConfigNode class
            c.getSkippedURLList().clear();
            for (int i = 0; i < skipURLNL.getLength(); i++) {
                Element skipURLElement = (Element) skipURLNL.item(i);
                c.getSkippedURLList().add(skipURLElement.getAttribute("value"));
            }
        }

        //get the <field> elements
        NodeList fieldNL = configElement.getElementsByTagName("field");

        if (fieldNL != null && fieldNL.getLength() > 0) {
            for (int i = 0; i < fieldNL.getLength(); i++) {
                Element fieldElement = (Element) fieldNL.item(i);

                Field f;
                if (fieldElement.getAttribute("type").equalsIgnoreCase(Field.CONTINUOUS_TEXT)){
                    f = new Field(fieldElement.getAttribute("name"),
                        fieldElement.getAttribute("type"),
                        fieldElement.getAttribute("delimiter"));
                } else {
                    f = new Field(fieldElement.getAttribute("name"),
                        fieldElement.getAttribute("type"),
                        "");
                }
                // get nodelist of <entry> elements
                NodeList entryNL = fieldElement.getElementsByTagName("entry");
                if (entryNL != null && entryNL.getLength() > 0) {
                    for (int j = 0; j < entryNL.getLength(); j++) {
                        Element entryElement = (Element) entryNL.item(j);

                        Entry e = new Entry(entryElement.getAttribute("value"), entryElement.getAttribute("type"));

                        f.getEntries().add(e);
                    }
                }
                c.getFields().add(f);
            }
        }
    }

    public String getConfigURL() {
        return configURL;
    }

    public void setConfigURL(String configURL) {
        this.configURL = configURL;
    }

    public Document getDom() {
        return dom;
    }

    public void setDom(Document dom) {
        this.dom = dom;
    }

    public Config getConf() {
        return conf;
    }

    public void setConf(Config conf) {
        this.conf = conf;
    }
}