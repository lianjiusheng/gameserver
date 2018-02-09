package com.ljs.mg.gameserver.actor;

import akka.actor.AbstractActor;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import com.ljs.mg.gameserver.Config;
import com.ljs.mg.gameserver.Session;
import com.ljs.mg.gameserver.SessionManger;
import com.ljs.mg.gameserver.entry.PlayerEntry;
import com.ljs.mg.gameserver.entry.PlayerEntrySimpleInfo;
import com.ljs.mg.gameserver.message.network.requests.ReqCreatePlayer;
import com.ljs.mg.gameserver.message.network.requests.ReqEnterGame;
import com.ljs.mg.gameserver.message.network.requests.ReqLogin;
import com.ljs.mg.gameserver.message.network.responses.RespCreatePlayer;
import com.ljs.mg.gameserver.message.network.responses.RespEnterGame;
import com.ljs.mg.gameserver.message.network.responses.RespLogin;
import com.ljs.mg.gameserver.message.network.responses.RespPlayerList;
import com.ljs.mg.gameserver.repository.PlayerEntryRepository;
import com.ljs.mg.gameserver.services.PlayerEntryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component("AuthenticationService")
@Scope("prototype")
public class AuthenticationService extends AbstractActor {

    protected Logger log= LogManager.getLogger(getClass());

    @Autowired
    private PlayerEntryRepository playerEntryRepository;
    @Autowired
    private PlayerEntryService playerEntryService;
    @Autowired
    private SessionManger sessionManger;


    @Override
    public void preStart() throws Exception {
        log.info("AuthenticationService Actor start..");
    }

    @Override
    public Receive createReceive() {

        return receiveBuilder()
                .match(ReqLogin.class, this::processReqLogin)
                .match(ReqCreatePlayer.class, this::processCreatePlayer)
                .match(ReqEnterGame.class, this::processEnterWorld)
                .build();
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

    private void processReqLogin(ReqLogin msg) {

        String sign = generateSign(msg);

        Session session = sessionManger.getSession(msg.getChannelId());

        if (sign.equalsIgnoreCase(msg.getSign())) {
            session.setAuthed(true);
            session.setAccountId(msg.getAccountId());
            List<PlayerEntrySimpleInfo> playerEntrySimpleInfoList = playerEntryRepository.findAccountPlayerList(msg.getAccountId());
            RespPlayerList respPlayerList = new RespPlayerList();
            respPlayerList.setPlayers(playerEntrySimpleInfoList);
            session.writeMessage(respPlayerList);
        } else {
            //发送消息给客户端
            RespLogin respLogin = new RespLogin();
            respLogin.setRs(1);
            respLogin.setErrorMsg("认证失败");
            session.writeMessage(respLogin);
        }
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

    private String generateSign(ReqLogin msg) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(msg.getAccountId()).append(";")
                .append(msg.getOpenId()).append(";")
                .append(msg.getPlatform()).append(";")
                .append(msg.getTime()).append(";")
                .append(Config.signkey);

        String sign = Hashing.sha256().hashString(stringBuilder.toString(), Charsets.UTF_8).toString();
        return sign;
    }

}
