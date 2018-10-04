package com.dmy.netty.util;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by DMY on 2018/10/2 14:30
 */
public class NettyUtil {

    private static final int MAX_RETRY = 5;

    /**
     * 绑定一个端口，若端口绑定失败，则尝试端口号自增进行重新绑定
     */
    public static void bind(ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(port).addListener((future) -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功！");
            } else {
                System.out.println("端口[" + port + "]绑定失败！将重试端口[" + (port + 1) + "]!");
                bind(serverBootstrap, port + 1);
            }
        });
    }

    public static void connect(Bootstrap bootstrap, String host, int port) {
        connect(bootstrap, host, port, MAX_RETRY);
    }

    /**
     * 客户端自动重连机制，但是，通常情况下，连接建立失败不会立即重新连接，而是会通过一个指数退避的方式，
     * 比如每隔1 秒、2 秒、4 秒、8 秒，以 2 的幂次来建立连接，然后到达一定次数之后就放弃连接，
     */
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener((futrue) -> {
            if (futrue.isSuccess()) {
                System.out.println("成功连接到服务器！");
            } else if (retry == 0) {
                System.out.println("以达到最大连接次数，连接服务器失败!");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔时间
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连...");
                bootstrap.config()
                        .group()
                        .schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }
}
