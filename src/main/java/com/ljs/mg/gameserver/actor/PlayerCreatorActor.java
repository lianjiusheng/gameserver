package com.ljs.mg.gameserver.actor;

import akka.actor.AbstractActor;
import com.google.common.base.Strings;
import com.ljs.mg.gameserver.Session;
import com.ljs.mg.gameserver.SessionManger;
import com.ljs.mg.gameserver.entry.PlayerEntry;
import com.ljs.mg.gameserver.message.network.requests.ReqCreatePlayer;
import com.ljs.mg.gameserver.message.network.responses.RespCreatePlayer;
import com.ljs.mg.gameserver.repository.PlayerEntryRepository;
import com.ljs.mg.gameserver.services.PlayerEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component("PlayerCreatorActor")
@Scope("prototype")
public class PlayerCreatorActor extends AbstractActor{

    @Autowired
    private PlayerEntryService playerEntryService;
    @Autowired
    private SessionManger sessionManger;


    @Override
    public Receive createReceive() {
        return receiveBuilder() .match(ReqCreatePlayer.class, this::processCreatePlayer).build();
    }

    private void processCreatePlayer(ReqCreatePlayer msg) {

        Session session = sessionManger.getSession(msg.getChannelId());
        String name = msg.getPlayerName();

        RespCreatePlayer respCreatePlayer = new RespCreatePlayer();

        if (Strings.isNullOrEmpty(name)) {
            respCreatePlayer.setRs(4);
            respCreatePlayer.setErrorMsg("角色名不能为空");
        } else {
            PlayerEntry playerEntry = new PlayerEntry();
            playerEntry.setAccountId(session.getAccountId());
            playerEntry.setId(UUID.randomUUID().toString());
            playerEntry.setName(name);
            playerEntry.setCreateTime(new Date());
            playerEntry.setLastLoginTime(new Date());

            playerEntryService.savePlayerEntry(playerEntry);
            respCreatePlayer.setPlayerEntry(playerEntry);
        }
        session.writeMessage(respCreatePlayer);
    }

}
