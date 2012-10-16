/*
 * Copyright (C) 2012 Wesam Elshamy <wesam elshamy gmail com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wesam.nutch.indexer.bbcc;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.log4j.Logger;
import org.apache.nutch.crawl.CrawlDatum;
import org.apache.nutch.crawl.Inlinks;
import org.apache.nutch.indexer.IndexingException;
import org.apache.nutch.indexer.IndexingFilter;
import org.apache.nutch.indexer.NutchDocument;
import org.apache.nutch.parse.Parse;
import org.wesam.nutch.parse.bbcc.BbccParseFilter;

/**
 * 
 * @author Wesam Elshamy
 *
 */
public class BbccIndexingFilter implements IndexingFilter {
	private static final Logger LOG = Logger.getLogger(BbccIndexingFilter.class.getName());
	private Configuration conf;
	
	@Override
	public Configuration getConf() {
		return conf;
	}

	@Override
	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	@Override
	public NutchDocument filter(NutchDocument doc, Parse parse, Text url,
			CrawlDatum datum, Inlinks inlinks) throws IndexingException {
		Text dateString = (Text) datum.getMetaData().get(new Text(BbccParseFilter.STORY_DATE_KEY));
		// Add story date/time to index
		doc.add(BbccParseFilter.STORY_DATE_KEY, formatDate(dateString.toString()));
		return doc;
	}
	
	private Date formatDate(String dateTime) {
		Date parseDate = null;
		try {
			parseDate = DateUtils.parseDate(dateTime,
					new String [] {"EEE, MMM dd HH:mm:ss yyyy zzz"});
		} catch (ParseException e) {
			LOG.warn("can't parse date" + dateTime);
		}
		return parseDate;
	}
}
