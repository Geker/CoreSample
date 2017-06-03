package io.netty.example.custom;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LoggingHandler;

public class CustomServer {

    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap sbs = new ServerBootstrap();
        sbs.group(new NioEventLoopGroup(10)).channel(NioServerSocketChannel.class).handler(new LoggingHandler())
            .childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipe = ch.pipeline();
                    pipe.addLast(new LoggingHandler());
                    pipe.addLast(new LengthFieldPrepender(4));
                    pipe.addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4));
                    // pipe.addLast(new LoggingHandler());
                    pipe.addLast(new ServerHandler());
                    // pipe.addLast(new LoggingHandler());
                    pipe.addLast(new ObjectEchoServerHandler());
                }
            });
        // Server的in从first到last处理。out从last到first处理
        Channel chnl = sbs.bind(1024).sync().channel();
        chnl.closeFuture().sync();

    }


}
