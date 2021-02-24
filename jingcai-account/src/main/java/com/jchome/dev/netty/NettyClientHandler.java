package com.lqh.dev.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lqh.dev.mytransactional.transactional.LbTransaction;
import com.lqh.dev.mytransactional.transactional.LbTransactionManager;
import com.lqh.dev.mytransactional.transactional.TransactionType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext context;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("接收数据：" + msg.toString());

        JSONObject object = JSON.parseObject(msg.toString());
        String groupId = object.getString("groupId");
        String command = object.getString("command");//事务组的状态

        System.out.println("接收command：" + command);
        LbTransaction lbTransaction = LbTransactionManager.getByGroupId(groupId);
        //根据事务组的状态去改变每个本地事务的状态
        if ("commit".equals(command)) {
            lbTransaction.setTransactionType(TransactionType.COMMIT);
        } else {
            lbTransaction.setTransactionType(TransactionType.ROLLBACK);
        }

        //唤醒
        lbTransaction.getTask().signalTask();

    }

    public void call(JSONObject object) {
        System.out.println("发送的数据: " + object.toJSONString());
        context.writeAndFlush(object.toJSONString());
    }
}
