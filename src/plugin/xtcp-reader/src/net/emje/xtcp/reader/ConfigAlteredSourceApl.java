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

import java.util.Iterator;

/**
 *
 * @author shadiq
 */
public class ConfigAlteredSourceApl {

    /**
     * Remove and replace strings defined in {@codew XPathTreeConfigNode config} from web page source code.
     * @param source : web page source code
     * @param config : XPathTreeConfigNode config
     * @return altered web page source code by remove and replace strings from the specified config.
     */
    public static String alterSource(String source, Config config){

        String alteredSource = source;
        // replace words that included on AlteredSourceStrings configuration
        // replaced strings section
        Iterator replaceMapIterator = config.getReplaceDataMap().keySet().iterator();
        while (replaceMapIterator.hasNext()) {
            String text = (String) replaceMapIterator.next();
            CharSequence csText = text;
            CharSequence csWith = (String) config.getReplaceDataMap().get(text);
            alteredSource = alteredSource.replace(csText, csWith);
        }
        // removed strings section
        Iterator removeMapIterator = config.getRemoveDataList().iterator();
        while(removeMapIterator.hasNext()){
            alteredSource = alteredSource.replaceAll((String) removeMapIterator.next(), "");
        }

        return alteredSource;
    }
}
