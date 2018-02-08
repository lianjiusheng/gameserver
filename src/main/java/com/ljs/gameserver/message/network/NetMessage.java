package com.ljs.gameserver.message.network;

public abstract class NetMessage {

    private final int op;//业务编号
    private long tid;//事务ID
    private String version="1.0";// 版本


    public NetMessage(int op) {
        this.op=op;
    }

    public int getOp() {
        return op;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
