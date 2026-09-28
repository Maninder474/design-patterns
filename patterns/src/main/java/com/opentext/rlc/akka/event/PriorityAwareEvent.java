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

package com.opentext.rlc.akka.event;

public abstract class PriorityAwareEvent {
    public final static int HIGH_PRIORITY = 0;
    public final static int NORMAL_PRIORITY = 1;
    public final static int LOW_PRIORITY = 2;
    public final static int LAST_PRIORITY = 3;

    abstract public int getPriority();

    @Override
    public String toString() {
        return "priority=" + getPriority();
    }
}
