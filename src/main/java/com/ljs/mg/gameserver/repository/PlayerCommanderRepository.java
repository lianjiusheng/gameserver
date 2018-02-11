package com.ljs.mg.gameserver.repository;

import com.ljs.mg.gameserver.entry.CommanderManager;
import com.ljs.mg.gameserver.entry.PlayerCommanderEntry;
import com.ljs.mg.gameserver.mapper.PlayerCommanderEntryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerCommanderRepository {

    @Autowired
    private PlayerCommanderEntryMapper playerCommanderEntryMapper;

    @Cacheable(value = "playerCommanderManager",key = "#playerId")
    public CommanderManager getCommanderManager(String playerId) {
        CommanderManager manager = new CommanderManager();
        List<PlayerCommanderEntry> entryList = playerCommanderEntryMapper.selectPlayerCommanderEntrys(playerId);
        manager.restore(playerId, entryList);
        return manager;
    }

    @CachePut(value = "playerCommanderManager",key = "#commanderManager.playerId")
    public void saveCommanderManager(CommanderManager commanderManager){

    }


}
