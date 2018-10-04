package com.dmy.netty.chapter_8.protocol.request;

import com.dmy.netty.chapter_8.protocol.command.Command;
import com.dmy.netty.chapter_8.protocol.Packet;
import lombok.Data;

@Data
public class LoginRequestPacket extends Packet {
    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
