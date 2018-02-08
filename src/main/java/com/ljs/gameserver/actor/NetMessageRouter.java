package com.ljs.gameserver.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.ljs.gameserver.message.network.NetMessage;
import com.ljs.gameserver.message.network.RequestNetMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component("netMessageRouter")
@Scope("prototype")
public class NetMessageRouter extends AbstractActor{

    private  Logger logger= LogManager.getLogger(getClass());
    private ActorSystem system=getContext().getSystem();
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(NetMessage.class ,this::routing).build();
    }

    private void routing(NetMessage msg){
        if(msg instanceof RequestNetMessage){
            RequestNetMessage requestNetMessage=(RequestNetMessage)msg;
            if(!StringUtils.isEmpty(requestNetMessage.getPlayerId())){
                ActorSelection actorSelection= system.actorSelection("akka://actorSystem/user/PlayerEntryRepository/player@"+requestNetMessage.getPlayerId());
                actorSelection.tell(requestNetMessage,getSelf());
            }else{
                ActorSelection actorSelection= system.actorSelection("akka://actorSystem/user/WorldActor");
                actorSelection.tell(requestNetMessage,getSelf());
            }

        }else{
            logger.warn("不能分发消息：op={}",msg.getOp());
        }
    }
}
