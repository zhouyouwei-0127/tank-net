package com.zyw.tank.net;

import com.zyw.tank.Dir;
import com.zyw.tank.Group;
import com.zyw.tank.Player;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

/**
 * test MsgEncoder and MsgDecoder which in the package com.zyw.tank.net
 */
public class TankJoinMsgTest {

    @Test
    public void encode() {
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgEncoder());
        Player player = new Player(5, 10, Dir.L, Group.BAD);
        TankJoinMsg tm = new TankJoinMsg(player);
        channel.writeOutbound(tm);

        ByteBuf buf = channel.readOutbound();
        int length = buf.readInt();
        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir = Dir.values()[buf.readInt()];
        boolean moving = buf.readBoolean();
        Group group = Group.values()[buf.readInt()];
        UUID id = new UUID(buf.readLong(), buf.readLong());

        assert length == 33;
        assert x == 5;
        assert y == 10;
        assert dir == Dir.L;
        assert !moving;
        assert group == Group.BAD;
        assert id.equals(player.getId());
    }

    @Test
    public void decoder() {
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgDecoder());

        UUID id = UUID.randomUUID();
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(33);
        buf.writeInt(5);
        buf.writeInt(10);
        buf.writeInt(Dir.L.ordinal());
        buf.writeBoolean(false);
        buf.writeInt(Group.BAD.ordinal());
        buf.writeLong(id.getMostSignificantBits());
        buf.writeLong(id.getLeastSignificantBits());

        channel.writeInbound(buf);

        TankJoinMsg tjm = channel.readInbound();
        assert tjm.getX() == 5;
        assert tjm.getY() == 10;
        assert tjm.getDir() == Dir.L;
        assert !tjm.isMoving();
        assert tjm.getGroup() == Group.BAD;
        assert tjm.getId().equals(id);
    }
}
