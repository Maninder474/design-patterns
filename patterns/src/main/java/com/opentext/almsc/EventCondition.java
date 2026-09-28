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

import java.util.Collection;
import java.util.HashMap;

/**
 * <code>ZMFEventCondition</code> holds the event condition data. 
 * The data contains the following properties:
 *	Event type
 *	Object type
 *	List of event manager end points
 *
 * In order to provide a matching key to determine which event condition to process, 
 * the entry for event type, object type, product, and product version are hashed together 
 * to provide a unique token.  
 * 
 * @author Manny Panis
 */
public class EventCondition {
	private StringBuffer zmfEventType;

	private StringBuffer zmfObjectType;

	private StringBuffer product;
	
	private StringBuffer productVersion; 

	private StringBuffer productInstance;
	
	private StringBuffer callbackURI;
	
	private StringBuffer eventType;
	
	private StringBuffer objectType;
	
	private HashMap<String, Object> extensions;
	
	public EventCondition() {
		init();
	}

	public EventCondition(String zmfEventType, String zmfObjectType) {
		init();
		setZMFEventType(zmfEventType);
		setZMFObjectType(zmfObjectType);
	}

	public void init() {
		this.zmfEventType = new StringBuffer();
		this.zmfObjectType = new StringBuffer();
		this.product = new StringBuffer();
		this.productVersion = new StringBuffer();
		this.productInstance = new StringBuffer();
		this.callbackURI = new StringBuffer();
		this.eventType = new StringBuffer();
		this.objectType = new StringBuffer();
		this.extensions	= new HashMap<String, Object>();
 	}

	/**
	 * Gets the zmfEventType value for this EventCondition.
	 * 
	 * @return String zmfEventType
	 */
	public String getZMFEventType() {
		return this.zmfEventType.toString();
	}

	/**
	 * Sets the zmfEventType value for this EventCondition.
	 * 
	 * @param String
	 *            zfmEventType
	 */
	public void setZMFEventType(String zmfEventType) {
		if (zmfEventType != null && zmfEventType.length() > 0)
			this.zmfEventType.append(zmfEventType);
	}

	/**
	 * Gets the ZMFObjectType value for this EventCondition.
	 * 
	 * @return String zmfObjectType
	 */
	public String getZMFObjectType() {
		return this.zmfObjectType.toString();
	}

	/**
	 * Sets the ZMFobjectType value for this EventCondition.
	 * 
	 * @param String
	 *            zmfObjectType
	 */
	public void setZMFObjectType(String zmfObjectType) {
		if (zmfObjectType != null && zmfObjectType.length() > 0)
			this.zmfObjectType.append(zmfObjectType);
	}


	/**
	 * Gets the Product value for this EventCondition.
	 * 
	 * @return String product
	 */
	public String getProduct() {
		return product.toString();
	}

	/**
	 * Sets the product value for this EventCondition.
	 * 
	 * @param String
	 *            product
	 */
	public void setProduct(String product) {
		if (product != null && product.length() > 0)
			this.product.append(product);
	}
	
	/**
	 * Gets the Product value for this EventCondition.
	 * 
	 * @return String product
	 */
	public String getProductVersion() {
		return productVersion.toString();
	}

	/**
	 * Sets the product value for this EventCondition.
	 * 
	 * @param String
	 *            product version
	 */
	public void setProductVersion(String version) {
		if (version != null && version.length() > 0)
			this.productVersion.append(version);
	}
	
	/**
	 * Gets the ProductInstance value for this EventCondition.
	 * 
	 * @return String productInstance
	 */
	public String getProductInstance() {
		return productInstance.toString();
	}

	/**
	 * Sets the productInstance value for this EventCondition.
	 * 
	 * @param String
	 *            productInstance
	 */
	public void setProductInstance(String productInstance) {
		if (productInstance != null && productInstance.length() > 0)
			this.productInstance.append(productInstance);
	}
	
	/**
	 * Gets the callbackURI value for this EventCondition.
	 * 
	 * @return String callbackURI
	 */
	public String getCallbackURI() {
		return callbackURI.toString();
	}

	/**
	 * Sets the callbackURI value for this EventCondition.
	 * 
	 * @param String
	 *            callbackURI
	 */
	public void setCallbackURI(String callbackURI) {
		if (callbackURI != null && callbackURI.length() > 0)
			this.callbackURI.append(callbackURI);
	}
	
	/**
	 * Gets the EventType value for this EventCondition.
	 * 
	 * @return String eventType
	 */
	public String getEventType() {
		return this.eventType.toString();
	}

	/**
	 * Sets the EventType value for this EventCondition.
	 * 
	 * @param String
	 *            EventType
	 */
	public void setEventType(String eventType) {
		if (eventType != null && eventType.length() > 0)
			this.eventType.append(eventType);
	}

	/**
	 * Gets the ObjectType value for this EventCondition.
	 * 
	 * @return String ObjectType
	 */
	public String getObjectType() {
		return this.objectType.toString();
	}

	/**
	 * Sets the ObjectType value for this EventCondition.
	 * 
	 * @param String
	 *            ObjectType
	 */
	public void setObjectType(String objectType) {
		if (objectType != null && objectType.length() > 0)
			this.objectType.append(objectType);
	}


	/**
	 * Gets the extensions value for this EventCondition.
	 * 
	 * @return HashMap extensions
	 */
	public HashMap<String, Object> getExtensions() {
		return extensions;
	}

	/**
	 * Sets the extensions value for this EventCondition
	 * 
	 * @param HashMap
	 *            extensions
	 */
	public void setExtensions(HashMap<String, Object> extensions) {
		this.extensions = extensions;
	}

	public void addExtensionEntry(String key, ExtensionData extData) {
		extensions.put(key, (Object) extData);
	}

	public ExtensionData getExtensionEntry(String key) {
	    if (extensions.containsKey((Object) key))
	        return (ExtensionData) extensions.get(key);
	    else 
	        return null;
	}
	
	public int getHashToken() {
		return getHashToken(getZMFEventType(), getZMFObjectType());
	}

	public static int getHashToken(String eventType, String objectType)  {
		TokenGenerator nCrc = new TokenGenerator();
		StringBuffer sBuf = new StringBuffer();
		if (eventType != null && eventType.length() > 0)
			sBuf.append(eventType);
		
		if (objectType != null && objectType.length() > 0)
			sBuf.append(objectType);
		
	
		return nCrc.calculate(sBuf);
	}
	
	public String toString() {
		StringBuffer sbuf = new StringBuffer();
		sbuf.append("zmfEventType=");
		sbuf.append(this.zmfEventType.toString());
		sbuf.append(" zmfObjectType=");
		sbuf.append(this.zmfObjectType.toString());
		sbuf.append(" product=");
		sbuf.append(this.product.toString());
		sbuf.append(" productVersion=");
		sbuf.append(this.productVersion.toString());
		sbuf.append(" productInstance=");
		sbuf.append(this.productInstance.toString());
		sbuf.append(" callbackURI=");
		sbuf.append(this.callbackURI.toString());
		sbuf.append(" eventType=");
		sbuf.append(this.eventType.toString());
		sbuf.append(" objectType=");
		sbuf.append(this.objectType.toString());
		ExtensionData extData = null;
		Collection<Object> values = this.extensions.values();
		for (Object value : values) {
			extData = (ExtensionData) value;
			if (extData.getValue().equalsIgnoreCase("?"))
				continue;
			
			sbuf.append(" " + extData.getKey() + "=" + extData.getValue());
		}
		
		return sbuf.toString();
	}
}
