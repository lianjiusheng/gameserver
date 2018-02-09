package com.ljs.mg.gameserver.services;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.ljs.mg.core.actor.PlayerActor;
import com.ljs.mg.gameserver.actor.ActorPathConst;
import com.ljs.mg.gameserver.entry.PlayerEntry;
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

    private ConcurrentMap<String, ActorRef> playerRefs = new ConcurrentHashMap<>();

    public PlayerEntry loadPlayerEntry(String playerId) {
        PlayerEntry entry = repository.findById(playerId);
        //TODO 加载玩家其他数据
        synchronized (playerRefs) {
            if (!playerRefs.containsKey(playerId)) {
                ActorRef ref = actorSystem.actorOf(Props.create(PlayerActor.class), ActorPathConst.getPlayerActorName(entry.getId()));
                playerRefs.put(playerId, ref);
            }
        }
        return entry;
    }

    @Transactional
    public void savePlayerEntry(PlayerEntry entry){
        repository.insertPlayerEntry(entry);
        //TODO 创建玩家其他相关数据
    }

}
