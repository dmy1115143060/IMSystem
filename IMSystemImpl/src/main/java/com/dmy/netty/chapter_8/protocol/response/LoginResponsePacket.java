package com.dmy.netty.chapter_8.protocol.response;

import com.dmy.netty.chapter_8.protocol.Packet;
import com.dmy.netty.chapter_8.protocol.command.Command;
import lombok.Data;

/**
 * 登录响应数据包
 */
@Data
public class LoginResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
