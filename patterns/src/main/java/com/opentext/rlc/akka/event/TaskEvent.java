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


public class TaskEvent extends TaskCollectionAwareEvent {
    private final EventType type;
    private final long taskId;
    private final State state;
    private final String message;
    private final String executionUrl;

    public TaskEvent(EventType type, long taskCollectionId, long taskId, State state, String message, String executionUrl) {
        super(taskCollectionId);
        this.type = type;
        this.taskId = taskId;
        this.state = state;
        this.message = message;
        this.executionUrl = executionUrl;
    }

    public EventType getType() {
        return type;
    }

    public long getTaskId() {
        return taskId;
    }

    public State getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    public String getExecutionUrl() {
        return executionUrl;
    }

    @Override
    public int getPriority() {
        switch (type) {
            case START:
            case RUN:
                return HIGH_PRIORITY;
            case CHECK:
                return LOW_PRIORITY;
            case CHANGE_STATE:
            default:
                return NORMAL_PRIORITY;

        }
    }

    @Override
    public String toString() {
        return "TaskEvent{" +
                "type=" + type +
                ", " + super.toString() +
                ", taskId=" + taskId +
                ", state=" + state +
                ", message='" + message + '\'' +
                '}';
    }
}
