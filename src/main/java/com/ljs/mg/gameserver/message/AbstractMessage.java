package com.ljs.mg.gameserver.message;

/**
 * 业务消息的基类
 */
public class AbstractMessage {

    private int op;//业务ID
    private String tid;//事务ID
    private String channelId;

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
