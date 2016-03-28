package com.ctf.protocol;

import com.ctf.util.PacketWrapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class Decoder extends ByteToMessageDecoder {

    public Decoder() {

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ByteBuf copy = in.copy();
        int packetId = DefinedPacket.readVarInt(in);
        DefinedPacket packet = null;
        if (PacketProtocol.INBOUND.hasPacket(packetId)) {
            packet = PacketProtocol.INBOUND.createPacket(packetId);
            packet.read(in);
            if (in.readableBytes() != 0) {
                throw new BadPacketException("Did not read all bytes from packet " + packet.getClass() + " " + packetId + " Direction " + PacketProtocol.INBOUND);
            }
        } else {
            in.skipBytes(in.readableBytes());
        }
        out.add(new PacketWrapper(packet, copy));
    }

}
