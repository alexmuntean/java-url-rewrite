package org.urlrewrite;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class Config {
    public static List<Rule> getRules(InputStream inputStream) {
	final List<Rule> rules = new ArrayList<Rule>();

	SAXParserFactory factory = SAXParserFactory.newInstance();
	try {
	    SAXParser saxParser = factory.newSAXParser();
	    DefaultHandler handler = new DefaultHandler() {
		public void startElement(String uri, String localName,
			String qName, Attributes attributes)
			throws SAXException {
		    if (qName.equals("rewrite")) {
			String fromValue = attributes.getValue("from");
			String toValue = attributes.getValue("to");
			String handlerValue = attributes.getValue("handler");
			String statusValue = attributes.getValue("status");
			if (fromValue != null
				&& (handlerValue != null || toValue != null)) {
			    From from = new From(fromValue);
			    To to = new To(toValue);
			    String status = statusValue;
			    if (status != null) {
				to.setHttpStatus(Integer.parseInt(status));
			    }

			    Rule rule = new Rule();
			    rule.setFrom(from);
			    rule.setTo(to);
			    try {
				rule.setHandler(handlerValue);
				rules.add(rule);
			    } catch (Exception e) {
				e.printStackTrace();
			    }
			}
		    }
		}
	    };
	    saxParser.parse(inputStream, handler);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return rules;
    }
}
