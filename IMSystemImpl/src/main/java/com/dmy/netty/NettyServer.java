package com.dmy.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by DMY on 2018/9/30 21:55
 */
public class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 这两个对象可以看做是传统IO编程模型的两大线程组，boss表示监听端口，accept新连接的线程组，
        // worker表示处理每一条连接的数据读写的线程组,内部采用轮询的方式，实际上每个线程都会带上一个Selector
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap.group(boss, worker)
                // NioServerSocketChannel和NioSocketChannel的概念可以和BIO编程模型中的
                // ServerSocket以及Socket两个概念对应上
                .channel(NioServerSocketChannel.class)
                // 这里主要就是定义后续每条连接的数据读写，业务处理逻辑
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) {
                        channel.pipeline().addLast(new StringDecoder());
                        channel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) {
                                System.out.println(msg);
                            }
                        });
                    }
                }).bind(8000);

    }
}
