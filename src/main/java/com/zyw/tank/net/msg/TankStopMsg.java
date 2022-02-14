package com.zyw.tank.net.msg;

import com.zyw.tank.Tank;
import com.zyw.tank.TankFrame;
import lombok.Getter;

import java.io.*;
import java.util.UUID;

@Getter
public class TankStopMsg extends Msg {

    private UUID id;
    private int x, y;

    public TankStopMsg(UUID id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public TankStopMsg() {
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.flush();
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
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
            this.x = dis.readInt();
            this.y = dis.readInt();
        } catch (Exception e) {
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
        if(this.id.equals(TankFrame.INSTANCE.getGm().getMyTank().getId())) return;
        Tank tank = TankFrame.INSTANCE.getGm().findTankByUUID(this.id);
        if (tank != null) {
            tank.setMoving(false);
            tank.setX(this.x);
            tank.setY(this.y);
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TANK_STOP;
    }

    @Override
    public String toString() {
        return "TankStopMsg{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
