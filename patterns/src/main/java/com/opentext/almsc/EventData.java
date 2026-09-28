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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * <code>EventData</code> holds the parsed ZMF event data container. 
 * 
 * @author Manny Panis
 */
public class EventData implements Constants {

	private StringBuffer eventID;
	
	private StringBuffer eventType;
	
	private StringBuffer eventDescription;
	
	private StringBuffer timeStamp;

    private StringBuffer objectType;
		
	private StringBuffer serverName;
	
	private StringBuffer serverAddress;
	
	private StringBuffer serverPortID;
	
	private StringBuffer serverVersion;
	
	private HashMap<Object, String> eventDetailsTable;
	
	private static final HashMap<Object, Object> eventIDXlateTable = new HashMap<Object, Object>(50);
	 
    static {
		// load event ID translation table.
	   	eventIDXlateTable.put((Object)"80", (Object) "Create");                                            // build or create package
	   	eventIDXlateTable.put((Object)"40", (Object) "Freeze");                                     	   // freeze package
	   	eventIDXlateTable.put((Object)"20", (Object) "Approve");                                   	       // approve package
	   	eventIDXlateTable.put((Object)"30", (Object) "Reject");                                            // reject package
	   	eventIDXlateTable.put((Object)"10", (Object) "Revert");                                            // revert package   
	   	eventIDXlateTable.put((Object)"08", (Object) "Delete");                                            // delete package
		eventIDXlateTable.put((Object)"13", (Object) "MemoDelete");                                        // memo delete package
		eventIDXlateTable.put((Object)"08", (Object) "Undelete");                                          // undelete package
	   	eventIDXlateTable.put((Object)"48", (Object) "Promote");                                           // promote package
	   	eventIDXlateTable.put((Object)"44", (Object) "Demote");                                            // demote package
	   	eventIDXlateTable.put((Object)"50", (Object) "Audit");                                             // audit package
	   	eventIDXlateTable.put((Object)"57", (Object) "Abend");                                             // abend
	   	eventIDXlateTable.put((Object)"58", (Object) "Notify");                                            // notify
	   	eventIDXlateTable.put((Object)"04", (Object) "Distribute");                                        // distribute package
	   	eventIDXlateTable.put((Object)"02", (Object) "Install");                                           // install package
	   	eventIDXlateTable.put((Object)"15", (Object) "BaselineRipple");                                    // baseline ripple package
	   	eventIDXlateTable.put((Object)"01", (Object) "BackOut");                                           // backout package
	   	eventIDXlateTable.put((Object)"16", (Object) "BaselineReverseRipple");                             // baseline reverse ripple package
	   	eventIDXlateTable.put((Object)"70", (Object) "FTStart");                                           // file tailoring started
	   	eventIDXlateTable.put((Object)"71", (Object) "FTFail");                                            // file tailoring failed
	   	eventIDXlateTable.put((Object)"72", (Object) "FTComplete");                                        // file tailoring completed
	   	eventIDXlateTable.put((Object)"03", (Object) "TemporaryChangeCycle");                              // temporary change package cycle
	   	eventIDXlateTable.put((Object)"23", (Object) "BackoutRelease");                                    // backout release
	   	eventIDXlateTable.put((Object)"24", (Object) "InstallRelease");                                    // install release
		eventIDXlateTable.put((Object)"25", (Object) "DistributeRelease");                                 // distribute release
		eventIDXlateTable.put((Object)"26", (Object) "DeleteRelease");                                     // delete release
		eventIDXlateTable.put((Object)"27", (Object) "RevertRelease");                                     // revert release
		eventIDXlateTable.put((Object)"28", (Object) "ApproveRelease");                                    // approve release
		eventIDXlateTable.put((Object)"29", (Object) "RejectRelease");                                     // reject release
		eventIDXlateTable.put((Object)"31", (Object) "MemoDeleteRelease");                                 // memo delete release
		eventIDXlateTable.put((Object)"32", (Object) "UndeleteRelease");                                   // undelete release                             
		eventIDXlateTable.put((Object)"33", (Object) "BaselineRelease");                                   // baseline release
		eventIDXlateTable.put((Object)"35", (Object) "BlockRelease");                                      // block release   
		eventIDXlateTable.put((Object)"36", (Object) "UnblockRelease");                                    // unblock release
		eventIDXlateTable.put((Object)"37", (Object) "BuildRelease");                                      // build release (create or update)
		eventIDXlateTable.put((Object)"45", (Object) "PromoteReleaseArea");                                // promote release area
		eventIDXlateTable.put((Object)"46", (Object) "DemoteReleaseArea");                                 // demote release area 
		eventIDXlateTable.put((Object)"52", (Object) "AuditReleaseArea");                                  // audit release area
		eventIDXlateTable.put((Object)"53", (Object) "ApproveReleaseArea");                                // approve release area
		eventIDXlateTable.put((Object)"54", (Object) "RejectReleaseArea");                                 // reject release area
		eventIDXlateTable.put((Object)"55", (Object) "BlockReleaseArea");                                  // block release area
		eventIDXlateTable.put((Object)"56", (Object) "UnblockReleaseArea");                                // unblock release area
		eventIDXlateTable.put((Object)"78", (Object) "CheckinToReleaseAreaComplete");                      // checkin to release area complete
		eventIDXlateTable.put((Object)"79", (Object) "RetrieveFromReleaseAreaComplete");                   // retrieve from release area
		eventIDXlateTable.put((Object)"94", (Object) "AttachPackageToRelease");                            // attach package to release
		eventIDXlateTable.put((Object)"95", (Object) "DetachPackageFromRelease");                          // detach package from release
	}
	
    static String xlateEventType(String eventID) {
    	return (String) eventIDXlateTable.get((Object) eventID);
    }

    public EventData() {
		init();
	}

	public EventData(String eventID,
					 String eventType,
					 String eventDescription,
					 String timeStamp,
					 String objectType,
					 String serverName,
					 String serverAddress,
					 String serverPortID,
					 String serverVersion) {
		init();
		this.eventID.append(eventID);
		this.eventType.append(eventType);
		this.eventDescription.append(eventDescription);
		this.timeStamp.append(timeStamp);
		this.objectType.append(objectType);
		this.serverName.append(serverName);
		this.serverAddress.append(serverAddress);
		this.serverPortID.append(serverPortID);
		this.serverVersion.append(serverVersion);
	}
	
	public void init() {
		this.eventID = new StringBuffer();
		this.eventType = new StringBuffer();
		this.eventDescription = new StringBuffer();
		this.timeStamp = new StringBuffer();
		this.objectType = new StringBuffer();
		this.serverName = new StringBuffer();
		this.serverAddress = new StringBuffer();
		this.serverPortID = new StringBuffer();
		this.serverVersion = new StringBuffer();
		eventDetailsTable = new HashMap<Object, String>();
	}
	public HashMap<Object, String> getEventDetailsTable() {
		return eventDetailsTable;
	}

	public String getEventType() {
		return this.eventType.toString();
	}

	public void setEventType(String eventType) {
		if (eventType != null && eventType.length() > 0)
			this.eventType.append(eventType);
	}

	public String getEventID() {
		return this.eventID.toString();
	}

	public void setEventID(String eventID) {
		if (eventID != null && eventID.length() > 0)
			this.eventID.append(eventID);
	}

	public String getEventDescription() {
		return this.eventDescription.toString();
	}

	public void setEventDescription(String eventDescription) {
		if (eventDescription != null && eventDescription.length() > 0)
			this.eventDescription.append(eventDescription);
	}
	
	public String getTimeStamp() {
		return this.timeStamp.toString();
	}

	public void setTimeStamp(String timeStamp) {
		if (timeStamp != null && timeStamp.length() > 0)
			this.timeStamp.append(timeStamp);
	}

	public String getObjectType() {
		return this.objectType.toString();
	}

	public void setObjectType(String objectType) {
		if (objectType != null && objectType.length() > 0)
			this.objectType.append(objectType);
	}

	public String getServerName() {
		return this.serverName.toString();
	}

	public void setServerName(String serverName) {
		if (serverName != null && serverName.length() > 0)
			this.serverName.append(serverName);
	}

	public String getServerAddress() {
		return this.serverAddress.toString();
	}

	public void setServerAddress(String serverAddress) {
		if (serverAddress != null && serverAddress.length() > 0)
			this.serverAddress.append(serverAddress);
	}

	public String getServerPortID() {
		return this.serverPortID.toString();
	}

	public void setServerPortID(String serverPortID) {
		String portId = serverPortID;
		if (portId == null || portId.length() < 1)
			portId = "0";
		
		// trim leading zeroes.
		Integer iPort = new Integer(serverPortID);
		this.serverPortID.append(iPort.toString());
	}
	
	public String getServerVersion() {
		return this.serverVersion.toString();
	}

	public void setServerVersion(String serverVersion) {
		if (serverVersion != null && serverVersion.length() > 0)
			this.serverVersion.append(serverVersion);
	}
	
	public void addEventDetail(String key, String value) {
		eventDetailsTable.put((Object) key, value);
	}
	
	public String toString() {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("<" + TAG_EVENTDATA + ">" + "\n");
		sBuffer.append("\t" + "<" + TAG_EVENTID + ">" + getEventID() + "</" + TAG_EVENTID + ">" + "\n");
		sBuffer.append("\t" + "<" + TAG_EVENTTYPE + ">" + getEventType() + "</" + TAG_EVENTTYPE + ">" + "\n");
		sBuffer.append("\t" + "<" + TAG_EVENTDESC + ">" + getEventDescription() + "</" + TAG_EVENTDESC + ">" + "\n");
		sBuffer.append("\t" + "<" + TAG_OBJECTTYPE + ">" + getObjectType() + "</" + TAG_OBJECTTYPE + ">" + "\n");
		sBuffer.append("\t" + "<" + TAG_TIMESTAMP + ">" + getTimeStamp() + "</" + TAG_TIMESTAMP + ">" + "\n");
		sBuffer.append("\t" + "<" + TAG_SERVER + ">" + "\n");
		sBuffer.append("\t\t" + "<" + TAG_SERVERNAME + ">" + getServerName() + "</" + TAG_SERVERNAME + ">" + "\n");
		sBuffer.append("\t\t" + "<" + TAG_SERVERADDRESS + ">" + getServerAddress() + "</" + TAG_SERVERADDRESS + ">" + "\n");
		sBuffer.append("\t\t" + "<" + TAG_SERVERPORTID + ">" + getServerPortID() + "</" + TAG_SERVERPORTID + ">" + "\n");
		sBuffer.append("\t\t" + "<" + TAG_SERVERVERSION + ">" + getServerVersion() + "</" + TAG_SERVERVERSION + ">" + "\n");
		sBuffer.append("\t" + "</" + TAG_SERVER + ">" + "\n");
		int size = eventDetailsTable.size();
		if (size > 0) {
			sBuffer.append("\t" + "<" + TAG_EVENTDETAILS + ">" + "\n");
			Set<Object> keys = eventDetailsTable.keySet();
			Iterator<Object> i = keys.iterator();
			while (i.hasNext()) {
				String key = (String) i.next();
				sBuffer.append("\t\t" + "<" + key + ">"); 
				sBuffer.append((String) eventDetailsTable.get((Object) key));
				sBuffer.append("</" + key + ">" + "\n");

			}
	
			sBuffer.append("\t" + "</" + TAG_EVENTDETAILS + ">" + "\n");
		}

		sBuffer.append("</" + TAG_EVENTDATA + ">" + "\n");
		return sBuffer.toString();
	}
	
	
}
