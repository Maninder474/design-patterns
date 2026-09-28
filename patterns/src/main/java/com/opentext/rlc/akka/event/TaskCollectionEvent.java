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


import com.opentext.rlc.akka.state.EventType;

public class TaskCollectionEvent extends TaskCollectionAwareEvent {
    private final EventType type;
    private final Status status;
    private final String ssoToken;

    public TaskCollectionEvent(EventType type, long taskCollectionId, Status status, String ssoToken) {
        super(taskCollectionId);
        this.type = type;
        this.status = status;
        this.ssoToken = ssoToken;
    }

    public EventType getType() {
        return type;
    }

    public Status getStatus() {
        return status;
    }

    public String getSsoToken() {
        return ssoToken;
    }

    @Override
    public int getPriority() {
        switch (type) {
            case CHECK:
                return LOW_PRIORITY;
            case START:
                return HIGH_PRIORITY;
            case CHANGE_STATE:
            default:
                return NORMAL_PRIORITY;

        }
    }

    @Override
    public String toString() {
        return "TaskCollectionEvent{" +
                "type=" + type +
                ", " + super.toString() +
                ", status=" + status +
                '}';
    }
}
