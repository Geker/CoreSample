/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.corejava.netty.example.localecho;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public final class LocalEcho {

	private static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));
	static final String HOST = System.getProperty("host", "127.0.0.1");

	public static void main(String[] args) throws Exception {
		// Address to bind on / connect to.
		EventLoopGroup serverGroup = new NioEventLoopGroup(1);
		EventLoopGroup serverWorkGroup = new NioEventLoopGroup();
		EventLoopGroup clientGroup = new NioEventLoopGroup(); // NIO event loops are also OK
		try {
			// Note that we can use any event loop to ensure certain local channels
			// are handled by the same event loop thread which drives a certain socket
			// channel
			// to reduce the communication latency between socket channels and local
			// channels.
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(serverGroup, serverWorkGroup).channel(NioServerSocketChannel.class)
					.handler(new ChannelInitializer<NioServerSocketChannel>() {
						@Override
						public void initChannel(NioServerSocketChannel ch) throws Exception {
							ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
						}
					}).childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO), new LocalEchoServerHandler());
						}
					});

			Bootstrap cb = new Bootstrap();
			cb.group(clientGroup)

					.channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new LoggingHandler(LogLevel.TRACE),new StringEncoder(), new LocalEchoClientHandler());
						}
					});

			// Start the server.
			sb.bind(PORT).sync();

			// Start the client.
			Channel ch = cb.connect(HOST, PORT).sync().channel();

 
 			ChannelFuture lastWriteFuture = null;
 //        
			String line = "xtxt110";

			// Sends the received line to the server.
			lastWriteFuture = ch.writeAndFlush(line).sync();
//    

			// Wait until all messages are flushed before closing the channel.
			if (lastWriteFuture != null) {
				lastWriteFuture.awaitUninterruptibly();
			}
			System.in.read();
			System.out.println("end....");
		} finally {
			serverGroup.shutdownGracefully();
			clientGroup.shutdownGracefully();
		}
	}
}
