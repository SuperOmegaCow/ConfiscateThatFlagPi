package com.ctf.protocol.packets;


import com.ctf.protocol.AbstractPacketHandler;
import com.ctf.protocol.DefinedPacket;
import io.netty.buffer.ByteBuf;

/**
 * Robot -> Computer
 */
public class Gyroscope extends DefinedPacket {

    private float Gx;
    private float Gy;

    public Gyroscope() {

    }

    public Gyroscope(float gx, int gy) {
        this.Gx = gx;
        this.Gy = gy;
    }

    @Override
    public void handle(AbstractPacketHandler handler) throws Exception {
        handler.handle(this);
    }

    @Override
    public void read(ByteBuf buf) {
        this.Gx = buf.readFloat();
        this.Gy = buf.readFloat();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeFloat(this.Gx);
        buf.writeFloat(this.Gy);
    }

    public float getGx() {
        return this.Gx;
    }

    public void setGx(float gx) {
        this.Gx = gx;
    }

    public float getGy() {
        return this.Gy;
    }

    public void setGy(float gy) {
        this.Gy = gy;
    }

}
