/*
 * Copyright 2023 Open Text.
 *
 * The only warranties for products and services of Open Text and its affiliates and licensors (“Open Text”)
 * are as may be set forth in the express warranty statements accompanying such products and services.
 * Nothing herein should be construed as constituting an additional warranty. Open Text shall not be liable
 * for technical or editorial errors or omissions contained herein. The information contained herein is subject
 * to change without notice.
 * Except as specifically indicated otherwise, this document contains confidential information and a valid
 * license is required for possession, use or copying. If this work is provided to the U.S. Government,
 * consistent with FAR 12.211 and 12.212, Commercial Computer Software, Computer Software
 * Documentation, and Technical Data for Commercial Items are licensed to the U.S. Government under
 * vendor's standard commercial license.
 */

package com.opentext.almsc;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * <code>EventDefaultHandler</code> ALF Event default schema parser.
 * 
 * @author Manny Panis
 */
public class EventDefaultHandler extends DefaultHandler {

	// ===========================================================
	// SAX DocumentHandler methods
	// ===========================================================

	public void startDocument() throws SAXException {
	}

	public void endDocument() throws SAXException {
	}

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes attrs) throws SAXException {
	}

	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
	}

	public void characters(char buf[], int offset, int len) throws SAXException {
	}

	/**
	 * Strips the namespace from qualified name since some parsers do not
	 * support namespaces
	 */
	public String getLocalNameFromQualified(String qname) {
		String localName;
		if (qname != null) {
			int index = qname.lastIndexOf(':');
			if (index > -1)
				localName = qname.substring(index + 1, qname.length());
			else
				localName = qname;
		} else
			localName = "";
		return localName;
	}

}
