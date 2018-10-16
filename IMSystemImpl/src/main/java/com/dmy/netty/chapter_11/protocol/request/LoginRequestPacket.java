package com.dmy.netty.chapter_11.protocol.request;

import com.dmy.netty.chapter_11.protocol.Packet;
import com.dmy.netty.chapter_11.protocol.command.Command;
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
