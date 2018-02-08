package com.ljs.gameserver.message.network;

public abstract class NetMessage<T extends NetMessage> extends  ActorMessage<T>{

    private String channelId;

    public NetMessage(int op) {
        super(op);
    }
    public T setChannelId(String channelId) {
        this.channelId = channelId;
        return (T)this;
    }
    public String getChannelId() {
        return channelId;
    }

}
