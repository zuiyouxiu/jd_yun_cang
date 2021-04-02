package com.lqh.netty;

public class NettyServerMain {
    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start("localhost",8000);
        System.out.println("netty server start ...");
    }
}
