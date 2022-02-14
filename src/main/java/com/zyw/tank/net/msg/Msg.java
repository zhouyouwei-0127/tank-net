package com.zyw.tank.net.msg;

import com.zyw.tank.net.msg.MsgType;

/**
 * 所有消息的父类
 */
public abstract class Msg {

    public abstract byte[] toBytes();

    public abstract void parse(byte[] bytes);

    public abstract void handle();

    public abstract MsgType getMsgType();
}
