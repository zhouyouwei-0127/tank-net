package com.zyw.net.nettycodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class TankMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        if (buf.readableBytes() < 8) {
            return;
        }
        int x = buf.readInt();
        int y = buf.readInt();
        //System.out.println("decoder: x:" + x + " y:" + y);
        out.add(new TankMsg(x, y));
    }
}
