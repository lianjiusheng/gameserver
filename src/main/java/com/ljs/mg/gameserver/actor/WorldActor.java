package com.ljs.mg.gameserver.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.ljs.gameserver.entry.PlayerEntrySimpleInfo;
import com.ljs.gameserver.message.AuthenticationServiceProtocol;
import com.ljs.gameserver.message.network.requests.ReqEnterWorld;
import com.ljs.gameserver.message.network.requests.ReqLogin;
import com.ljs.gameserver.message.network.responses.RespPlayerList;
import com.ljs.gameserver.message.repository.PlayerEntryRepositoryProtocol;
import com.ljs.gameserver.SessionHelper;
import com.ljs.mg.gameserver.message.AuthenticationServiceProtocol;
import com.ljs.mg.gameserver.message.repository.PlayerEntryRepositoryProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("WorldActor")
@Scope("prototype")
public class WorldActor extends AbstractActor {


    private ActorSystem actorSystem;
    private SessionHelper sessionHelper;


    public WorldActor(@Autowired  ActorSystem actorSystem,@Autowired SessionHelper sessionHelper){
        this.actorSystem=actorSystem;
        this.sessionHelper=sessionHelper;
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ReqLogin.class,this::processReqLogin)
                .match(AuthenticationServiceProtocol.AuthenticateSuccess.class, this::processAuthenticateSuccess)
                .match(PlayerEntryRepositoryProtocol.AccountPlayerList.class,this::processAccountPlayerList)
                .match(PlayerEntryRepositoryProtocol.FindByIdResult.class, this::processFindByIdResult)
                .match(ReqEnterWorld.class,this::processEnterWorld)
                .build();
    }

    private  void processReqLogin(ReqLogin msg) {
        ActorSelection authenticationServiceSelection=actorSystem.actorSelection(ActorPathConst.AuthenticationServicePath);
        authenticationServiceSelection.tell(new AuthenticationServiceProtocol.AuthenticateRequest(msg.getChannelId(),msg.getAccountId(),msg.getPlatform(),msg.getOpenId(),msg.getTime(),msg.getSign()),getSelf());
    }

    private  void processEnterWorld(ReqEnterWorld msg) {
        PlayerEntryRepositoryProtocol.FindById findById=  new PlayerEntryRepositoryProtocol.FindById(msg.getPlayerId());
        findById.setChannelId(msg.getChannelId());
        ActorSelection  playerEntryRepositorySelection=actorSystem.actorSelection(ActorPathConst.PlayerEntryRepositoryPath);
        playerEntryRepositorySelection.tell(findById,getSelf());
    }

    private  void processAuthenticateSuccess(AuthenticationServiceProtocol.AuthenticateSuccess msg)
    {
        ActorSelection  playerEntryRepositorySelection=actorSystem.actorSelection(ActorPathConst.PlayerEntryRepositoryPath);
        playerEntryRepositorySelection.tell(new PlayerEntryRepositoryProtocol.FindAccountPlayerList(msg.getChannelId(),msg.getAccountId()),getSelf());
    }

    private  void processAccountPlayerList(PlayerEntryRepositoryProtocol.AccountPlayerList msg) {
        List<PlayerEntrySimpleInfo> simpleInfoList= msg.getSimpleInfoList();
        RespPlayerList resp=new RespPlayerList();
        resp.setPlayers(simpleInfoList);
        sessionHelper.writeMessageTo(msg.getChannelId(),resp);
    }

    private void processFindByIdResult(PlayerEntryRepositoryProtocol.FindByIdResult result) {

        if(result.getResultRef()==null){

            //TODO 客户端发送登录进入游戏失败的消息
            //sessionHelper.writeMessageTo(result.getChannelId(),null);
            return ;
        }

    }

}
