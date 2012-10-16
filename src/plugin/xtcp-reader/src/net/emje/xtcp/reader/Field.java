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

/**
 *
 * @author Ammar Shadiq
 */
public class Field {
    private String fieldName; // CONTINUOUS_TEXT
    public static final String CONTINUOUS_TEXT = "CONTINUOUS_TEXT";
    public static final String SEGMENTED_TEXT = "SEGMENTED_TEXT";
    public static final String OUTLINKS = "OUTLINKS";
    private String fieldType;
    private String entryDelimiter;
    private ArrayList<Entry> entries = new ArrayList<Entry>();

    /**
     * A simple Lucene field adapter<br>
     * Act as an adapter that glue this lib Field object and Lucene Field object
     * @param fieldName: the Lucene Field Name
     */
    public Field(String fieldName) {
        this(
                fieldName,
                CONTINUOUS_TEXT,
                "");
    }
    /**
     * A simple Lucene field adapter<br>
     * Act as an adapter that glue this lib Field object and Lucene Field object
     * @param fieldName: the Lucene Field Name
     * @param fieldType: attribute to describe how the entries of this field would be concatenated/treated
     * @param entryDelimiter: attribute to be used as the delimiter for entries value concatenation process
     */
    public Field(String fieldName, String fieldType, String entryDelimiter){
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        if(fieldType.equalsIgnoreCase(CONTINUOUS_TEXT)) this.entryDelimiter =  entryDelimiter;
        else this.entryDelimiter = "";
    }

    /**
     * Get the name for this field
     * @return {@code fieldName} the name for this field
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Set Name for this field<br>
     * this fieldName would also be the Lucene Field Name
     * @param {@code fieldName} the name for this field
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Get the delimiter for separating each entry value for this field<br>
     * Used as the delimiter for entries value concatenation process
     * @return {@code entryDelimiter} the delimiter for separating each entry value for this field
     */
    public String getEntryDelimiter() {
        return entryDelimiter;
    }

    /**
     * Set the delimiter for separating each entry value for this field<br>
     * Used as the delimiter for entries value concatenation process
     * @param {@code entryDelimiter} the delimiter for separating each entry value for this field
     */
    public void setEntryDelimiter(String entryDelimiter) {
        this.entryDelimiter = entryDelimiter;
    }

    /**
     * Returns Field Type, attribute to describe how the entries of this field would be concatenated/treated, of:
     * <ul>
     *  <li>CONTINUOUS_TEXT : every entry values would be concatenated as a continuous String</li>
     *  <li>SEGMENTED_TEXT : every entry values would be treated as individual strings, not concatenated and therefore not using {@code entryDelimiter}</li>
     *  <li>OUTLINKS : Special type of Lucene outlinks that contains outlinks entry data type of URL and Anchor (not using {@code entryDelimiter})</li>
     * </ul>
     * 
     * @return {@code fieldType} attribute to describe how the entries of this field would be concatenated/treated
     */
    public String getFieldType() {
        return fieldType;
    }

    /**
     * Set Field Type, attribute to describe how the entries of this field would be concatenated/treated, with strings value from variables:
     * <ul>
     *  <li>static String org.emje.nutch.parse.xtcp.Field.CONTINUOUS_TEXT</li>
     *  <li>static String org.emje.nutch.parse.xtcp.Field.SEGMENTED_TEXT</li>
     *  <li>static String org.emje.nutch.parse.xtcp.Field.OUTLINKS</li>
     * </ul>
     * Function for each strings value:
     * <ul>
     *  <li>CONTINUOUS_TEXT : every entry values would be concatenated as a continuous String</li>
     *  <li>SEGMENTED_TEXT : every entry values would be treated as individual strings, not concatenated and therefore not using {@code entryDelimiter}</li>
     *  <li>OUTLINKS : Special type of Lucene outlinks that contains outlinks entry data type of URL and Anchor (not using {@code entryDelimiter})</li>
     * </ul>
     * @param {@code fieldType}  attribute to describe how the entries of this field would be concatenated/treated, advised to use static variable described above
     */
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * Get the list of xtcp Entries specified by this configuration field.<br>
     * This Entries could be used as way to get the xpath value.
     * see {@link Entry}
     * @return {@code entries} the list of xtcp Entries specified by this configuration field
     */
    public ArrayList<Entry> getEntries() {
        return entries;
    }

    /**
     * Set the list of xtcp Entries specified by this configuration field.<br>
     * This Entries could be used as way to get the xpath value.
     * see {@link Entry}
     * @param {@code entries} the list of xtcp Entries specified by this configuration field
     */
    public void setEntries(ArrayList<Entry> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        return this.getFieldName();
    }
}
