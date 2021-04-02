package com.lqh.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 作为事务管理者，它需要
 * 1.创建并保存事务组
 * 2.保存各个子事务在对应的事务组内
 * 3.统计并判断事务组内的各个事务的状态，以算出当前事务组的状态（提交或回滚）
 * 4.通知各个子事务提交或回滚
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    //事务组中的事务状态列表
    private static Map<String, List<String>> transactionTypeMap = new HashMap<>();
    //事务组是否已经接收到结束的标记
    private static Map<String,Boolean> isEndMap = new HashMap<>();
    //事务组中应该有的事务个数
    private static Map<String,Integer> transactionCountMap = new HashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("接受数据:" + msg.toString());
        JSONObject object = JSON.parseObject(msg.toString());
        String command = object.getString("command");//create-创建事务组
        String groupId = object.getString("groupId");//事务组Id
        String transactionType = object.getString("transactionType");//子事务类型，commit/rollback
        Integer transactionCount = object.getInteger("transactionCount");//事务数量
        Boolean isEnd = object.getBoolean("isEnd");//是否是结束事务

        if ("create".equals(command)) {
            //创建事务组
            transactionTypeMap.put(groupId,new LinkedList<>());
        } else if ("add".equals(command)) {
            //加入事务组
            transactionTypeMap.get(groupId).add(transactionType);
            if (isEnd) {
                //结束事务
                isEndMap.put(groupId,true);
                transactionCountMap.put(groupId,transactionCount);
            }

            JSONObject result = new JSONObject();
            result.put("groupId",groupId);

            //如果已经接收到结束事务的标记，比较事务是否已经全部到达，如果全部到达则看是否需要回滚。
            if (isEndMap.get(groupId) && transactionCountMap.get(groupId).equals(transactionTypeMap.get(groupId).size())) {
                if (transactionTypeMap.get(groupId).contains("rollback")) {
                    result.put("command", "rollback");
                } else {
                    result.put("command","commit");
                }
                //TODO 发送 result
                sendResult(result);
            }
        }

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.add(channel);

    }

    private void sendResult(JSONObject result) {
        for (Channel channel : channelGroup) {
            System.out.println("发送数据"+result.toJSONString());
            channel.writeAndFlush(result.toJSONString());
        }
    }
}
