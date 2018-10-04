package com.dmy.netty.chapter_8.protocol.command;

/**
 * Created by DMY on 2018/10/4 12:45
 */
public interface Command {
    /**
     * 登录指令
     */
    Byte LOGIN_REQUEST = 1;

    /**
     * 登录响应指令
     */
    Byte LOGIN_RESPONSE = 2;
}
