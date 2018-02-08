package com.ljs.gameserver.message.network.requests;

import com.ljs.gameserver.message.network.NetMessage;

public class ReqNetMessage extends NetMessage {

    private String channelId;
    private String playerId;

    public ReqNetMessage(int op) {
        super(op);
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
