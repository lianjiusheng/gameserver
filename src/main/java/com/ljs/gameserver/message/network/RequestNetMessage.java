package com.ljs.gameserver.message.network;

public class RequestNetMessage<T extends RequestNetMessage> extends NetMessage<T> {

    private String playerId;

    public RequestNetMessage(int op) {
        super(op);
    }

    public String getPlayerId() {
        return playerId;
    }

    public T setPlayerId(String playerId) {
        this.playerId = playerId;
        return (T)this;
    }
}
