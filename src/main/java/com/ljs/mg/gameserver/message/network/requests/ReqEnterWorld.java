package com.ljs.mg.gameserver.message.network.requests;

import com.ljs.mg.core.packet.ReqNetMessage;

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
