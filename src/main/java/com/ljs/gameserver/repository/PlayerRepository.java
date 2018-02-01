package com.ljs.gameserver.repository;

import com.ljs.gameserver.entry.PlayerEntry;
import com.ljs.gameserver.mapper.PlayerEntryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PlayerRepository {

    @Autowired
    private PlayerEntryMapper playerEntryMapper;


    public PlayerEntry findById(String id){
       return  playerEntryMapper.selectByPrimaryKey(id);
    }

}
