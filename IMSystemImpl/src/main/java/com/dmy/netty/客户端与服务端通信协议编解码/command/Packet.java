package com.dmy.netty.客户端与服务端通信协议编解码.command;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by DMY on 2018/10/2 16:35
 */
@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version;

    /**
     * 指令
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
