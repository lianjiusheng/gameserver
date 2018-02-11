package com.ljs.mg.gameserver.repository;

import com.ljs.mg.gameserver.entry.PlayerEntry;
import com.ljs.mg.gameserver.entry.PlayerEntrySimpleInfo;
import com.ljs.mg.gameserver.mapper.PlayerEntryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;



@Component
public class PlayerEntryRepository {

    @Autowired
    private PlayerEntryMapper playerEntryMapper;

    @Cacheable(value = "players",key = "#id")
    public PlayerEntry findById(String id){
        return playerEntryMapper.selectByPrimaryKey(id);
    }
    @Cacheable(value = "acount_players",key = "#accountId" )
    public List<PlayerEntrySimpleInfo> findAccountPlayerList(String accountId){
        return playerEntryMapper.selectAcountPlayerList(accountId);
    }

    @CachePut(value = "players",key = "#playerEntry.id" ,condition ="#result")
    public boolean insertPlayerEntry(PlayerEntry playerEntry){
        return  playerEntryMapper.insert(playerEntry)!=0;
    }
}
