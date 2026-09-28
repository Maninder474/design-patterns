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


public enum Status {
    NOT_STARTED {
        @Override
        public boolean isApplicable(Status nextStatus) {
            return true;
        }
    },
    STARTING {
        @Override
        public boolean isApplicable(Status nextStatus) {
            switch (nextStatus) {
                case NOT_STARTED:
                    return false;
            }

            return true;
        }
    },
    IN_PROGRESS {
        @Override
        public boolean isApplicable(Status nextStatus) {
            switch (nextStatus) {
                case NOT_STARTED:
                case STARTING:
                    return false;
            }

            return true;
        }
    },
    FAILED {
        @Override
        public boolean isApplicable(Status nextStatus) {
            switch (nextStatus) {
                case CANCELED:
                    return true;
            }

            return false;
        }
    },
    SUCCEED {
        @Override
        public boolean isApplicable(Status nextStatus) {
            switch (nextStatus) {
                case CANCELED:
                    return true;
            }

            return false;
        }
    },
    CANCELING {
        @Override
        public boolean isApplicable(Status nextStatus) {
            switch (nextStatus) {
                case CANCELING:
                case CANCELED:
                    return true;
            }

            return false;
        }
    },
    CANCELED;

    public boolean isApplicable(Status nextStatus) {
        return false;
    }
}
