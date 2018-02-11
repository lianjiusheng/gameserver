package com.ljs.mg.gameserver.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
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
import com.ljs.mg.gameserver.springakka.SpringExtension;
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
    private SessionManger sessionManger;

    int count=0;
    long startTime=0;

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

        getContext().actorSelection(ActorPathConst.PlayerCreatorActor).forward(msg,getContext());

    }

    private void processReqLogin(ReqLogin msg) {

        if(count==0){
            startTime=System.currentTimeMillis();
        }

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

        count++;

        if(count%1000==0){
            log.info("count:{}",count);
        }

        if(count==100000){
            log.info("select {} cost time {}",count,System.currentTimeMillis()-startTime);
        }
    }

    private void processEnterWorld(ReqEnterGame msg) {
        getContext().actorSelection(ActorPathConst.PlayerLoaderActor).forward(msg,getContext());
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
