package com.dmy.netty.nettylearning;

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
 * 要启动一个Netty服务端，必须要指定三类属性，分别是线程模型、IO 模型、连接读写处理逻辑
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
                // handler()用于指定在服务端启动过程中的一些逻辑，通常情况下呢，我们用不着这个方法。
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel nioServerSocketChannel) {
                        System.out.println("服务器启动中！");
                    }
                })
                // childHandler主要就是定义后续每条连接的数据读写，业务处理逻辑
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
                });
        bind(serverBootstrap, 1000);
    }

    /**
     * 绑定一个端口，若端口绑定失败，则尝试端口号自增进行重新绑定
     */
    private static void bind(ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(port).addListener((future) -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功！");
            } else {
                System.out.println("端口[" + port + "]绑定失败！将重试端口[" + (port + 1) + "]!");
                bind(serverBootstrap, port + 1);
            }
        });
    }
}
