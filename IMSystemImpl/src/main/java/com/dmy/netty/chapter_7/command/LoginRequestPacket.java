package com.dmy.netty.chapter_7.command;

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
     * 获取对应的登录指令类型
     * @see com.dmy.netty.chapter_7.command.Command
     */
    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
