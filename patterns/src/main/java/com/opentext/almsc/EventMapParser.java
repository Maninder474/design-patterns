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

import java.util.HashMap;

/**
 * <code>EventMapParser</code> parses the ZMF Event Mapping file. The data 
 * parsed are loaded inside the event mapping table. The event must have the following XML format:
 * <ZMALFEventMap xmlns="http://www.serena.com/zmf/XMLSchema/Events">
 
 * <EventConditions>
 * 	<ZMFEvent>
 * 		<ZMFEventType/>
 * 		<ZMFObjectType/>
 * 	</ZMFEvent>
 * 	<ALFEvent>	   	
 * 		<Product/>
 *      <ProductVersion/>
 * 		<ProductInstance/>
 * 		<CallbackURI/>
 *		<EventType/>
 *		<ObjectType/>
 *		<Extension>
 *			<any>
 *		</Extension>
 * 	</ALFEvent>
 * </EventConditions>
 **
 * ZMF Event router will load the event mapping file at startup or when a reloadEventMap command is issued.
 * The event mapping file will be used to filter the events and to determine the target end point address where to
 * forward the event. When an event is raised from ZMF, the router will search the event mappping table for a
 * matching EventType, ObjectType, Product, and ProductVersion. If a match is found, it will route the event to all
 * the end points specified in the <EventManger> element. All events that are not found will be discarded. 
 * 
 * @author Manny Panis
 */
public class EventMapParser extends EventDefaultHandler implements Constants {

	private HashMap<Object, Object> eventTable;
	
	private int curElement;
	
	private EventCondition eventCondition;
	
	private static int ELEMENT_NONE_ID = -1;
	
	private static int ELEMENT_ZMFEVENTTYPE_ID  = 2;
	
	private static int ELEMENT_ZMFOBJECTTYPE_ID = 3;
	
	private static int ELEMENT_PRODUCT_ID = 4;
	
	private static int ELEMENT_PRODUCTVERSION_ID = 5;
	
	private static int ELEMENT_PRODUCTINSTANCE_ID = 6;
	
	private static int ELEMENT_CALLBACKURI_ID = 7;
	
	private static int ELEMENT_EVENTTYPE_ID  = 8;
	
	private static int ELEMENT_OBJECTTYPE_ID = 9;

	private static int ELEMENT_EXTENSION_ID = 10;

	private static int ELEMENT_EXTENSIONDATA_ID = 11;

	private static boolean bExtension;

	private static String extensionName;

	private static StringBuffer extensionValue;
			
	//===========================================================
    // SAX DocumentHandler methods
    //===========================================================

    public void startDocument() throws SAXException {
    	eventTable = new HashMap<Object, Object>();
		bExtension = false;
    }

    public void endDocument() throws SAXException {
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs) throws SAXException {
    	// in case parser does not support namespaces, 
    	// get localname from qname
    	curElement = ELEMENT_NONE_ID;
    	if (localName == null || localName.length() == 0)
    		localName = getLocalNameFromQualified(qName);

    	if (localName.equals(TAG_EVENTCONDITIONS)) {
    		// create a new eventcondition
    		eventCondition = new EventCondition();
    	} else if (localName.equals(TAG_EXTENSION)) {
    		curElement = ELEMENT_EXTENSION_ID;
		   	bExtension = true;	
		} else if (localName.equals(TAG_ZMFEVENTTYPE))
    		curElement = ELEMENT_ZMFEVENTTYPE_ID;
    	else if (localName.equals(TAG_ZMFOBJECTTYPE))
    		curElement = ELEMENT_ZMFOBJECTTYPE_ID;
    	else if (localName.equals(TAG_PRODUCT)) 
    		curElement = ELEMENT_PRODUCT_ID;  
    	else if (localName.equals(TAG_PRODUCTVERSION)) 
    		curElement = ELEMENT_PRODUCTVERSION_ID;  
    	else if (localName.equals(TAG_PRODUCTINSTANCE)) 
    		curElement = ELEMENT_PRODUCTINSTANCE_ID;
    	else if (localName.equals(TAG_CALLBACKURI)) 
    		curElement = ELEMENT_CALLBACKURI_ID; 
    	else if (localName.equals(TAG_EVENTTYPE))
    		curElement = ELEMENT_EVENTTYPE_ID;
    	else if (localName.equals(TAG_OBJECTTYPE))
    		curElement = ELEMENT_OBJECTTYPE_ID;
		else if (bExtension) {
			curElement = ELEMENT_EXTENSIONDATA_ID;
			extensionName = localName;
			extensionValue = new StringBuffer();
		}
    }

    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
    	curElement = ELEMENT_NONE_ID;
    	// in case parser does not support namespaces, 
    	// get localname from qname
    	if (localName == null || localName.length() == 0)
    		localName = getLocalNameFromQualified(qName);
    	
    	if (localName.equals(TAG_EVENTCONDITIONS)) {
    		int nToken = eventCondition.getHashToken();
    		Integer i = new Integer(nToken);
    		eventTable.put((Object) i.toString(), (Object) eventCondition);
		} else if (localName.equals(extensionName)) {
		    eventCondition.addExtensionEntry(extensionName, new ExtensionData(extensionName, extensionValue.toString()));
    	} else if (localName.equals(TAG_EXTENSION)) {
    		bExtension = false;
    	} 
   	}
    	

    public void characters(char buf[], int offset, int len)  throws SAXException {
    	if (curElement > ELEMENT_NONE_ID) {
    	   	String s = new String(buf, offset, len);
           	if (curElement == ELEMENT_ZMFEVENTTYPE_ID)
           		eventCondition.setZMFEventType(s);
           	else if (curElement == ELEMENT_ZMFOBJECTTYPE_ID)
           		eventCondition.setZMFObjectType(s);
           	else if (curElement == ELEMENT_PRODUCT_ID)
           		eventCondition.setProduct(s);
        	else if (curElement == ELEMENT_PRODUCTVERSION_ID)
           		eventCondition.setProductVersion(s);
        	else if (curElement == ELEMENT_PRODUCTINSTANCE_ID)
           		eventCondition.setProductInstance(s);
        	else if (curElement == ELEMENT_CALLBACKURI_ID)
           		eventCondition.setCallbackURI(s);
           	else if (curElement == ELEMENT_EVENTTYPE_ID)
           		eventCondition.setEventType(s);
           	else if (curElement == ELEMENT_OBJECTTYPE_ID)
           		eventCondition.setObjectType(s);
			else if (curElement == ELEMENT_EXTENSIONDATA_ID)
				extensionValue.append(s);
    	}
     }

	public HashMap<Object, Object> getEventTable() {
		return eventTable;
	}
}
