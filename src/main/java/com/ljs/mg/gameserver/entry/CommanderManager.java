package com.ljs.mg.gameserver.entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommanderManager {

    private String playerId;

    private Map<Integer,PlayerCommanderEntry> commanderEntryMap=new HashMap<>();


    public void restore(String playerId,List<PlayerCommanderEntry> playerCommanderEntries){
        this.playerId=playerId;
        playerCommanderEntries.forEach(e->{
            commanderEntryMap.put(e.getCommanderId(),e);
        });
    }

    public List<PlayerCommanderEntry> getCommanderList(){
        return new ArrayList<>(commanderEntryMap.values());
    }

    public void addPlayerCommander(PlayerCommanderEntry playerCommanderEntry){
        commanderEntryMap.put(playerCommanderEntry.getCommanderId(),playerCommanderEntry);
    }

    public PlayerCommanderEntry getPlayerCommanderEntry(int commanderId){
        return commanderEntryMap.get(commanderId);
    }


    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }
}
