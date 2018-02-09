package com.ljs.gameserver.message.network.responses;

import com.ljs.mg.core.packet.RespNetMessage;
import com.ljs.gameserver.entry.PlayerEntrySimpleInfo;
import com.ljs.gameserver.message.network.Op;

import java.util.List;

public class RespPlayerList extends RespNetMessage {

    private List<PlayerEntrySimpleInfo> players;

    public RespPlayerList() {
        super(Op.OP_PLAYER_LIST_RESP);
    }

    public List<PlayerEntrySimpleInfo> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerEntrySimpleInfo> players) {
        this.players = players;

    }
}
