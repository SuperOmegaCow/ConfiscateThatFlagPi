package com.ctf.util;

import com.ctf.logic.UserConnection;
import com.ctf.protocol.Decoder;
import com.ctf.protocol.Encoder;
import com.ctf.protocol.Varint21FrameDecoder;
import com.ctf.protocol.Varint21LengthFieldPrepender;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.util.AttributeKey;

public class PipelineUtils {

    private static final Varint21LengthFieldPrepender framePrepender = new Varint21LengthFieldPrepender();

    public static final AttributeKey<UserConnection> USER = new AttributeKey<>("User");

    public static final String FRAME_DECODER = "frame-decoder";
    public static final String PACKET_DECODER = "packet-decoder";
    public static final String FRAME_ENCODER = "frame-encoder";
    public static final String PACKET_ENCODER = "packet-encoder";
    public static final String PACKET_HANDLER = "packet-handler";

    public static final ChannelInitializer<Channel> CHANNEL_INITIALIZER = new ChannelInitializer<Channel>() {
        @Override
        protected void initChannel(Channel channel) throws Exception {
            ChannelPipeline pipeline = channel.pipeline();
            pipeline.addLast(FRAME_DECODER, new Varint21FrameDecoder());
            pipeline.addLast(PACKET_DECODER, new Decoder());
            pipeline.addLast(FRAME_ENCODER, framePrepender);
            pipeline.addLast(PACKET_ENCODER, new Encoder());
            UserConnection userConnection = new UserConnection(channel);
            pipeline.addLast(PACKET_HANDLER, userConnection);
        }
    };

}
