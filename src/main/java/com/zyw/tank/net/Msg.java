package com.zyw.tank.net;

/**
 * 所有消息的父类
 */
public abstract class Msg {

    abstract byte[] toBytes();

    abstract void parse(byte[] bytes);

    abstract void handle();

    abstract MsgType getMsgType();
}
