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

import com.urbancode.commons.util.crypto.CryptStringUtil;
import com.urbancode.commons.util.crypto.DefaultKeysProvider;
import com.urbancode.commons.util.crypto.KeysProviderCache;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.databinding.types.URI;
import org.apache.axis2.databinding.types.URI.MalformedURIException;
import org.apache.axis2.transport.http.AxisServlet;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.alf.eventmanager.webservice.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;


/**
 * <code>ZMFALFHttpServlet</code> is an extension of HttpServlet.
 * This is executed when ZMF Web Services is started or at the first time a ZMF Web service is invoked. When this is
 * executed, it will load the resource properties, and parse and load the ZMF Event Xml mapping table.
 *
 * @author Manny Panis
 */
public class ZMFALFHttpServlet extends AxisServlet implements Constants {

    private static final Log LOG = LogFactory.getLog(ZMFALFHttpServlet.class);

    private static final long serialVersionUID = -2188815945137916864L;

    private static final String ZMF_ALF_PROPERTIES_HOME = "zmfalfPropertiesHome";
    private static final String ZMF_ALF_RESOURCE_PROPERTIES_FILE = "zmfalf_resource.properties";
    private static final String ZMF_ALF_EVENT_MAP_FILE = "zmfalfeventmap.xml";
    static private final String SLASH = "/";

    private static ServletContext context;
    private static Map<?, ?> eventMapTable = null;
    private static Properties properties = null;
    private static String realPath = null;
    public static ALFEventManagerStub _alfStub = null;

    public static String sbmAlfEventManagerUrl = null;
    public static String sbmUserId = null;
    public static String sbmPswd = null;
    public static String rlcNotificationUrl = null;

    /**
     * Performs initial processing.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        context = config.getServletContext();
        setRealPath();

        if (properties == null) {
            loadProperties();
        }

        if (eventMapTable == null) {
            parseEventMap();
            printEventMap();
        }

        CryptStringUtil.setKeysProvider(new KeysProviderCache(new DefaultKeysProvider()));

        sbmAlfEventManagerUrl = ZMFALFHttpServlet.getProperty(SBM_ALF_EVENTMANAGERURL);
        if (sbmAlfEventManagerUrl != null)
            sbmAlfEventManagerUrl = sbmAlfEventManagerUrl.trim();
        else
            sbmAlfEventManagerUrl = null;

        sbmUserId = ZMFALFHttpServlet.getProperty(SBM_USERID);
        if (sbmUserId != null) {
            try {
                if (CryptStringUtil.isEncrypted(sbmUserId))
                    sbmUserId = CryptStringUtil.decrypt(sbmUserId);
            } catch (GeneralSecurityException e) {
                LOG.error(e.getMessage(), e);
                sbmUserId = ZMFALFHttpServlet.getProperty(SBM_USERID);
            }
        }

        sbmPswd = ZMFALFHttpServlet.getProperty(SBM_PASSWORD);
        if (sbmPswd != null) {
            try {
                if (CryptStringUtil.isEncrypted(sbmPswd))
                    sbmPswd = CryptStringUtil.decrypt(sbmPswd);
            } catch (GeneralSecurityException e) {
                LOG.error(e.getMessage(), e);
                sbmPswd = ZMFALFHttpServlet.getProperty(SBM_PASSWORD);
            }
        }
        rlcNotificationUrl = ZMFALFHttpServlet.getProperty(RLC_NOTIFICATION_URL);
        if (rlcNotificationUrl != null)
            rlcNotificationUrl = rlcNotificationUrl.trim();
    }

    /**
     * Sets the actual path of the ZMFALF Web Service properties home directory. The the actual path is defined in the
     * web.xml file under context-param with he following configuration:
     * <context-param>
     * <param-name>zmfPropertiesHome</param-name>
     * <param-value>/WEB-INF/</param-value>
     * </context-param>
     */
    synchronized public static void setRealPath() {
        realPath = context.getInitParameter(ZMF_ALF_PROPERTIES_HOME);
        if (realPath == null || realPath.length() < 1) {
            LOG.error(ERR_ZMF_ALF_PROPERTIES_HOME_NOT_SET);
            return;
        }
        if (!realPath.startsWith(SLASH)) {
            realPath = SLASH + realPath;
        }
        if (!realPath.endsWith(SLASH)) {
            realPath += SLASH;
        }
    }

    /**
     * Parses event event-action mapping file. This static method that should be
     * called when the event-action map file needs to be parsed. This happens
     * automatically at startup from the init() method. It can also be called if
     * there are changes made to the event-action map file.
     */
    synchronized public static void parseEventMap() {
        // don't do anyting if the context isn't initialized yet
        if (context == null) {
            return;
        }
        String eventMapFile = getRealPath() + ZMF_ALF_EVENT_MAP_FILE;
        if (StringUtils.isEmpty(eventMapFile)) {
            LOG.error(ERR_ZMF_ALF_EVENT_MAP_FILE_NOT_FOUND);
            return;
        }
        try (InputStream in = context.getResourceAsStream(eventMapFile)) {
            if (in == null) {
                LOG.error(ERR_ZMF_ALF_EVENT_MAP_FILE_NOT_FOUND);
                return;
            }
            EventMapParser ep = new EventMapParser();
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            saxParserFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            SAXParser p = saxParserFactory.newSAXParser();
            p.parse(in, ep);
            eventMapTable = ep.getEventTable();
        } catch (Exception e) {
            LOG.error(ERR_UNABLE_TO_LOAD_EVENT_MAP_FILE + e.getMessage(), e);
            for (Throwable t: e.getSuppressed()) {
                LOG.error("Suppressed exception: " + t.getMessage());
            }
        }
    }

    /**
     * Prints event mapping file.
     */
    synchronized public static void printEventMap() {
        // don't do anyting if the context isn't initialized yet
        if (context == null) {
            return;
        }

        if (eventMapTable == null || eventMapTable.isEmpty()) {
            LOG.error(EVENT_MAP_FILE_EMPTY);
        } else {
            Collection<?> values = eventMapTable.values();
            Iterator<?> iValues = values.iterator();
            while (iValues.hasNext()) {
                EventCondition eventCondition = (EventCondition) iValues.next();
                LOG.debug(eventCondition.toString());
            }
        }
    }

    /**
     * Finds the matching EventCondition object.
     *
     * @param eventType - String eventType
     * @param objectType - String objectType
     * @return EventCondition object
     */
    synchronized public EventCondition getEventCondition(String eventType, String objectType) {

        EventCondition eventCondition = null;
        if (eventMapTable != null && eventMapTable.size() > 0) {
            Integer iKey = new Integer(EventCondition.getHashToken(eventType, objectType));

            eventCondition = (EventCondition) eventMapTable.get(iKey.toString());
        }

        Calendar date = Calendar.getInstance();
        if (eventCondition == null) {
            LOG.debug(date.getTime().toString() + " No match found for eventType=" + eventType + " objectType=" + objectType);
        } else {
            LOG.debug(date.getTime().toString() + " Match found for " + eventCondition.toString());
        }

        return eventCondition;
    }

    /**
     * Loads ZMFALF Web Service resource properties.
     */
    synchronized public static void loadProperties() {
        // don't do anything if the context isn't initialized yet
        if (context == null) {
            return;
        }
        String propertiesFile = getRealPath() + ZMF_ALF_RESOURCE_PROPERTIES_FILE;
        if (StringUtils.isEmpty(propertiesFile)) {
            LOG.error(ERR_ZMF_ALF_PROPERTIES_FILE_NOT_FOUND);
            return;
        }
        properties = new Properties();
        try (InputStream in = context.getResourceAsStream(propertiesFile)) {
            properties.load(in);
        } catch (Exception e) {
            LOG.error(ERR_ZMF_ALF_PROPERTIES_FILE_NOT_FOUND + e.getMessage(), e);
            for (Throwable t: e.getSuppressed()) {
                LOG.error("Suppressed exception: " + t.getMessage());
            }
        }
    }

    /**
     * Gets ZMFALF Web Services properties home directory.
     *
     * @return String realPath
     */
    synchronized public static String getRealPath() {
        return realPath;
    }

    /**
     * Gets property entry from the Properties
     *
     * @param key - String property key
     * @return String property value
     */
    synchronized public static String getProperty(String key) {
        return properties.getProperty(key);
    }


    synchronized public OMElement[] fillExtensions(EventCondition eventCondition) {
        HashMap<?, ?> extensions = eventCondition.getExtensions();
        int nSize = extensions.size();
        if (nSize < 1)
            return null;

        OMFactory fac = OMAbstractFactory.getOMFactory();
        OMNamespace nsxsi = fac.createOMNamespace(
                "http://www.w3.org/2001/XMLSchema-instance", "xsi");
        OMNamespace nss = fac.createOMNamespace(
                "http://www.eclipse.org/alf/schema/EventBase/1", "s");
        OMElement[] elements = new OMElement[nSize];

        Collection<?> values = extensions.values();
        Iterator<?> entries = values.iterator();
        int i = 0;
        while (entries.hasNext()) {
            ExtensionData extData = (ExtensionData) entries.next();
            OMElement omelement = fac.createOMElement(extData.getKey(), nss);
            omelement.addAttribute("type", "xsd:string", nsxsi);
            omelement.addChild(fac.createOMText(omelement, extData.getValue()));
            elements[i] = omelement;
            i++;
        }

        return elements;
    }

    synchronized public void EventFill(ALFEventType event, EventCondition eventCondition) {
        event.setBase(new EventBaseType());
        EventBaseType base = event.getBase();
        base.setEventId(new SourceEventIdType());
        base.setEventType(new EventTypeType());
        base.setObjectId(new ObjectIdType());
        base.setObjectType(new ObjectTypeType());
        base.setTimestamp(new TimestampType());
        base.getTimestamp().setTimestampType(Calendar.getInstance());
        base.setSource(new SourceType());
        SourceType source = base.getSource();
        source.setProduct(new ProductType());
        source.setProductVersion(new ProductVersionType());
        source.setProductInstance(new ProductInstanceType());
        source.setProductCallbackURI(new ProductCallbackURIType());
        URI callbackURI = null;
        try {
            callbackURI = new URI(eventCondition.getCallbackURI());
        } catch (MalformedURIException e) {
        }
        source.getProductCallbackURI().setProductCallbackURIType(callbackURI);

        CredentialsType credType = new CredentialsType();
        ALFSecurityType alfSecType = new ALFSecurityType();
        UsernameToken_type0 usrNamTknType = new UsernameToken_type0();

        if (sbmUserId != null && sbmUserId.length() > 0)
            usrNamTknType.setUsername(sbmUserId);
        else
            usrNamTknType.setUsername("admin");

        if (sbmPswd != null && sbmPswd.length() > 0)
            usrNamTknType.setPassword(sbmPswd);
        else
            usrNamTknType.setPassword("admin");
        alfSecType.setUsernameToken(usrNamTknType);

        credType.setALFSecurity(alfSecType);
        base.setUser(credType);
        // leave user empty
        // no Extension
        base.setEventControl(new EmBaseType());
        EmBaseType eventControl = base.getEventControl();
        eventControl.setApplicationName(new ApplicationNameType());
        eventControl.getApplicationName().setApplicationNameType("");
        eventControl.setCallback(false); // must be false unless this is a
        // callback from a ServiceFlow

        eventControl.setEmEventId(new EventIdType());
        eventControl.getEmEventId().setEventIdType("");
        eventControl.setEmTimestamp(base.getTimestamp()); // can't be null so
        // set it to base
        // time
        eventControl.setEmUser(credType);
        eventControl.setEnvironment(new EnvironmentType());
        eventControl.getEnvironment().setEnvironmentType("");
        eventControl.setEventMatchName(new EventMatchNameType());
        eventControl.getEventMatchName().setEventMatchNameType("");
        eventControl.setPrecedingEmEventId(new EventIdType());
        eventControl.getPrecedingEmEventId().setEventIdType("");
        eventControl.setServiceFlowId(new ServiceFlowIdType());
        eventControl.getServiceFlowId().setServiceFlowIdType("");
        eventControl.setServiceFlowName(new ServiceFlowNameType());
        eventControl.getServiceFlowName().setServiceFlowNameType("");
        // no Extension
        event.setVersion(ALFSchemaVersionType.value1);
        // no Detail
        // no Extension
        event.setExtension(new CustomExtensionType());
        // no extra
    }

    synchronized public void createResponse(HttpServletResponse res, String sEvent) {
        try {
            int idx = sEvent.indexOf("</" + TAG_ZMFEVENT);
            if (idx == -1) {
//                res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Invalid ZMF Event Xml instance: " + sEvent);
                res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Invalid ZMF Event Xml instance.");
            }
//            String result = sEvent.substring(0, idx);
//            String segment = sEvent.substring(idx);
//            result = result.concat("<" + TAG_EVENTWASRECEIVED +
//                    " xmlns=\"\">true</" +
//                    TAG_EVENTWASRECEIVED + ">");
//            result = result.concat(segment);
//            LOG.debug(result);
            String result = "Event was successfully received.";
            res.getOutputStream().write(result.getBytes());
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
