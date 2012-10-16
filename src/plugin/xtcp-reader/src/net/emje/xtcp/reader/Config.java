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

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Ammar Shadiq
 */
public class Config {

    private String name;
    private String sourceURL;
    private String hostName;
    private String parser;
    private ArrayList<String> removedDataList = new ArrayList<String>();
    private Map<String, String> replaceDataMap = new TreeMap<String, String>();
    private ArrayList<String> acceptedURLList = new ArrayList<String>();
    private ArrayList<String> skippedURLList = new ArrayList<String>();
    private ArrayList<Field> fields = new ArrayList<Field>();

    public Config(String name) {
        this(
            name,
            null,
            null,
            null
        );
    }

    public Config(String name, String hostName) {
        this(
            name,
            hostName,
            null,
            null
        );
    }

    public Config(String name, String hostName, String sourceURL) {
        this(
            name,
            hostName,
            null,
            null
        );
    }

    public Config(String name, String hostName, String sourceURL, String parser) {
        this.name = name;
        this.hostName = hostName;
        this.sourceURL = sourceURL;
        if(parser == null) this.parser = "html2xhtml";
        else this.parser = parser;
    }

    /**
     * Get the configuration name
     * @return {@code name} the configuration name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the configuration name
     * @param {@code name} the configuration name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the config source URL, the web page source URL in which this config is based
     * @return {@code sourceURL} the source URL
     */
    public String getSourceURL() {
        return sourceURL;
    }

    /**
     * Set the config URL, the web page source URL in which this config is based
     * @param {@code sourceURL} the config source URL
     */
    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    /**
     * Set the host name of source URL, the web page source URL host in which this config is based
     * @return {@code hostName} the host name of source URL
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Get the host name of source URL, the web page source URL host in which this config is based
     * @param {@code hostName} the host name of source URL
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * Get Parser name, the parser/converter in which this config is based
     * @return {@code parser} the parser/converter name
     */
    public String getParser(){
        return this.parser;
    }

    /**
     * Set Parser name, the parser/converter in which this config is based.
     * @param {@code parser} the parser/converter name
     */
    public void setParser(String parser) {
        this.parser = parser;
    }

    /**
     * Get the list of removed string specified by this configuration.<br>
     * This removed strings could be used to removed from the source code of the specified web page.<br>
     * <br>
     * This list could be used as a way to remove string of web page code that causes error.
     * @return {@code removedDataList} the list of removed string specified by this configuration.
     */
    public ArrayList<String> getRemoveDataList(){
        return this.removedDataList;
    }

    /**
     * Set the list of removed string specified by this configuration.<br>
     * This removed strings could be used to removed from the source code of the specified web page.<br>
     * <br>
     * This list could be used as a way to remove string of web page code that causes error.
     * @param {@code dataList} the list of removed string specified for this configuration.
     */
    public void setRemovedDataList(ArrayList<String> dataList){
        this.removedDataList = dataList;
    }

    /**
     * Get the Map of {@code Key} and {@code Value} of strings as a map of the text that want to be replaced, and the replacement text.<br>
     * This map of replace strings could be applied to replace the source code of the specified web page with the replacement strings.<br>
     * <br>
     * This Map could be used as a way to replace string of web page code that causes error, by replacing it with the appropriate string of web page code.
     * @return {@code replaceDataMap} The Map of {@code Key} and {@code Value} of strings as a map of the text that want to be replaced, and the replacement text
     */
    public Map<String, String> getReplaceDataMap(){
        return this.replaceDataMap;
    }

    /**
     * Set the Map of {@code Key} and {@code Value} of strings as a map of the text that want to be replaced, and the replacement text.<br>
     * This map of replace strings could be applied to replace the source code of the specified web page with the replacement strings.<br>
     * <br>
     * This Map could be used as a way to replace string of web page code that causes error, by replacing it with the appropriate string of web page code.
     * @param {@code dataMap} The Map of {@code Key} and {@code Value} of strings as a map of the text that want to be replaced, and the replacement text
     */
    public void setReplaceDataMap(Map<String, String> dataMap){
        this.replaceDataMap = dataMap;
    }

    /**
     * Get the list of Accepted URL regex string specified by this configuration.<br>
     * This Accepted URL regex string could be used as a filter to only accept certain URL for this configuration
     * @return {@code acceptedURLList} the list of Accepted URL regex string specified by this configuration
     */
    public ArrayList<String> getAcceptedURLList(){
        return this.acceptedURLList;
    }

    /**
     * Set the list of Accepted URL regex string specified by this configuration.<br>
     * This Accepted URL regex string could be used as a filter to only accept certain URL for this configuration
     * @param {@code dataList} the list of Accepted URL regex string specified for this configuration
     */
    public void setAcceptedURLList(ArrayList<String> dataList){
        this.acceptedURLList = dataList;
    }

    /**
     * Get the list of Skipped URL regex string specified by this configuration.<br>
     * This Skipped URL regex string could be used as a filter to skip certain URL for this configuration
     * @return {@code skippedURLList} the list of Skipped URL regex string specified by this configuration
     */
    public ArrayList<String> getSkippedURLList(){
        return this.skippedURLList;
    }

    /**
     * Set the list of Skipped URL regex string specified by this configuration.<br>
     * This Skipped URL regex string could be used as a filter to skip certain URL for this configuration
     * @param {@code dataList} the list of Skipped URL regex string specified by this configuration
     */
    public void setSkippedURLList(ArrayList<String> dataList){
        this.skippedURLList = dataList;
    }

    /**
     * Get the list of xtcp Fields specified by this configuration.<br>
     * This Fields could be used as way to get the entry string by various way:
     * <ul>
     *  <li> as a string if the field type are CONTINUOUS_TEXT</li>
     *  <li> as a list of value Object if the field type are SEGMENTED_TEXT</li>
     *  <li> as a map of URL and Anchor string if the field type are OUTLINKS</li>
     * </ul>
     * see {@link Field}
     * @return {@code fields} the list of xtcp Fields specified by this configuration
     */
    public ArrayList<Field> getFields() {
        return fields;
    }

    /**
     * Set the list of xtcp Fields specified by this configuration.<br>
     * This Fields could be used as way to get the entry string by various way:
     * <ul>
     *  <li> as a string if the field type are CONTINUOUS_TEXT</li>
     *  <li> as a list of value Object if the field type are SEGMENTED_TEXT</li>
     *  <li> as a map of URL and Anchor string if the field type are OUTLINKS</li>
     * </ul>
     * see {@link Field}
     * @param {@code fields} the list of xtcp Fields specified by this configuration
     */
    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return this.getName();
    }
    
}
