package com.ctf.protocol.packets;

import com.ctf.util.StatusUpdates;
import com.ctf.protocol.AbstractPacketHandler;
import com.ctf.protocol.DefinedPacket;
import io.netty.buffer.ByteBuf;

/**
 * Robot <-> Robot <br>
 * Sent when status is updated, occurs in login or normal use
 */
public class Status extends DefinedPacket {

    private int code;

    public Status() {

    }

    public Status(int code) {
        this.code = code;
    }

    public Status(StatusUpdates statusUpdates) {
        this.code = statusUpdates.getStatus();
    }

    @Override
    public void read(ByteBuf buf) {
        this.code = readVarInt(buf);
    }

    @Override
    public void write(ByteBuf buf) {
        writeVarInt(this.code, buf);
    }

    @Override
    public void handle(AbstractPacketHandler handler) throws Exception {
        handler.handle(this);
    }

    public int getCode() {
        return code;
    }

    public StatusUpdates getStatusUpdates() {
        return StatusUpdates.getFromValue(this.code);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setCode(StatusUpdates statusUpdates) {
        this.code = statusUpdates.getStatus();
    }

}
