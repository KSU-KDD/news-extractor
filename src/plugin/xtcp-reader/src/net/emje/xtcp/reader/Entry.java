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

/**
 *
 * @author Ammar Shadiq
 */
public class Entry {
    public static final String XPATH_STRING_ENTRY = "xpath.string";
    public static final String XPATH_NORMALIZED_STRING_ENTRY = "xpath.string.normalized";
    public static final String XPATH_NODE_ENTRY = "xpath.node";
    public static final String XPATH_NODESET_ENTRY = "xpath.nodeset";
    public static final String XPATH_NUMBER_ENTRY = "xpath.number";
    public static final String XPATH_BOOLEAN_ENTRY = "xpath.boolean";
    public static final String STRING_ENTRY = "plain.string";
    public static final String XPATH_OUTLINK_NODESET_ENTRY = "xpath.outlink.nodeset";
//    private boolean containsResult;
    private String xPathExpression;
    private String expressionType;
//    private String resultString;
//    private String[][] resultTable;
//    private String[] resultList;

    public Entry() {
        this("", "xpath.string");
    }

    public Entry(String xPathExpression, String expressionType) {
        this.xPathExpression = xPathExpression;
        this.expressionType = expressionType;
    }

    /**
     * Get the xPath expression return type.<br>
     * <ul>
     *  <li> XPATH_STRING_ENTRY : xPath Expression would returns a strings</li>
     *  <li> XPATH_NORMALIZED_STRING_ENTRY : xPath Expression would returns a normalized strings by putting xPath Expression inside normalize-space( ) argument</li>
     *  <li> XPATH_NODE_ENTRY : xPath Expression would returns a XPath 1.0 NodeSet data type</li>
     *  <li> XPATH_NODESET_ENTRY : xPath Expression would returns a XPath 1.0 NodeSet data type</li>
     *  <li> XPATH_NUMBER_ENTRY : xPath Expression would returns a XPath 1.0 Number data type</li>
     *  <li> XPATH_BOOLEAN_ENTRY : xPath Expression would returns a XPath 1.0 Boolean data type</li>
     *  <li> STRING_ENTRY : xPath Expression would simply returns the expression as a plain string</li>
     *  <li> XPATH_OUTLINK_NODESET_ENTRY : xPath Expression would returns a map of URL and Anchor</li>
     * <ul>
     * see {@link javax.xml.xpath.XPathConstants}
     *
     * @return {@code expressionType} the xPath expression return type
     */
    public String getExpressionType() {
        return expressionType;
    }

    /**
     * Set the xPath expression return type.<br>
     * <ul>
     *  <li> XPATH_STRING_ENTRY : xPath Expression would returns a strings</li>
     *  <li> XPATH_NORMALIZED_STRING_ENTRY : xPath Expression would returns a normalized strings by putting xPath Expression inside normalize-space( ) argument</li>
     *  <li> XPATH_NODE_ENTRY : xPath Expression would returns a XPath 1.0 NodeSet data type</li>
     *  <li> XPATH_NODESET_ENTRY : xPath Expression would returns a XPath 1.0 NodeSet data type</li>
     *  <li> XPATH_NUMBER_ENTRY : xPath Expression would returns a XPath 1.0 Number data type</li>
     *  <li> XPATH_BOOLEAN_ENTRY : xPath Expression would returns a XPath 1.0 Boolean data type</li>
     *  <li> STRING_ENTRY : xPath Expression would simply returns the expression as a plain string</li>
     *  <li> XPATH_OUTLINK_NODESET_ENTRY : xPath Expression would returns a map of URL and Anchor</li>
     * <ul>
     * see {@link javax.xml.xpath.XPathConstants}
     * @param {@code expressionType} the xPath expression return type
     */
    public void setExpressionType(String expressionType) {
        this.expressionType = expressionType;
    }

    /**
     * Get the XPath Expression
     * @return {@code xPathExpression} the XPath Expression
     */
    public String getxPathExpression() {
        return xPathExpression;
    }

    /**
     * Set the XPath Expression
     * @param {@code xPathExpression} the XPath Expression
     */
    public void setxPathExpression(String xPathExpression) {
        this.xPathExpression = xPathExpression;
    }
    
    @Override
    public String toString() {
        return this.getxPathExpression();
    }
}
