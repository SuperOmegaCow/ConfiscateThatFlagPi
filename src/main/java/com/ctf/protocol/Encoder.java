package com.ctf.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class Encoder extends MessageToByteEncoder<DefinedPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, DefinedPacket msg, ByteBuf out) throws Exception {
        DefinedPacket.writeVarInt(PacketProtocol.OUTBOUND.getId(msg.getClass()), out);
        msg.write(out);
    }
}
