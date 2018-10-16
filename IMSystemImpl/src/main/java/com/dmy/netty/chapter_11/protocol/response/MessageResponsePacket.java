package com.dmy.netty.chapter_11.protocol.response;

import com.dmy.netty.chapter_11.protocol.Packet;
import com.dmy.netty.chapter_11.protocol.command.Command;
import lombok.Data;

@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
