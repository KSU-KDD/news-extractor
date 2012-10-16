package org.wesam.nutch.parse.bbcc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.metadata.Metadata;
import org.apache.nutch.parse.HTMLMetaTags;
import org.apache.nutch.parse.HtmlParseFilter;
import org.apache.nutch.parse.Parse;
import org.apache.nutch.parse.ParseResult;
import org.apache.nutch.protocol.Content;

import org.w3c.dom.DocumentFragment;
import org.apache.log4j.Logger;

public class BbccParseFilter implements HtmlParseFilter {

	public static final String STORY_DATE_KEY = "story_date";
	
	private static final Logger LOG = Logger.getLogger(BbccParseFilter.class);	
	private static final Pattern datePattern = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d");
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
	public ParseResult filter(Content content, ParseResult parseResult,
			HTMLMetaTags metaTags, DocumentFragment doc) {
		LOG.debug("Parsing URL: " + content.getUrl());
	    BufferedReader reader = new BufferedReader(
	      new InputStreamReader(new ByteArrayInputStream(
	      content.getContent())));
	    String line;
	    String storyDate = null;
	    try {
	    while ((line = reader.readLine()) != null) {
	    	// Find story date and time 
	    	if (line.contains("</time>")) {
	    		Matcher m = datePattern.matcher(line);
	    		if (m.find()) {
	    			LOG.debug("Found date " + m.group(1));
	    			storyDate = formatDate(m.group(1));
	    		}
	    	}
	    }
	    reader.close();
	    } catch (IOException e) {
	    	LOG.warn("IOException while reading file: ");
	    }
	    Parse parse = parseResult.get(content.getUrl());
	    Metadata metadata = parse.getData().getParseMeta();
	    
	    // Add fields to metadata
	    metadata.add(STORY_DATE_KEY, storyDate);	    
	    return parseResult;
	}
	
	private String formatDate(String dateTime) {
		Date parseDate = null;
		try {
			parseDate = DateUtils.parseDate(dateTime,
					new String [] {"yyyy-MM-dd'T'HH:mm:ss'Z'"});
		} catch (ParseException e) {
			LOG.warn("can't parse date" + dateTime);
		}
		return parseDate.toString();
	}
}
