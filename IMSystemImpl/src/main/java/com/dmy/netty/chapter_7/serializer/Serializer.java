package com.dmy.netty.chapter_7.serializer;

import com.dmy.netty.chapter_7.serializer.impl.JSONSerializer;

/**
 * Created by DMY on 2018/10/2 16:50
 * 序列化接口
 */
public interface Serializer {

    /**
     * JSON序列化作为默认的序列化算法
     */
    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
