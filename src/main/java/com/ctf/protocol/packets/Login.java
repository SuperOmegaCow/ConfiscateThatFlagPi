package com.ctf.protocol.packets;

import com.ctf.protocol.AbstractPacketHandler;
import com.ctf.protocol.DefinedPacket;
import io.netty.buffer.ByteBuf;


/**
 * Robot <-> Computer <br>
 * Login is successful if computer receives same intent from server as sent
 */
public class Login extends DefinedPacket {

    /**
     * intent = 0 connector has intent of controlling robot directly <br>
     * intent = 1 connector has intent of raw data receiving <br>
     * intent = 2 connector has intent of calculated data receiving <br>
     * intent = 3 connector has intent of assisting robot in calculations
     */
    private byte intent;

    public Login() {

    }

    public Login(byte intent) {
        this.intent = intent;
    }

    @Override
    public void read(ByteBuf buf) {
        this.intent = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(this.intent);
    }

    @Override
    public void handle(AbstractPacketHandler handler) throws Exception {
        handler.handle(this);
    }

    public byte getIntent() {
        return intent;
    }

    public void setIntent(byte intent) {
        this.intent = intent;
    }
}
