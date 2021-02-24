package com.lqh.dev.mytransactional.transactional;

import com.alibaba.fastjson.JSONObject;
import com.lqh.dev.netty.NettyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class LbTransactionManager {

    private static NettyClient nettyClient;
    private static Map<String,LbTransaction> LB_TRANSACTION_MAP = new HashMap<>();

    public LbTransactionManager(NettyClient client) {
        System.out.println("注入nettyClient");
        LbTransactionManager.nettyClient = client;
    }

    public static String createLbTransactionGroup() {
        //netty
        String groupId = UUID.randomUUID().toString();

        JSONObject object = new JSONObject();
        object.put("groupId", groupId);
        object.put("command", "create");

        nettyClient.send(object);
        return groupId;
    }

    public static LbTransaction createTransaction(String groupId) {
        //创建我自己的事务
        String transactionId = UUID.randomUUID().toString();
        LbTransaction lbTransaction = new LbTransaction(transactionId, groupId, null);//本地事务的状态如何来去
        System.out.println("创建事务:");
        LB_TRANSACTION_MAP.put(groupId,lbTransaction);
        return lbTransaction;
    }

    public static void addLbTransaction(LbTransaction lbTransaction, Boolean isEnd) {
        //加到事务组里面去,netty
        JSONObject object = new JSONObject();
        object.put("groupId",lbTransaction.getGroupId());
        object.put("transactionId",lbTransaction.getTransactionId());
        object.put("transactionType",lbTransaction.getTransactionType());
        object.put("commit","add");
        object.put("isEnd",isEnd);
        //object.put("transactionCount");
        LB_TRANSACTION_MAP.put(lbTransaction.getGroupId(),lbTransaction);
        nettyClient.send(object);
        System.out.println("添加事务并发送");
    }

    public static LbTransaction getByGroupId(String groupId) {
        return LB_TRANSACTION_MAP.get(groupId);
    }
}
