package com.lqh.dev.mytransactional.transactional;

import com.lqh.dev.mytransactional.task.MyTask;
import lombok.Data;

@Data
public class LbTransaction {

    private String transactionId;
    private String groupId;
    private TransactionType transactionType;
    private MyTask task;

    public LbTransaction(String transactionId, String groupId, TransactionType transactionType) {
        this.transactionId = transactionId;
        this.groupId = groupId;
        this.transactionType = transactionType;
        //让每个事务都能对应一个任务
        this.task = new MyTask();
    }

    public LbTransaction() {

    }
}
