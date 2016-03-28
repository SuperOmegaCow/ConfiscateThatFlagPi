package com.ctf.protocol.packets;


import com.ctf.protocol.AbstractPacketHandler;
import com.ctf.protocol.DefinedPacket;
import io.netty.buffer.ByteBuf;

/**
 * Robot -> Computer
 */
public class Accelerometer extends DefinedPacket {

    private float Ax;
    private float Ay;

    public Accelerometer() {

    }

    public Accelerometer(float ax, float ay) {
        this.Ax = ax;
        this.Ay = ay;
    }

    @Override
    public void read(ByteBuf buf) {
        this.Ax = buf.readFloat();
        this.Ay = buf.readFloat();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeFloat(this.Ax);
        buf.writeFloat(this.Ay);
    }

    @Override
    public void handle(AbstractPacketHandler handler) throws Exception {
        handler.handle(this);
    }

    public float getAx() {
        return Ax;
    }

    public void setAx(float ax) {
        Ax = ax;
    }

    public float getAy() {
        return Ay;
    }

    public void setAy(float ay) {
        Ay = ay;
    }

}
