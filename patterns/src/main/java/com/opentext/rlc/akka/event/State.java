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


public enum State {
    NOT_STARTED {
        @Override
        public boolean isApplicable(State nextState) {
            return true;
        }

        @Override
        public boolean isMutable() {
            return true;
        }
    },
    SCHEDULED {
        @Override
        public boolean isApplicable(State nextState) {
            /*switch (nextState) {
                case NOT_STARTED:
                    return false;
            }*/

            return true;
        }

        @Override
        public boolean isMutable() {
            return true;
        }
    },
    STARTING {
        @Override
        public boolean isApplicable(State nextState) {
            switch (nextState) {
                case NOT_STARTED:
                case STARTING:
                case SCHEDULED:
                    return false;
            }

            return true;
        }

        @Override
        public boolean isMutable() {
            return true;
        }
    },
    PENDING {
        @Override
        public boolean isApplicable(State nextState) {
            switch (nextState) {
                case NOT_STARTED:
                case STARTING:
                case SCHEDULED:
                    return false;
            }

            return true;
        }

        @Override
        public boolean isMutable() {
            return true;
        }
    },
    IN_PROGRESS {
        @Override
        public boolean isApplicable(State nextState) {
            switch (nextState) {
                case NOT_STARTED:
                case STARTING:
                case PENDING:
                    return false;
            }

            return true;
        }

        @Override
        public boolean isMutable() {
            return true;
        }
    },
    BLOCKED {
        @Override
        public boolean isApplicable(State nextState) {
            switch (nextState) {
                case NOT_STARTED:
                case STARTING:
                case PENDING:
                    return false;
            }

            return true;
        }

        @Override
        public boolean isMutable() {
            return true;
        }
    },
    COMPLETED {
        @Override
        public boolean isApplicable(State nextState) {
            return false;
        }
    },
    FAILED {
        @Override
        public boolean isApplicable(State nextState) {
            return false;
        }
    },
    CANCELING {
        @Override
        public boolean isApplicable(State nextState) {
            switch (nextState) {
                case CANCELED:
                    return true;
            }

            return false;
        }
    },
    CANCELED {
        @Override
        public boolean isApplicable(State nextState) {
            return false;
        }
    },
    SKIPPED {
        @Override
        public boolean isApplicable(State nextState) {
            return false;
        }
    };

    public boolean isApplicable(State nextState) {
        return false;
    }

    public boolean isMutable() {
        return false;
    }
}
