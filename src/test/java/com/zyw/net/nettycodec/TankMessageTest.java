package com.zyw.net.nettycodec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

public class TankMessageTest {

    @Test
    public void encode() {
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new TankMsgEncoder());
        TankMsg tm = new TankMsg(5, 8);
        channel.writeOutbound(tm);
        ByteBuf buf = channel.readOutbound();
        int x = buf.readInt();
        int y = buf.readInt();

        assert x == 5;
        assert y == 8;
    }

    @Test
    public void decode() {
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new TankMsgDecoder());
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(5);
        buf.writeInt(8);
        channel.writeInbound(buf);
        TankMsg tm = channel.readInbound();
        assert tm.getX() == 5;
        assert tm.getY() == 8;
    }
}
