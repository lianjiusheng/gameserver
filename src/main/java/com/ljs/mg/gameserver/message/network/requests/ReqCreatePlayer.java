package com.ljs.mg.gameserver.message.network.requests;

import com.ljs.mg.core.packet.ReqNetMessage;
import com.ljs.mg.gameserver.message.network.Op;

public class ReqCreatePlayer extends ReqNetMessage {

    private String playerName;

    public ReqCreatePlayer(int op) {
        super(Op.OP_CREATE_PLAYER_REQ);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
