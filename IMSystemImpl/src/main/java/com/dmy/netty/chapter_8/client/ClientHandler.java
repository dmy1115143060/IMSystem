package com.dmy.netty.chapter_8.client;

import com.dmy.netty.chapter_8.protocol.Packet;
import com.dmy.netty.chapter_8.protocol.PacketCodeC;
import com.dmy.netty.chapter_8.protocol.request.LoginRequestPacket;
import com.dmy.netty.chapter_8.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

/**
 * Created by DMY on 2018/10/4 12:42
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端连接上服务端之后会回调该方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端开始登录");

        // 创建登录对象数据包
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("flash");
        loginRequestPacket.setPassword("pwd");

        // 对数据包按照编码协议进行编码
        ByteBuf buf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);

        // 发送登录请求给服务器
        ctx.channel().writeAndFlush(buf);
    }

    /**
     * 读取服务端发送的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 接收消息并进行解码
        ByteBuf buf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(buf);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            if (loginResponsePacket.isSuccess()) {
                System.out.println(new Date() + ": 客户端登录成功");
            } else {
                System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
            }
        }
    }
}
