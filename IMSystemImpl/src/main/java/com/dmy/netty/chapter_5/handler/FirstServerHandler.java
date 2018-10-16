package com.dmy.netty.chapter_5.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created by DMY on 2018/10/2 14:18
 * 服务端读取客户端的数据
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 这个方法在接收到客户端发来的数据之后被回调
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println(new Date() + ": 服务端读到数据 -》 " + buf.toString(Charset.forName("UTF-8")));

        // 服务器回写数据给客户端
        System.out.println(new Date() + ": 服务端写出数据!");
        buf = getByteBuf(ctx);
        ctx.channel().writeAndFlush(buf);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        ByteBuf buf = ctx.alloc().buffer();
        byte[] bytes = "Hello Client!".getBytes(Charset.forName("UTF-8"));
        buf.writeBytes(bytes);
        return buf;
    }
}
