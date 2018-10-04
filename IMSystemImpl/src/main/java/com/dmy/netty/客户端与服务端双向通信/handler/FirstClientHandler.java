package com.dmy.netty.客户端与服务端双向通信.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created by DMY on 2018/10/2 14:12
 * 实现客户端向服务端写数据
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 客户端连接建立成功之后，调用到 channelActive() 方法，此时客户端会向服务端发送一条数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(new Date() + ": 客户端写出数据!");
        ByteBuf buf = getByteBuf(ctx);
        // 向Channel中写数据
        ctx.channel().writeAndFlush(buf);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        // 获取Channel对应的缓冲区，每个Channel对应一个ChannelHandlerContext
        ByteBuf buf = ctx.alloc().buffer();
        byte[] bytes = "Hello Server!".getBytes(Charset.forName("UTF-8"));
        // 向缓冲区中填充数据
        buf.writeBytes(bytes);
        return buf;
    }

    /**
     * 接收服务器发来的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println(new Date() + ": 客户端收到数据 -》 " + buf.toString(Charset.forName("UTF-8")));
    }
}
