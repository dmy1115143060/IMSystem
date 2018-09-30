package com.dmy.netty.bio;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * Created by DMY on 2018/9/30 17:42
 */
public class BIOClient {
    public static void main(String[] args) {
        // 启动一个客户端线程，每隔2S发送一条消息
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + ": hello world").getBytes());
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
