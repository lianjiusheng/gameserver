package com.ljs.mg.gameserver.message.network.responses;

import com.ljs.mg.core.packet.RespNetMessage;
import com.ljs.mg.gameserver.entry.PlayerEntry;
import com.ljs.mg.gameserver.message.network.Op;

public class RespEnterGame extends RespNetMessage {

    private PlayerEntry playerEntry;

    public RespEnterGame() {
        super(Op.OP_ENTER_GAME_RESP);
    }

    public PlayerEntry getPlayerEntry() {
        return playerEntry;
    }

    public void setPlayerEntry(PlayerEntry playerEntry) {
        this.playerEntry = playerEntry;
    }

}
