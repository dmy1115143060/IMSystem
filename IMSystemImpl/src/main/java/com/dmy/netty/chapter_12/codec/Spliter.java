package com.dmy.netty.chapter_12.codec;

import com.dmy.netty.chapter_12.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 按照自定义协议来解决使用Netty过程中数据的粘包问题以及对数据进行过滤
 */
public class Spliter extends LengthFieldBasedFrameDecoder {
    /**
     * 数据的偏移长度与数据字段的长度
     * 自定义协议：魔数(4字节) + 版本号(1字节) + 序列化算法标识(1字节) + 指令(1字节) + 数据长度(4字节) + 数据
     */
    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter() {
        // 调用LengthFieldBasedFrameDecoder的构造方法
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 屏蔽非被协议的数据包，此时传输进来的数据是协议的起始魔数部分
        if (in.getInt(in.readerIndex()) != PacketCodeC.MAGIC_NUMBER) {
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
