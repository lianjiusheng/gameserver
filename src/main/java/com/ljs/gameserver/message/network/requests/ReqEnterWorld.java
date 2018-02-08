package com.ljs.gameserver.message.network.requests;

public class ReqEnterWorld extends ReqNetMessage {

    private String playerId;

    public ReqEnterWorld(int op) {
        super(op);
    }

    @Override
    public String getPlayerId() {
        return playerId;
    }
}
