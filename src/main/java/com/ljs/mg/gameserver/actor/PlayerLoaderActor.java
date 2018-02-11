package com.ljs.mg.gameserver.actor;

import akka.actor.AbstractActor;
import com.google.common.base.Strings;
import com.ljs.mg.gameserver.Session;
import com.ljs.mg.gameserver.SessionManger;
import com.ljs.mg.gameserver.entry.PlayerEntry;
import com.ljs.mg.gameserver.message.network.requests.ReqCreatePlayer;
import com.ljs.mg.gameserver.message.network.requests.ReqEnterGame;
import com.ljs.mg.gameserver.message.network.responses.RespCreatePlayer;
import com.ljs.mg.gameserver.message.network.responses.RespEnterGame;
import com.ljs.mg.gameserver.services.PlayerEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component("PlayerLoaderActor")
@Scope("prototype")
public class PlayerLoaderActor extends AbstractActor{

    @Autowired
    private PlayerEntryService playerEntryService;
    @Autowired
    private SessionManger sessionManger;

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(ReqEnterGame.class, this::processEnterWorld).build();
    }

    private void processEnterWorld(ReqEnterGame msg) {

        PlayerEntry entry = playerEntryService.loadPlayerEntry(msg.getPlayerId());
        Session session = sessionManger.getSession(msg.getChannelId());
        RespEnterGame respEnterGame = new RespEnterGame();
        if (entry == null) {
            respEnterGame.setRs(2);
            respEnterGame.setErrorMsg("角色不存在");
        } else {
            if (!entry.getAccountId().equals(session.getAccountId())) {
                respEnterGame.setRs(3);
                respEnterGame.setErrorMsg("用户没有该角色");
            } else {
                respEnterGame.setPlayerEntry(entry);
                session.setPlayerId(entry.getId());
            }
        }
        session.writeMessage(respEnterGame);
    }

}
