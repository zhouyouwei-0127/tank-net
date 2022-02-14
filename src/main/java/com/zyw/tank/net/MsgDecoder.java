package com.zyw.tank.net;

import com.zyw.tank.net.msg.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
        if (buf.readableBytes() < 8) return;

        buf.markReaderIndex();
        MsgType msgType = MsgType.values()[buf.readInt()];
        int length = buf.readInt();
        if (buf.readableBytes() < length) {
            buf.resetReaderIndex();
            return;
        }

        byte[] bytes = new byte[length];
        buf.readBytes(bytes);

        Msg msg = getMsgByMsgType(msgType);
        msg.parse(bytes);

        list.add(msg);
    }

    private Msg getMsgByMsgType(MsgType msgType) {
        Msg msg = null;
        switch (msgType) {
            case TANK_JOIN:
                msg = new TankJoinMsg();
                break;
            case TANK_MOVE_OR_DIR_CHANGE:
                msg = new TankMoveOrDirChangeMsg();
                break;
            case TANK_STOP:
                msg = new TankStopMsg();
                break;
            case BULLET_NEW:
                msg = new BulletNewMsg();
                break;
        }
        return msg;
    }
}
