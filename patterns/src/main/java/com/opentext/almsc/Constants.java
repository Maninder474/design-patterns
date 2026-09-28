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

/**
 * @author mpanis
 */
public interface Constants {
    // properties

    static public final String SBM_ALF_EVENTMANAGERURL = "SBM_ALF_EVENTMANAGERURL";

    public final static String SBM_USERID = "SBM_USERID";

    public final static String SBM_PASSWORD = "SBM_PASSWORD";

    public final static String RLC_NOTIFICATION_URL = "RLC_NOTIFICATION_URL";

    static public final String defultALFEndPoint = "http://localhost:8085/eventmanager/services/ALFEventManager";

    // Messages

    static public final String ERR_ZMF_ALF_PROPERTIES_HOME_NOT_SET = "WARNING!:zmfalfProportiesHome parameter not set in the web.xml context parameters.";

    static public final String ERR_ZMF_ALF_PROPERTIES_FILE_NOT_FOUND = "WARNING!: zmfalf_resource.properties file not found.";

    static public final String ERR_ZMF_ALF_EVENT_MAP_FILE_NOT_FOUND = "WARNING!: zmfalfeventmap.xml file not found.";

    static public final String ERR_UNABLE_TO_LOAD_EVENT_MAP_FILE = "Unable to load the event mapping file.";

    static public final String EVENT_MAP_FILE_EMPTY = "Event mapping file is empty. Restart ZMF Web services or issue a ZMFAdmin reloadEventMap command.";

    // XML element tags

    static public final String TAG_ZMFEVENT = "ZMFEvent";

    static public final String TAG_ALFEVENT = "ALFEvent";

    static public final String TAG_VERSION = "version";

    static public final String TAG_EVENTCONDITIONS = "EventConditions";

    static public final String TAG_EVENTDATA = "EventData";

    static public final String TAG_EVENTMANAGER = "EventManager";

    static public final String TAG_EVENTID = "EventID";

    static public final String TAG_ZMFEVENTTYPE = "ZMFEventType";

    static public final String TAG_EVENTTYPE = "EventType";

    static public final String TAG_EVENTDESC = "EventDescription";

    static public final String TAG_TIMESTAMP = "Timestamp";

    static public final String TAG_OBJECTTYPE = "ObjectType";

    static public final String TAG_ZMFOBJECTTYPE = "ZMFObjectType";

    static public final String TAG_SERVER = "Server";

    static public final String TAG_SERVERADDRESS = "ServerAddress";

    static public final String TAG_SERVERPORTID = "ServerPortID";

    static public final String TAG_SERVERVERSION = "ServerVersion";

    static public final String TAG_EVENTDETAILS = "EventDetails";

    static public final String TAG_PRODUCT = "Product";

    static public final String TAG_PRODUCTVERSION = "ProductVersion";

    static public final String TAG_PRODUCTINSTANCE = "ProductInstance";

    static public final String TAG_CALLBACKURI = "CallbackURI";

    static public final String TAG_OPERATION = "operation";

    static public final String TAG_EXTENSION = "Extension";

    static public final String TAG_EVENTWASRECEIVED = "EventWasReceived";

    static public final String TAG_OBJECT_UPDATED = "Object Updated";

    static public final String TAG_SERVERNAME = "ServerName";

    static public final String TAG_ZMFPACKAGE = "ZMFPackage";

    static public final String TAG_PACKAGE = "Package";

    static public final String TAG_PACKAGENAME = "PackageName";

    static public final String TAG_SITENAME = "SiteName";

    static public final String TAG_PROMOTIONNAME = "PromotionName";

    static public final String TAG_PROMOTIONAREA = "PromotionArea";

    static public final String TAG_PROMOTIONLEVEL = "PromotionLevel";

    static public final String TAG_STATUS = "Status";

    static public final String TAG_APPLICATION = "Application";

    static public final String TAG_RELEASE = "Release";

    static public final String TAG_RELEASEAREA = "ReleaseArea";

    static public final String TAG_RELEASEFROMAREA = "ReleaseFromArea";

    static public final String TAG_COMPONENT = "Component";

    static public final String TAG_LIBTYPE = "LibType";

    static public final String TAG_LASTMESSAGE = "LastMessage";

    static public final String TAG_LASTEVENTDESCRIPTION = "LastEventDescription";

    static public final String TAG_LASTJOBNAME = "LastJobname";

    static public final String TAG_LASTJOBNUMBER = "LastJobnumber";

    static public final String TAG_LASTJOBTYPE = "LastJobType";

    static public final String TAG_USERID = "UserId";
}
