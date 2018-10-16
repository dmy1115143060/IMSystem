package com.dmy.netty.chapter_9.protocol.request;

import com.dmy.netty.chapter_9.protocol.Packet;
import com.dmy.netty.chapter_9.protocol.command.Command;
import lombok.Data;


@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
