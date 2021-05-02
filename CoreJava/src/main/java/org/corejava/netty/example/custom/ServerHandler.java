package org.corejava.netty.example.custom;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

public class ServerHandler extends ByteToMessageCodec<String> {

    /**
     * Server发送的时候的encode
     * (non-Javadoc)
     *
     * @see io.netty.handler.codec.ByteToMessageCodec#encode(io.netty.channel.ChannelHandlerContext, java.lang.Object,
     *      io.netty.buffer.ByteBuf)
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        out.writeBytes(msg.getBytes());
    }

    /**
     * server收到信息之后的decoder
     * (non-Javadoc)
     *
     * @see io.netty.handler.codec.ByteToMessageCodec#decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf,
     *      java.util.List)
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] dst = new byte[in.readableBytes()];
        in.readBytes(dst);
        String str = new String(dst);
        System.err.println("server recv:" + str);
        out.add(str);
    }

}
