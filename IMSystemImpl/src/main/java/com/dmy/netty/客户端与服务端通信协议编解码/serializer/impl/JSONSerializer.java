package com.dmy.netty.客户端与服务端通信协议编解码.serializer.impl;

import com.alibaba.fastjson.JSON;
import com.dmy.netty.客户端与服务端通信协议编解码.serializer.Serializer;
import com.dmy.netty.客户端与服务端通信协议编解码.serializer.SerializerAlgorithm;

/**
 * Created by DMY on 2018/10/2 16:51
 * 使用fastjson来进行序列化与反序列化操作
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
