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

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.axiom.om.OMElement;
import org.apache.axis2.client.Options;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.eclipse.alf.eventmanager.webservice.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;


/**
 * * <code>ZMFALFEventRouter</code> is a subsystem of the ZMF Event Notification system. All emitted ZMF events will be
 * sent to this subsystem. The ZMF Event Http Soap Handler subsystem that runs under SERNET on zOS must be
 * configured to point to the URL of the event router:
 * <p>
 * http://hostname:8080/almzmfalf/services/ZMFALFEventRouter
 * <p>
 * The hostname is the machine name or address that is hosting the ZMF Event router, and 8090 is the port id
 * that the router is listening on for events.
 * <p>
 * When the router receives the event, it is compared against the Event Mapping table. If a match is found, the ZMF
 * event is transformed to an ALF event. Depending on the event manager properties in the event mapping file,
 * the ALF event is then routed to ALF or some tool event listener.
 *
 * @author Manny Panis
 */
public class ZMFALFEventRouter extends ZMFALFHttpServlet implements Constants {

    private static final Log LOG = LogFactory.getLog(ZMFALFEventRouter.class);

    private static final long serialVersionUID = -4053314361170940538L;

    private static final String CONST_STARTED = "Started";
    private static final String CONST_PROMOTE = "Promote";
    private static final String CONST_DEMOTE = "Demote";
    private static final String CONST_AUDIT = "Audit";
    private static final String CONST_INSTALL = "Install";
    private static final String CONST_DISTRIBUTE = "Distribute";
    private static final String CONST_BASELINE_RIPPLE = "BaselineRipple";
    private static final String CONST_BACKOUT = "Backout";
    private static final String CONST_BACKOUT_DESCR = "Backed out";
    private static final String CONST_BASELINE_REVERSE_RIPPLE = "BaselineReverseRipple";
    private static final String CONST_REVERT = "Revert";
    private static final String CONST_COMPLETED = "Completed";
    private static final String CONST_PACKAGE = "package";
    private static final String CONST_SITE = "site";
    private static final String CONST_SUCCESS = "SUCCESS";
    private static final String CONST_FAILED = "Failed";
    private static final String CONST_FAILURE = "FAILURE";
    private static final String CONST_IN_PROGRESS = "IN_PROGRESS";
    private static final String CONST_EVENT_STATUS = "eventStatus";

    /**
     * Performs initial processing.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {


        // read request input stream into a string buffer.
        StringBuffer sBuffer = new StringBuffer();
        ServletInputStream in = req.getInputStream();
        byte[] b = new byte[4096];
        int nBytes = 0;
        while (nBytes > -1) {
            nBytes = in.readLine(b, 0, 4096);
            if (nBytes == -1)
                break;

            String s = new String(b, 0, nBytes);
            sBuffer.append(s);
        }

        // Print and log event.
        String sEvent = sBuffer.toString();
        LOG.debug("ZMF Log Event - " + sEvent);
        // Parse and load event XML request.
        ByteArrayInputStream bArrayIn = new ByteArrayInputStream(sEvent.getBytes());
        EventParser parser = new EventParser();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(bArrayIn, parser);

            // Find a matching event. If a match is found, format an ALF event.
            HashSet<?> eventDataTables = parser.getEventDataTable();
            Iterator<?> iter = eventDataTables.iterator();
            while (iter.hasNext()) {
                EventData eventData = (EventData) iter.next();
                String eventType = eventData.getEventType();
                String objectType = eventData.getObjectType();
                String eventDesc = eventData.getEventDescription();
                // make sure to process just package events for now.
                if (eventDesc != null && eventDesc.length() > 0) {
                    if (!eventDesc.startsWith("cmponent/")) {
                        EventCondition eventCondition = this.getEventCondition(eventType, objectType);
                        if (eventCondition != null) {
                            if (sbmAlfEventManagerUrl != null && sbmAlfEventManagerUrl.length() > 0)
                                formatALFEvent(res, eventData, eventCondition, eventDesc);

                            if (rlcNotificationUrl != null && rlcNotificationUrl.length() > 0)
                                notifyRLC(eventData, eventCondition, eventDesc);
                        }
                    }

                }

            }

            createResponse(res, sEvent);
        } catch (Exception e) {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
            LOG.error(e.getMessage(), e);
        }
    }

    synchronized public void notifyRLC(EventData eventData, EventCondition eventCondition, String eventDesc) {
        LOG.debug(RLC_NOTIFICATION_URL + "=" + rlcNotificationUrl);

        try (CloseableHttpClient client = HttpClientBuilder.create().useSystemProperties().build()) {

            URI uri = new URI(rlcNotificationUrl);
            HttpPut request = new HttpPut(uri);
            HashMap<?, ?> hashMap = eventData.getEventDetailsTable();
            String eventType = eventData.getEventType();
            if (eventType == null) {
                return;
            }
            String eventStatus = CONST_STARTED;
            String eventId = eventType.toUpperCase();
            String promoEnv = null;
            if (eventType.equalsIgnoreCase(CONST_PROMOTE) || eventType.equalsIgnoreCase(CONST_DEMOTE)) {
				/* Sample event descriptions
				"PACKAGE/PROMOTE/SERVICE BKUPSITE/BKUPAREA/40 Completed"
				"PACKAGE/DEMOTE/SERVICE BKUPSITE/BKUPAREA/40 Completed."
				"BKUPSITE/BKUPAREA/40 Failed."
				"ARMQS9   Failed Package distribution"
				"ARMQS9   Failed Package Install"
				"ARMQSL   Failed Revert a package"
				 */
                String[] descriptions = eventDesc.split(" ");
                if (descriptions.length < 1) {
                    return;
                }
                if (descriptions.length > 2) {
                    promoEnv = descriptions[1];
                    eventStatus = descriptions[2];
                } else {
                    promoEnv = descriptions[0];
                    eventStatus = descriptions[1];
                }
                if (promoEnv != null && promoEnv.length() > 0) {
                    promoEnv = promoEnv.replaceAll("/", ":");
                    eventId = eventId + ":" + promoEnv;
                }
                if (eventStatus.endsWith(".")) {
                    eventStatus = eventStatus.substring(0, eventStatus.length() - 1);
                }
            } else if (eventType.equalsIgnoreCase(CONST_AUDIT)) {
                if (eventDesc.startsWith("Pass the audit")) {
                    eventStatus = CONST_COMPLETED;
                }
            } else if (eventType.equalsIgnoreCase(CONST_INSTALL)) {
                if (eventDesc.contains("Failed")) {
                    eventStatus = CONST_FAILED;
                } else {
                    eventStatus = CONST_IN_PROGRESS;
                }
            } else if (eventType.equalsIgnoreCase(CONST_DISTRIBUTE)) {
                if (eventDesc.contains("Failed")) {
                    eventStatus = CONST_FAILED;
                } else {
                    eventStatus = CONST_IN_PROGRESS;
                }
            } else if (eventType.equalsIgnoreCase(CONST_BASELINE_RIPPLE)) {
                eventStatus = CONST_COMPLETED;
            } else if (eventType.equalsIgnoreCase(CONST_BACKOUT)) {
                if (eventDesc.contains("Failed")) {
                    eventStatus = CONST_FAILED;
                } else {
                    String eventStatusFromResp = (String) hashMap.get((Object) CONST_EVENT_STATUS);
                    if (eventStatusFromResp.equalsIgnoreCase(CONST_COMPLETED)) {
                        eventStatus = CONST_COMPLETED;
                    } else {
                        eventStatus = CONST_IN_PROGRESS;
                    }
                }
            } else if (eventType.equalsIgnoreCase(CONST_BASELINE_REVERSE_RIPPLE)) {
                if (eventDesc.contains("Failed")) {
                    eventStatus = CONST_FAILED;
                } else {
                    eventStatus = CONST_COMPLETED;
                }
            } else if (eventType.equalsIgnoreCase(CONST_REVERT)) {
                if (eventDesc.contains("Failed")) {
                    eventStatus = CONST_FAILED;
                } else {
                    String eventStatusFromResp = (String) hashMap.get((Object) CONST_EVENT_STATUS);
                    if (eventStatusFromResp.equalsIgnoreCase(CONST_COMPLETED)) {
                        eventStatus = CONST_COMPLETED;
                    } else {
                        eventStatus = CONST_IN_PROGRESS;
                    }
                }
            } else {
                return;
            }
            String packageName = (String) hashMap.get((Object) CONST_PACKAGE);
            if (packageName != null && packageName.length() > 0) {
                if (eventStatus.equalsIgnoreCase(CONST_COMPLETED)) {
                    eventStatus = CONST_SUCCESS;
                } else if (eventStatus.equalsIgnoreCase(CONST_FAILED)) {
                    eventStatus = CONST_FAILURE;
                } else {
                    eventStatus = CONST_IN_PROGRESS;
                }
                // skip in progress status for now.
                if (eventStatus.equalsIgnoreCase(CONST_IN_PROGRESS)) {
                    return;
                }
                String content;
                if (eventId.equalsIgnoreCase(CONST_BACKOUT) || eventId.equalsIgnoreCase(CONST_BASELINE_REVERSE_RIPPLE) || eventId.equalsIgnoreCase(CONST_REVERT)) {
                    String siteName = "";
                    if (hashMap.containsKey(CONST_SITE)) {
                        siteName = (String) hashMap.get((Object) CONST_SITE);
                    }
                    content = formatRlcNotificationBodyWithSiteName(eventData, packageName, eventId, eventStatus, siteName, eventDesc);
                } else {
                    content = formatRlcNotificationBody(eventData, packageName, eventId, eventStatus, eventDesc);
                }
                if (content != null && content.length() > 0) {
                    LOG.debug("RLC Notification message: " + content);
                    StringEntity se = new StringEntity(content, ContentType.APPLICATION_OCTET_STREAM);
                    request.setEntity(se);
                    request.setHeader("Content-Type", "application/octet-stream;charset=UTF-8");
                    try (CloseableHttpResponse response = client.execute(request)) { // auto-close 'response'; empty 'try' block -> throw non-suppressed exception from try-with-resources
                    }
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            for (Throwable t: e.getSuppressed()) {
                LOG.error("Suppressed exception: " + t.getMessage(), t);
            }
        }
    }

    private String formatRlcNotificationBody(EventData eventData, String packageName, String eventId, String eventStatus, String eventDesc) {
        // notification format: serveripaddress:port@packagename-eventMessage/status/message
        InetAddress address = null;
        try {
            address = InetAddress.getByName(eventData.getServerAddress());
            Integer iPort = Integer.valueOf(eventData.getServerPortID());
            return address.getHostAddress() + ":" + iPort.toString() +
                    "@" + packageName.toUpperCase() +
                    "-" + eventId +
                    "/" + eventStatus +
                    "/" + eventDesc;
        } catch (UnknownHostException e) {
            LOG.error(e.getMessage(), e);
        }

        return null;
    }

    private String formatRlcNotificationBodyWithSiteName(EventData eventData, String packageName, String eventId, String eventStatus, String siteName, String eventDesc) {
        // notification format: serveripaddress:port@packagename-eventMessage/status/siteName/message
        try {
            InetAddress address = InetAddress.getByName(eventData.getServerAddress());
            Integer iPort = Integer.valueOf(eventData.getServerPortID());
            return address.getHostAddress() + ":" + iPort.toString() +
                    "@" + packageName.toUpperCase() +
                    "-" + eventId +
                    "/" + eventStatus +
                    "/" + siteName +
                    "/" + eventDesc;
        } catch (UnknownHostException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    /*
     * Formats ALF event
     */
    synchronized public void formatALFEvent(HttpServletResponse res, EventData eventData, EventCondition eventCondition, String eventDesc) throws Exception {

        String eventType = eventData.getEventType();
        if (eventType == null || eventType.length() < 1)
            return;

        ALFEventManagerStub alfStub = null;
        try {

            alfStub = new ALFEventManagerStub(sbmAlfEventManagerUrl);
            Options options = alfStub._getServiceClient().getOptions();
            options.setManageSession(true);
            options.setTimeOutInMilliSeconds(60000);

            EventNotice eventNotice = new EventNotice();
            fillEventNotice(eventNotice, eventData, eventCondition, eventDesc);
            //raiseUseCount();
            ALFEventResponseType eventRes = alfStub.eventNotice(eventNotice).getEventNoticeResponse();
            //decreaseUseCount();
            LOG.debug(eventRes.toString());

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            if (alfStub != null)
                alfStub.cleanup();
        }
    }

    synchronized public void fillEventNotice(EventNotice param, EventData eventData, EventCondition eventCondition, String eventDesc) {

        param.setEventNotice(new ALFEventType());
        ALFEventType event = param.getEventNotice();
        EventFill(event, eventCondition);

        EventBaseType base = event.getBase();
        String eventType = eventData.getEventType();
        base.getEventId().setSourceEventIdType(UUID.randomUUID().toString());
        base.getEventType().setEventTypeType(eventCondition.getEventType());
        base.getObjectId().setObjectIdType(UUID.randomUUID().toString());
        base.getObjectType().setObjectTypeType(eventCondition.getObjectType());
        SourceType source = base.getSource();
        source.getProduct().setProductType(eventCondition.getProduct());
        String productVersion = eventCondition.getProductVersion();
        if (productVersion == null || productVersion.length() < 1 || productVersion.equalsIgnoreCase("?"))
            productVersion = eventData.getServerVersion();

        if (productVersion == null || productVersion.length() < 1)
            productVersion = "1.0";

        source.getProductVersion().setProductVersionType(productVersion);
        String productInstance = eventCondition.getProductInstance();
        if (productInstance == null || productInstance.length() < 1 || productInstance.equalsIgnoreCase("?")) {
            productInstance = eventData.getServerName() + "-" + eventData.getServerAddress() + ":" + eventData.getServerPortID();
        }

        source.getProductInstance().setProductInstanceType(productInstance);

        CustomExtensionType customExt = event.getExtension();

        // Set the values of the extended data.
        ExtensionData extData = eventCondition.getExtensionEntry(TAG_SERVERNAME);
        if (extData != null)
            extData.setValue(eventData.getServerName());

        extData = eventCondition.getExtensionEntry(TAG_SERVERADDRESS);
        if (extData != null)
            extData.setValue(eventData.getServerAddress());

        extData = eventCondition.getExtensionEntry(TAG_SERVERPORTID);
        if (extData != null)
            extData.setValue(eventData.getServerPortID());

        extData = eventCondition.getExtensionEntry(TAG_SERVERVERSION);
        if (extData != null)
            extData.setValue(eventData.getServerVersion());

        HashMap<?, ?> hashMap = eventData.getEventDetailsTable();

        String extValue = (String) hashMap.get((Object) "application");
        if (extValue != null && extValue.length() > 0) {
            extData = eventCondition.getExtensionEntry(TAG_APPLICATION);
            if (extData != null)
                extData.setValue(extValue);
        }

        extValue = (String) hashMap.get((Object) "release");
        if (extValue != null && extValue.length() > 0) {
            extData = eventCondition.getExtensionEntry(TAG_RELEASE);
            if (extData != null)
                extData.setValue(extValue);
        }

        extValue = (String) hashMap.get((Object) "package");
        if (extValue != null && extValue.length() > 0) {
            extData = eventCondition.getExtensionEntry(TAG_PACKAGENAME);
            if (extData != null)
                extData.setValue(extValue);
        }        
	    /* Sample event descriptions
		"PACKAGE/PROMOTE/SERVICE BKUPSITE/BKUPAREA/40 Completed"
		"PACKAGE/DEMOTE/SERVICE BKUPSITE/BKUPAREA/40 Completed."
		"BKUPSITE/BKUPAREA/40 Failed." 
	    */
        extValue = (String) hashMap.get((Object) "site");
        if (extValue != null && extValue.length() > 0) {
            extData = eventCondition.getExtensionEntry(TAG_SITENAME);
            if (extData != null)
                extData.setValue(extValue);
        }

        extValue = (String) hashMap.get((Object) "promotionName");
        if (extValue != null && extValue.length() > 0) {
            extData = eventCondition.getExtensionEntry(TAG_PROMOTIONNAME);
            if (extData != null)
                extData.setValue(extValue);
        }

        extValue = (String) hashMap.get((Object) "promotionLevel");
        if (extValue != null && extValue.length() > 0) {
            extData = eventCondition.getExtensionEntry(TAG_PROMOTIONLEVEL);
            if (extData != null)
                extData.setValue(extValue);
        }

        extValue = (String) hashMap.get((Object) "releaseArea");
        if (extValue != null && extValue.length() > 0) {
            extData = eventCondition.getExtensionEntry(TAG_RELEASEAREA);
            if (extData != null)
                extData.setValue(extValue);
        }

        extValue = (String) hashMap.get((Object) "releaseFromArea");
        if (extValue != null && extValue.length() > 0) {
            extData = eventCondition.getExtensionEntry(TAG_RELEASEFROMAREA);
            if (extData != null)
                extData.setValue(extValue);
        }

        extValue = (String) hashMap.get((Object) "component");
        if (extValue != null && extValue.length() > 0) {
            extData = eventCondition.getExtensionEntry(TAG_COMPONENT);
            if (extData != null)
                extData.setValue(extValue);
        }

        extValue = (String) hashMap.get((Object) "libType");
        if (extValue != null && extValue.length() > 0) {
            extData = eventCondition.getExtensionEntry(TAG_LIBTYPE);
            if (extData != null)
                extData.setValue(extValue);
        }

        extValue = (String) hashMap.get((Object) "userId");
        if (extValue != null && extValue.length() > 0) {
            extData = eventCondition.getExtensionEntry(TAG_USERID);
            if (extData != null)
                extData.setValue(extValue);
        }

        extValue = (String) hashMap.get((Object) "eventStatus");
        if (extValue != null && extValue.length() > 0) {
            extData = eventCondition.getExtensionEntry(TAG_LASTMESSAGE);
            if (extData != null)
                extData.setValue(extValue);
            extData = eventCondition.getExtensionEntry(TAG_STATUS);
            if (extData != null)
                extData.setValue(extValue);
        } else {
            extData = eventCondition.getExtensionEntry(TAG_LASTMESSAGE);
            if (extData != null)
                extData.setValue(eventDesc);
        }

        extValue = (String) hashMap.get((Object) "jobName");
        if (extValue != null && extValue.length() > 0) {
            extData = eventCondition.getExtensionEntry(TAG_LASTJOBNAME);
            if (extData != null)
                extData.setValue(extValue);
        }

        extValue = (String) hashMap.get((Object) "jobNumber");
        if (extValue != null && extValue.length() > 0) {
            extData = eventCondition.getExtensionEntry(TAG_LASTJOBNUMBER);
            if (extData != null)
                extData.setValue(extValue);
        }

        extValue = (String) hashMap.get((Object) "messageType");
        if (extValue != null && extValue.length() > 0) {
            extData = eventCondition.getExtensionEntry(TAG_LASTJOBTYPE);
            if (extData != null)
                extData.setValue(extValue);
        }

        LOG.debug(eventCondition.toString());

        OMElement[] elements = fillExtensions(eventCondition);
        if (elements != null && elements.length > 0)
            customExt.setExtraElement(elements);

    }
}
