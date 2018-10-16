package com.dmy.netty.chapter_9.server;

import com.dmy.netty.chapter_9.protocol.Packet;
import com.dmy.netty.chapter_9.protocol.PacketCodeC;
import com.dmy.netty.chapter_9.protocol.request.LoginRequestPacket;
import com.dmy.netty.chapter_9.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println(new Date() + ": 客户端开始登录……");
        ByteBuf requestByteBuf = (ByteBuf) msg;

        // 获取客户端的请求数据包
        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);

        // 处理客户端的登录请求
        if (packet instanceof LoginRequestPacket) {
            // 获取登录请求数据包
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            // 构建登录请求响应数据包
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());
            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date() + ": 登录成功!");
            } else {
                loginResponsePacket.setReason("账号密码校验失败");
                loginResponsePacket.setSuccess(false);
                System.out.println(new Date() + ": 登录失败!");
            }
            // 登录响应
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    /**
     * 账号密码校验逻辑
     */
    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
