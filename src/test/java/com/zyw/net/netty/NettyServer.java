package com.zyw.net.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) {
        //负责接客
        EventLoopGroup boss = new NioEventLoopGroup(2);
        //负责服务
        EventLoopGroup worker = new NioEventLoopGroup(4);
        //server启动辅助类
        ServerBootstrap b = new ServerBootstrap();
        b.group(boss, worker);
        //设置channel类型
        b.channel(NioServerSocketChannel.class);
        //netty内部处理了accept过程，当有一个连接建立好之后，会调用MyChildInitializer的initChannel方法
        b.childHandler(new MyChildInitializer());
        ChannelFuture future = b.bind(8888);

        future.channel().closeFuture();

        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }
}

class MyChildInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new MyChildHandler());
    }
}

class MyChildHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println(buf.toString());
        ctx.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}