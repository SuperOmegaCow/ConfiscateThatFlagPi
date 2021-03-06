package com.ctf.util;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;

public class ChannelWrapper {

    private final Channel ch;
    private volatile boolean closed;

    public ChannelWrapper(Channel ch) {
        this.ch = ch;
    }

    public boolean isClosed() {
        return closed;
    }

    public void write(Object packet) {
        if (!closed) {
            if (packet instanceof PacketWrapper) {
                ((PacketWrapper) packet).setReleased(true);
                ch.write(((PacketWrapper) packet).buf, ch.voidPromise());
            } else {
                ch.write(packet, ch.voidPromise());
            }
            ch.flush();
        }
    }

    public void close() {
        if (!closed) {
            closed = true;
            ch.flush();
            ch.close();
        }
    }
    
    public Channel getHandle() {
        return ch;
    }
}