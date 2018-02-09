package com.ljs.mg.gameserver.message.network.responses;

import com.ljs.mg.core.packet.RespNetMessage;
import com.ljs.mg.gameserver.entry.PlayerEntry;
import com.ljs.mg.gameserver.message.network.Op;

public class RespCreatePlayer extends RespNetMessage {

    private PlayerEntry playerEntry;

    public RespCreatePlayer() {
        super(Op.OP_CREATE_PLAYER_RESP);
    }

    public PlayerEntry getPlayerEntry() {
        return playerEntry;
    }

    public void setPlayerEntry(PlayerEntry playerEntry) {
        this.playerEntry = playerEntry;
    }

}
