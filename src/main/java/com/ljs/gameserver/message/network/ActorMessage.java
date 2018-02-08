package com.ljs.gameserver.message.network;

public abstract class ActorMessage<T extends ActorMessage>{

    private final int op;//业务编号
    private long tid;//事务ID
    private String version="1.0";// 版本

    public ActorMessage(int op) {
        this.op = op;
    }

    public int getOp() {
        return op;
    }

    public long getTid() {
        return tid;
    }

    public String getVersion() {
        return version;
    }

    public T  setVersion(String version) {
        this.version = version;
        return (T)this;
    }

    public T setTid(long tid) {
        this.tid = tid;
        return (T)this;
    }

}
