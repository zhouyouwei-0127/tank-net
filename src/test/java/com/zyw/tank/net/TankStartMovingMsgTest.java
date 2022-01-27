package com.zyw.tank.net;

import com.zyw.tank.Dir;
import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

/**
 * test TankStartMovingMsg encoder and decoder which in the package com.zyw.tank.net
 */
public class TankStartMovingMsgTest {

    @Test
    public void encode() {
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgEncoder());

        TankStartMovingMsg msg = new TankStartMovingMsg(UUID.randomUUID(), 50, 100, Dir.L);
        channel.writeOutbound(msg);

        ByteBuf buf = channel.readOutbound();
        MsgType msgType = MsgType.values()[buf.readInt()];
        int length = buf.readInt();
        UUID id = new UUID(buf.readLong(), buf.readLong());
        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir = Dir.values()[buf.readInt()];

        assert msgType.equals(MsgType.TankStartMoving);
        assert length == 28;
        assert id.equals(msg.getId());
        assert x == msg.getX();
        assert y == msg.getY();
        assert dir.equals(msg.getDir());
    }
}
