package com.ctf.util;

/**
 * code = 0 disconnecting no reason given, end connection <br>
 * code = 1 disconnecting computer is inactive, end connection <br>
 * code = 6 disconnecting unknown error
 * code = 10 reboot robot, causes robot to restart <br>
 * code = 11 update robot, cause robot to end connection and try to update, reestablish connection after update <br>
 * code = 12 ping robot/computer, used to calculate response time
 * code = 100 unknown error, end connection <br>
 * code = 101 aborting login, server didn't respond with proper response <br>
 * code = 102 controller already connected, end connection
 */

public enum StatusUpdates {

    DISCONNECT_NO_REASON(0),
    DISCONNECT_COMPUTER_INACTIVE(1),
    DISCONNECT_UNKNOWN_ERROR(6),

    REBOOT(10),
    UPDATE(11),
    PING(12),

    UNKNOWN_ERROR(100),
    ABORT_LOGIN_INCORRECT_RESPONSE(101),
    ABORT_LOGIN_CONTROLLER_CONNECTED(102);

    private int status;

    private StatusUpdates(int value) {
        this.status = value;
    }

    public int getStatus() {
        return this.status;
    }

    public static StatusUpdates getFromValue(int value) {
        for(StatusUpdates statusUpdates : values()) {
            if (statusUpdates.getStatus() == value)
                return statusUpdates;
        }
        return null;
    }

}
