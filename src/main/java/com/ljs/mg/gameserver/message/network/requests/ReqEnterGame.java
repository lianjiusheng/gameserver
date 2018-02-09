package com.ljs.mg.gameserver.message.network.requests;

import com.ljs.mg.core.packet.ReqNetMessage;
import com.ljs.mg.gameserver.message.network.Op;

public class ReqEnterGame extends ReqNetMessage {

    private String playerId;

    public ReqEnterGame() {
        super(Op.OP_ENTRY_GAME_REQ);
    }

    @Override
    public String getPlayerId() {
        return playerId;
    }

    @Override
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

}
