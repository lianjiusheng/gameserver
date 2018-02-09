package com.ljs.mg.gameserver;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.ljs.mg.core.IGameMessageDispather;
import com.ljs.mg.core.INetMessageHandler;
import com.ljs.mg.core.actor.PlayerActorProtocol;
import com.ljs.mg.core.packet.NetMessage;
import com.ljs.mg.core.packet.ReqNetMessage;
import com.ljs.mg.gameserver.actor.ActorPathConst;
import com.ljs.mg.gameserver.message.network.Op;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ServerMessageDispather implements IGameMessageDispather {

    protected Logger log= LogManager.getLogger(getClass());
    @Autowired
    private SessionManger sessionManger;
    @Autowired
    private ActorSystem actorSystem;
    @Autowired
    NetMessageHandlerFactory netMessageHandlerFactory;

    @Override
    public void dispath(NetMessage message) {

        int op=message.getOp();

        if(message instanceof ReqNetMessage){


            ReqNetMessage reqNetMessage=(ReqNetMessage)message;

            Session session= sessionManger.getSession(reqNetMessage.getChannelId());
            reqNetMessage.setPlayerId(session.getPlayerId());

            if(op == Op.OP_LOGIN_REQ || op==Op.OP_CREATE_PLAYER_REQ ||op ==Op.OP_ENTRY_GAME_REQ){
                ActorSelection actorSelection= actorSystem.actorSelection(ActorPathConst.AuthenticationServicePath);
                actorSelection.tell(reqNetMessage, ActorRef.noSender());
                return ;
            }

            String playerId= reqNetMessage.getPlayerId();
            INetMessageHandler messageHandler=netMessageHandlerFactory.getNetMessageHandler(op);
            if(messageHandler==null){
                log.warn("找不到op={}的处理类",op);
                return ;
            }
            //构建Actor消息，并将消息发送Actor
            PlayerActorProtocol.PlayerActorMessage playerActorMessage=new PlayerActorProtocol.PlayerActorMessage(reqNetMessage,messageHandler);
            ActorSelection playerActorSelection= actorSystem.actorSelection(ActorPathConst.getPlayerActorName(playerId));
            playerActorSelection.tell(reqNetMessage, ActorRef.noSender());
        }else{
            log.warn("暂不支持改类型消息，消息类型：",message.getClass());
        }
    }
}
