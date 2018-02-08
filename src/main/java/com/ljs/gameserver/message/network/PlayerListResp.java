package com.ljs.gameserver.message.network;

import com.ljs.gameserver.entry.PlayerEntry;

import java.util.List;

public class PlayerListResp extends NetMessage {

    private List<PlayerEntry> players;

    public PlayerListResp(int op) {
        super(Op.OP_PLAYER_LIST_RESP);
    }

    public List<PlayerEntry> getPlayers() {
        return players;
    }

    public PlayerListResp setPlayers(List<PlayerEntry> players) {
        this.players = players;
        return this;
    }
}
