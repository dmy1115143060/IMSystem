package com.dmy.netty.chapter_5;

import com.dmy.netty.util.NettyUtil;
import com.dmy.netty.chapter_5.handler.FirstClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by DMY on 2018/9/30 22:06
 * 对于客户端的启动来说，和服务端的启动类似，依然需要线程模型、IO 模型，以及IO 业务处理逻辑三大参数
 */
public class NettyClient {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new FirstClientHandler());
                    }
                });
        NettyUtil.connect(bootstrap, "127.0.0.1", 8000);
    }
}
