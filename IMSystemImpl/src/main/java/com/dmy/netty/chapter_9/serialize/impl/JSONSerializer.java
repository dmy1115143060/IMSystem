package com.dmy.netty.chapter_9.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.dmy.netty.chapter_9.serialize.Serializer;
import com.dmy.netty.chapter_9.serialize.SerializerAlogrithm;

public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlogrithm() {
        return SerializerAlogrithm.JSON;
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
