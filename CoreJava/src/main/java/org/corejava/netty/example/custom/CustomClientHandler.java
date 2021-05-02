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
package org.corejava.netty.example.custom;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * Handler implementation for the echo client. It initiates the ping-pong
 * traffic between the echo client and server by sending the first message to
 * the server.
 */
public class CustomClientHandler extends ChannelInboundHandlerAdapter {
    Logger logger
            = LoggerFactory.getLogger(CustomClientHandler.class);
    private final ByteBuf firstMessage;
    private ByteBuf buff;

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private final String msg;
    private LongAdder longAdder=new LongAdder();

    /**
     * Creates a client-side handler.
     *
     * @throws UnsupportedEncodingException
     */
    public CustomClientHandler() throws UnsupportedEncodingException {
        // firstMessage = Unpooled.buffer(EchoClient.SIZE);
        // for (int i = 0; i < firstMessage.capacity(); i++) {
        // firstMessage.writeByte((byte) i);
        // }
        msg = "hello netty";
        byte[] bytes = msg.getBytes("UTF-8");
        firstMessage = Unpooled.buffer(bytes.length);

        firstMessage.writeBytes(bytes);


    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] bytes = ByteBufUtil.getBytes((ByteBuf) msg);
        System.err.println(new String(bytes));
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush("hello String");

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            ctx.writeAndFlush(msg);
            firstMessage.retain();
        }, 1, 1, TimeUnit.SECONDS);

    }
}