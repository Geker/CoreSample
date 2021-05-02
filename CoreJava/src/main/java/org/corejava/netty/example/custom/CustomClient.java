package org.corejava.netty.example.custom;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;

public class CustomClient {

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bs = new Bootstrap();
        bs.group(new NioEventLoopGroup()).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(
                        new LoggingHandler(),
                        new LengthFieldBasedFrameDecoder(1024, 0,
                                4, 0, 4),
                        new LengthFieldPrepender(4),
                        new LoggingHandler(),
                        new StringEncoder(), new CustomClientHandler()

                );

            }
        });
        // client从last到first进行处理。
        // Message的Encoder和Decoder会进行类型的匹配。不匹配的类型不会在调用链执行
        Channel chnl = bs.connect("localhost", 1024).channel();
        chnl.closeFuture().sync();
    }
}
