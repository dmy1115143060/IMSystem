package com.dmy.netty.客户端与服务端通信协议编解码.command;

import com.dmy.netty.客户端与服务端通信协议编解码.serializer.impl.JSONSerializer;
import com.dmy.netty.客户端与服务端通信协议编解码.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DMY on 2018/10/2 17:00
 */
public class PacketCodeC {

    /**
     * 魔数，用于区别数据包
     */
    private static final int MAGIC_NUMBER = 0x12345678;

    /**
     * 指令类型
     */
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;

    /**
     * 序列化算法类型与实际序列化算法映射
     */
    private static final Map<Byte, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    /**
     * 协议编码
     */
    public ByteBuf encode(Packet packet) {
        // 1. 创建 ByteBuf 对象
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        // 2. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER); // 魔数
        byteBuf.writeByte(packet.getVersion()); // 协议版本号
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm()); // 序列化算法标识
        byteBuf.writeByte(packet.getCommand()); // 指令标识
        byteBuf.writeInt(bytes.length); // 数据长度
        byteBuf.writeBytes(bytes); // 数据内容

        return byteBuf;
    }

    /**
     * 协议解码
     */
    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 获得序列化算法标识并根据标识获得对应的序列化算法
        byte serializeAlgorithm = byteBuf.readByte();
        Serializer serializer = getSerializer(serializeAlgorithm);

        // 获得指令标识从而得到具体指令实体类
        byte command = byteBuf.readByte();
        Class<? extends Packet> requestType = getRequestType(command);

        // 获取数据包长度与数据内容
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        // 根据序列化算法解析出请求数据包结构Packet
        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
