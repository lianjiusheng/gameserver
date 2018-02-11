package com.ljs.mg.gameserver.services;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.ljs.mg.core.actor.PlayerActor;
import com.ljs.mg.gameserver.actor.ActorPathConst;
import com.ljs.mg.gameserver.entry.*;
import com.ljs.mg.gameserver.mapper.PlayerCommanderEntryMapper;
import com.ljs.mg.gameserver.repository.PlayerCommanderRepository;
import com.ljs.mg.gameserver.repository.PlayerEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class PlayerEntryService {

    @Autowired
    private PlayerEntryRepository repository;
    @Autowired
    private ActorSystem actorSystem;
    @Autowired
    private PlayerCommanderEntryMapper playerCommanderEntryMapper;
    @Autowired
    private PlayerCommanderRepository playerCommanderRepository;

    private ConcurrentMap<String, ActorRef> playerRefs = new ConcurrentHashMap<>();

    public PlayerEntry loadPlayerEntry(String playerId) {

        if (!playerRefs.containsKey(playerId)) {
            return repository.findById(playerId);
        }

        synchronized (playerRefs) {

            if (!playerRefs.containsKey(playerId)) {
                return repository.findById(playerId);
            }

            PlayerEntry entry = repository.findById(playerId);
            //TODO 加载玩家其他数据
            playerCommanderRepository.getCommanderManager(playerId);
            ActorRef ref = actorSystem.actorOf(Props.create(PlayerActor.class), ActorPathConst.getPlayerActorName(entry.getId()));
            playerRefs.put(playerId, ref);

            return entry;
        }

    }

    @Transactional
    public void savePlayerEntry(PlayerEntry entry) {
        repository.insertPlayerEntry(entry);
        //TODO 创建玩家其他相关数据
        CommanderManager manager = new CommanderManager();
        manager.setPlayerId(entry.getId());
        int size = 5;
        for (int i = 0; i < size; i++) {
            PlayerCommanderEntry playerCommanderEntry = new PlayerCommanderEntry();
            playerCommanderEntry.setOwnerId(entry.getId());
            playerCommanderEntry.setCommanderId(i);
            playerCommanderEntry.setExp(0);
            playerCommanderEntry.setHurtTimes(0);
            playerCommanderEntry.setLevel(1);
            playerCommanderEntry.setPower(100);
            playerCommanderEntry.setSkillInfo(new SkillInfo());
            playerCommanderEntry.setTalentInfo(new TelentInfo());
            playerCommanderEntryMapper.insert(playerCommanderEntry);
            manager.addPlayerCommander(playerCommanderEntry);
        }
        playerCommanderRepository.saveCommanderManager(manager);
    }


}
