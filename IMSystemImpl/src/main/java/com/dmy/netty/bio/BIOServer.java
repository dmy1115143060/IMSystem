package com.dmy.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by DMY on 2018/9/30 17:41
 * 传统BIO客户端服务器模型
 */
public class BIOServer {
    public static void main(String[] args) throws Exception {
        // 监听8000端口，接受客户端的连接
        ServerSocket serverSocket = new ServerSocket(8000);

        // 启用一个线程来接收客户端的连接
        new Thread(() -> {
            while (true) {
                try {
                    // 调用accept方法来等待客户端的连接，如无连接，此时会一直阻塞
                    Socket socket = serverSocket.accept();
                    // 对于每一个客户端的连接服务器创建一个线程来进行处理
                    new Thread(() -> {
                        try {
                            int len;
                            // 读取客户端发送的数据
                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();
                            while ((len = inputStream.read(data)) != -1) {
                                System.out.println(new String(data, 0, len));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
