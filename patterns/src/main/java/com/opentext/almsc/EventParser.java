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

import java.util.HashSet;

/**
 *  <code>EventParser</code> parses the raised ZMF Event data by ZMF Event Notification subsystem. The data 
 * parsed are loaded inside event data table. The event must have the following XML format:
 * <ZMFEvent version="1.0" xmlns="http://www.serena.com/zmf/XMLSchema/Events">
 *  <EventData>
 *   <EventID/>
 *   <EventDescription/>
 *   <ObjectType/>
 *   <Timestamp/>
 *   <Server>
 *    <ServerName/>
 *    <ServerAddress/>
 *    <ServerPortID/>
 *    <ServerVersion/>
 *   </Server>
 *	 <EventDetails> 
 *     <application/>
 *     <release/>
 *     <package/> 
 *     <site/>
 *     <promotionName/>
 *     <promotionArea/>
 *     <promotionLevel/>
 *     <releaseArea/>
 *     <releaseFromArea/>
 *     <component/>
 *     <libType/>
 *     <userId/>
 *     <jobName/>
 *     <jobNumber/>
 *    <eventStatus>
 *   </EventDetails>
 *  </EventData>
 * </ZMFEvent>
 *
 * The eventID is translated to an associated event type.  
 * 
 * @author Manny Panis
 */
public class EventParser extends EventDefaultHandler implements Constants {

	// XML element ids

	static private int ELEMENT_NONE_ID 	 = -1;
	
	static private int ELEMENT_EVENTDATA_ID  = 1;

	static private int ELEMENT_EVENTID_ID    = 2;

    static private int ELEMENT_EVENTDESC_ID  = 3;
	
	static private int ELEMENT_TIMESTAMP_ID  = 4;

	static private int ELEMENT_OBJECTTYPE_ID = 5;
	
	static private int ELEMENT_SERVERNAME_ID = 6;

	static private int ELEMENT_SERVERADDRESS_ID = 7;

	static private int ELEMENT_SERVERPORTID_ID  = 8;

	static private int ELEMENT_SERVERVERSION_ID = 9;

	static private int ELEMENT_EVENTDETAILS_ID  = 10;

	private int curElement;

	private HashSet<Object> eventDataTable;
	
	private String version;
	
	private EventData eventData = null;
	
	private String eventDetailsQname;
	
	private boolean isEventDetails;
	
//	===========================================================
    // SAX DocumentHandler methods
    //===========================================================

    public void startDocument() throws SAXException {
		eventDataTable = new HashSet<Object>();
		isEventDetails = false;
	}

    public void endDocument() throws SAXException {
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs) throws SAXException {
    	// in case parser does not support namespaces, 
    	// get localname from qname
		curElement = ELEMENT_NONE_ID;
		if (localName == null || localName.length() == 0)
			localName = getLocalNameFromQualified(qName);

		// check if event details or tool extension data indicator is set. If
		// yes, set qname.
		if (isEventDetails) {
			curElement = ELEMENT_EVENTDETAILS_ID;
			eventDetailsQname = localName;
		} else if (localName.equals(TAG_ZMFEVENT))
			version = attrs.getValue(TAG_VERSION);
		else if (localName.equals(TAG_EVENTDATA)) {
			eventData = new EventData();
			curElement = ELEMENT_EVENTDATA_ID;
		} else if (localName.equals(TAG_EVENTID))
			curElement = ELEMENT_EVENTID_ID;
		else if (localName.equals(TAG_EVENTDESC))
			curElement = ELEMENT_EVENTDESC_ID;
		else if (localName.equals(TAG_TIMESTAMP))
			curElement = ELEMENT_TIMESTAMP_ID;
		else if (localName.equals(TAG_OBJECTTYPE))
			curElement = ELEMENT_OBJECTTYPE_ID;
		else if (localName.equals(TAG_SERVERNAME))
			curElement = ELEMENT_SERVERNAME_ID;
		else if (localName.equals(TAG_SERVERADDRESS))
			curElement = ELEMENT_SERVERADDRESS_ID;
		else if (localName.equals(TAG_SERVERPORTID))
			curElement = ELEMENT_SERVERPORTID_ID;
		else if (localName.equals(TAG_SERVERVERSION))
			curElement = ELEMENT_SERVERVERSION_ID;
		else if (localName.equals(TAG_EVENTDETAILS)) 
			isEventDetails = true;
    }

    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		curElement = ELEMENT_NONE_ID;
		// in case parser does not support namespaces,
		// get localname from qname
		if (localName == null || localName.length() == 0)
			localName = getLocalNameFromQualified(qName);

		if (localName.equals(TAG_EVENTDATA)) {
			eventDataTable.add((Object) eventData);
		} else if (localName.equals(TAG_EVENTDETAILS))
			isEventDetails = false;
   	}


    public void characters(char buf[], int offset, int len)  throws SAXException {
		if (curElement > ELEMENT_NONE_ID) {
			String s = new String(buf, offset, len);
			if (isEventDetails)
				eventData.addEventDetail(eventDetailsQname, s);
			else if (curElement == ELEMENT_EVENTID_ID) {
				eventData.setEventID(s);
				eventData.setEventType(EventData.xlateEventType(s));
			}
			else if (curElement == ELEMENT_EVENTDESC_ID)
				eventData.setEventDescription(s);
			else if (curElement == ELEMENT_TIMESTAMP_ID)
				eventData.setTimeStamp(s);
			else if (curElement == ELEMENT_SERVERNAME_ID)
				eventData.setServerName(s);
			else if (curElement == ELEMENT_OBJECTTYPE_ID)
				eventData.setObjectType(s);
			else if (curElement == ELEMENT_SERVERADDRESS_ID)
				eventData.setServerAddress(s);
			else if (curElement == ELEMENT_SERVERPORTID_ID)
				eventData.setServerPortID(s);
			else if (curElement == ELEMENT_SERVERVERSION_ID)
				eventData.setServerVersion(s);
		}
    }
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public HashSet<Object> getEventDataTable() {
		return eventDataTable;
	}
}
