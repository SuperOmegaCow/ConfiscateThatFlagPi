package com.ctf.logic;

import com.ctf.protocol.packets.Status;
import com.ctf.util.ConnectionType;
import com.ctf.util.StatusUpdates;

import java.util.ArrayList;
import java.util.List;

public class ConfiscateThatFlagPi {

    public static ConfiscateThatFlagPi instance;

    private List<UserConnection> connected = new ArrayList<>();
    private List<UserConnection> rawData = new ArrayList<>();
    private List<UserConnection> processedData = new ArrayList<>();
    private UserConnection controller = null;

    public static ConfiscateThatFlagPi getInstance() {
        return instance;
    }

    public ConfiscateThatFlagPi() {
        instance = this;
    }

    public void setConnectionType(UserConnection userConnection, ConnectionType connectionType) {
        switch (connectionType) {
            case LOGIN_INCOMPLETE:
                if (rawData.contains(userConnection))
                    rawData.remove(userConnection);
                if (processedData.contains(userConnection))
                    processedData.remove(userConnection);
                break;
            case CONTROL_ROBOT:
                if (controller == null)
                    controller = null;
                else {
                    if (!controller.equals(userConnection)) {
                        disconnect(userConnection, StatusUpdates.ABORT_LOGIN_CONTROLLER_CONNECTED);
                    }
                }
                break;

        }
        if (!connected.contains(userConnection))
            connected.add(userConnection);

    }

    public void disconnect(UserConnection userConnection, StatusUpdates statusUpdates) {
        this.connected.remove(userConnection);
        if (rawData.contains(userConnection))
            rawData.remove(userConnection);
        if (processedData.contains(userConnection))
            processedData.remove(userConnection);
        if (controller.equals(userConnection))
            controller = null;
        userConnection.getChannel().write(new Status(statusUpdates));
        userConnection.getChannel().close();
    }

    public void addConnection(UserConnection userConnection) {
        this.setConnectionType(userConnection, ConnectionType.LOGIN_INCOMPLETE);
    }

}
