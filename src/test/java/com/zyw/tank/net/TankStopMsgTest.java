package com.zyw.tank.net;

import com.zyw.tank.net.msg.MsgType;
import com.zyw.tank.net.msg.TankStopMsg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

/**
 * test TankStopMsg encoder and decoder which in the package com.zyw.tank.net
 */
public class TankStopMsgTest {

    @Test
    public void encode() {
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgEncoder());

        TankStopMsg msg = new TankStopMsg(UUID.randomUUID(), 50, 100);
        channel.writeOutbound(msg);

        ByteBuf buf = channel.readOutbound();
        MsgType msgType = MsgType.values()[buf.readInt()];
        int length = buf.readInt();
        UUID id = new UUID(buf.readLong(), buf.readLong());
        int x = buf.readInt();
        int y = buf.readInt();

        assert msgType.equals(MsgType.TANK_STOP);
        assert length == 24;
        assert id.equals(msg.getId());
        assert x == 50;
        assert y == 100;
    }

    @Test
    public void decoder() {
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgDecoder());

        UUID id = UUID.randomUUID();
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TANK_STOP.ordinal());
        buf.writeInt(24);
        buf.writeLong(id.getMostSignificantBits());
        buf.writeLong(id.getLeastSignificantBits());
        buf.writeInt(50);
        buf.writeInt(100);

        channel.writeInbound(buf);

        TankStopMsg tjm = channel.readInbound();
        assert tjm.getId().equals(id);
        assert tjm.getX() == 50;
        assert tjm.getY() == 100;
    }
}
