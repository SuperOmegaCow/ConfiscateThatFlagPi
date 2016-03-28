package com.ctf.protocol.packets;


import com.ctf.protocol.AbstractPacketHandler;
import com.ctf.protocol.DefinedPacket;
import io.netty.buffer.ByteBuf;

/**
 * Robot -> Computer
 */
public class Line extends DefinedPacket {

    /**
     * location = 0 front line detection
     * location = 1 back line detection
     * location = 2 front and back line detection
     */
    private byte location;

    public Line() {

    }

    public Line(byte location) {
        this.location = location;
    }

    @Override
    public void handle(AbstractPacketHandler handler) throws Exception {
        handler.handle(this);
    }

    @Override
    public void read(ByteBuf buf) {
        this.location = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(this.location);
    }

    public byte getLocation() {
        return location;
    }

    public void setLocation(byte location) {
        this.location = location;
    }

}
