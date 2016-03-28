package com.ctf.logic;

import com.ctf.protocol.BadPacketException;
import com.ctf.util.ChannelWrapper;
import com.ctf.util.ConnectionType;
import com.ctf.util.PacketWrapper;
import com.ctf.util.PipelineUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.ReadTimeoutException;

import java.io.IOException;

public class UserConnection extends ChannelInboundHandlerAdapter {

    private ConnectionType connectionType;
    private ChannelWrapper channel;
    private PacketHandler packetHandler;

    public UserConnection(Channel channel) {
        this.channel = new ChannelWrapper(channel);
        channel.attr(PipelineUtils.USER).set(this);
        ConfiscateThatFlagPi.getInstance().addConnection(this);
        this.packetHandler = new PacketHandler(channel);
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    public ChannelWrapper getChannel() {
        return channel;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PacketWrapper packet = (PacketWrapper) msg;
        try {
            if (packet.packet != null) {
                packet.packet.handle(this.packetHandler);
            }
        } finally {
            packet.trySingleRelease();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (ctx.channel().isActive()) {
            if (cause instanceof ReadTimeoutException) {

            } else if (cause instanceof BadPacketException) {

            } else if (cause instanceof IOException) {

            } else {

            }
            ctx.close();
        }
    }

}
