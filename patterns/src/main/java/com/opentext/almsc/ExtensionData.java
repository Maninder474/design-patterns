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

/*
 * Created on Sep 7, 2007
*/
package com.opentext.almsc;

/**
 * @author mpanis
 */
public class ExtensionData {

    private String key;
    
    private String value;
    /**
     * Default constructor
     */
    public ExtensionData() {
       
    }

    public ExtensionData(String key, String value) {
        this.key = key;
        this.value = value;
    }
    
    public void setKey(String key) {
        if (key != null && key.length() > 0)
            this.key = key;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public void setValue(String value) {
        if (value != null && value.length() > 0)
            this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
}
