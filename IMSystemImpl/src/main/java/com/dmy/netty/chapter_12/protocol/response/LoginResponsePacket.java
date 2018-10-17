package com.dmy.netty.chapter_12.protocol.response;

import com.dmy.netty.chapter_12.protocol.Packet;
import com.dmy.netty.chapter_12.protocol.command.Command;
import lombok.Data;

@Data
public class LoginResponsePacket extends Packet {
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
