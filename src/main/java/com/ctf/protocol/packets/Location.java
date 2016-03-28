package com.ctf.protocol.packets;

import com.ctf.protocol.AbstractPacketHandler;
import com.ctf.protocol.DefinedPacket;
import io.netty.buffer.ByteBuf;

/**
 * Robot <-> Computer <br>
 * Robot -> Computer = predicted location by the robot
 * Computer -> Robot = set robot's predicted location
 */
public class Location extends DefinedPacket {

    private float x;
    private float y;
    /**
     * x component of the direction vector
     */
    private float Dx;
    /**
     * y component of the direction vector
     */
    private float Dy;

    public Location() {

    }

    public Location(float x, float y, float Dx, float Dy) {
        this.x = x;
        this.y = y;
        this.Dx = Dx;
        this.Dy = Dy;
    }

    @Override
    public void handle(AbstractPacketHandler handler) throws Exception {
        handler.handle(this);
    }

    @Override
    public void read(ByteBuf buf) {
        this.x = buf.readFloat();
        this.y = buf.readFloat();
        this.Dx = buf.readFloat();
        this.Dy = buf.readFloat();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeFloat(this.x);
        buf.writeFloat(this.y);
        buf.writeFloat(this.Dx);
        buf.writeFloat(this.Dy);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getDx() {
        return Dx;
    }

    public void setDx(float dx) {
        Dx = dx;
    }

    public float getDy() {
        return Dy;
    }

    public void setDy(float dy) {
        Dy = dy;
    }

}
