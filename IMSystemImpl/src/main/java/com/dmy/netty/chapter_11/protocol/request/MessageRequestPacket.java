package com.dmy.netty.chapter_11.protocol.request;

import com.dmy.netty.chapter_11.protocol.Packet;
import com.dmy.netty.chapter_11.protocol.command.Command;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {

    private String message;

    public MessageRequestPacket(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
