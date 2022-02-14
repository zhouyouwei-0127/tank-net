package com.zyw.tank.net.msg;

import com.zyw.tank.Bullet;
import com.zyw.tank.Dir;
import com.zyw.tank.Group;
import com.zyw.tank.TankFrame;

import java.io.*;
import java.util.UUID;

public class BulletNewMsg extends Msg{
    private UUID id;
    private UUID playerId;
    private int x, y;
    private Group group;
    private Dir dir;

    public BulletNewMsg(Bullet bullet) {
        this.id = bullet.getId();
        this.playerId = bullet.getPlayerId();
        this.x = bullet.getX();
        this.y = bullet.getY();
        this.group = bullet.getGroup();
        this.dir = bullet.getDir();
    }

    public BulletNewMsg() {}

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeLong(this.id.getMostSignificantBits());
            dos.writeLong(this.id.getLeastSignificantBits());
            dos.writeLong(this.playerId.getMostSignificantBits());
            dos.writeLong(this.playerId.getLeastSignificantBits());
            dos.writeInt(this.x);
            dos.writeInt(this.y);
            dos.writeInt(this.group.ordinal());
            dos.writeInt(this.dir.ordinal());
            dos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        ByteArrayInputStream bais;
        DataInputStream dis = null;

        try {
            bais = new ByteArrayInputStream(bytes);
            dis = new DataInputStream(bais);
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.playerId = new UUID(dis.readLong(), dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.group = Group.values()[dis.readInt()];
            this.dir = Dir.values()[dis.readInt()];
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void handle() {
        if (this.playerId.equals(TankFrame.INSTANCE.getGm().getMyTank().getId())) return;
        Bullet bullet = new Bullet(this.playerId, this.x, this.y, this.dir, this.group);
        bullet.setId(UUID.randomUUID());
        TankFrame.INSTANCE.getGm().add(bullet);
    }

    @Override
    public MsgType getMsgType() { return MsgType.BULLET_NEW; }

    @Override
    public String toString() {
        return "BulletNewMsg{" +
                "id=" + id +
                ", playerId=" + playerId +
                ", x=" + x +
                ", y=" + y +
                ", group=" + group +
                ", dir=" + dir +
                '}';
    }
}
