package com.zyw.tank.net.msg;

import com.zyw.tank.*;
import com.zyw.tank.net.Client;
import lombok.Getter;

import java.io.*;
import java.util.UUID;

@Getter
public class TankJoinMsg extends Msg {
    private int x, y;
    private Dir dir;
    private boolean moving;
    private Group group;
    private UUID id;

    public TankJoinMsg(Player p) {
        this.x = p.getX();
        this.y = p.getY();
        this.dir = p.getDir();
        this.moving = p.isMoving();
        this.group = p.getGroup();
        this.id = p.getId();
    }

    public TankJoinMsg() {
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeBoolean(moving);
            dos.writeInt(group.ordinal());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
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
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
            this.moving = dis.readBoolean();
            this.group = Group.values()[dis.readInt()];
            this.id = new UUID(dis.readLong(), dis.readLong());
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
        //是自己则不处理
        if (this.id.equals(TankFrame.INSTANCE.getGm().getMyTank().getId())) return;
        //已经存在则不处理
        if (TankFrame.INSTANCE.getGm().findTankByUUID(this.id) != null) return;

        Tank tank = new Tank(this);
        TankFrame.INSTANCE.getGm().add(tank);

        //每次处理消息的时候将自己发出去，不然后连上来的client不知道前面的存在
        Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getGm().getMyTank()));
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TANK_JOIN;
    }

    @Override
    public String toString() {
        return "TankJoinMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", moving=" + moving +
                ", group=" + group +
                ", id=" + id +
                '}';
    }
}
