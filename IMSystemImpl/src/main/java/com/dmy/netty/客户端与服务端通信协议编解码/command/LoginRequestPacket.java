package com.dmy.netty.客户端与服务端通信协议编解码.command;

import lombok.Data;

/**
 * Created by DMY on 2018/10/2 16:48
 * 登录请求数据包
 */
@Data
public class LoginRequestPacket extends Packet {

    private String userId;
    private String userName;
    private String password;

    /**
     * 对应的指令类型为：
     * @see com.dmy.netty.客户端与服务端通信协议编解码.command.Command
     */
    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
