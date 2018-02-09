package com.ljs.gameserver.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.ljs.gameserver.Session;
import com.ljs.gameserver.SessionManger;
import com.ljs.gameserver.message.network.Op;
import com.ljs.mg.core.packet.ReqNetMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("netMessageRouter")
@Scope("prototype")
public class NetMessageRouter extends AbstractActor{

    private Logger logger= LogManager.getLogger(getClass());
    private ActorSystem system=getContext().getSystem();
    private SessionManger sessionManger;

    public NetMessageRouter(SessionManger sessionManger) {
        this.sessionManger = sessionManger;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(ReqNetMessage.class ,this::routing).build();
    }

    private void routing(ReqNetMessage msg){
        if(msg.getOp()== Op.OP_LOGIN_REQ||msg.getOp()==Op.OP_ENTRY_GAME){
            ActorSelection worldActorSelection=system.actorSelection(ActorPathConst.WorldActorPath);
            worldActorSelection.tell(msg,getSelf());
        }else{
            Session session=sessionManger.getSession(msg.getChannelId());
            if(session.isAuth()) {
                msg.setPlayerId(session.getPlayerId());
                ActorSelection worldActorSelection = system.actorSelection(ActorPathConst.getPlayerActorName(msg.getPlayerId()));
                worldActorSelection.tell(msg, getSelf());
            }else{
                logger.error("用户还未登录，请登录后再试，address:{}",session.getChannel().remoteAddress());
            }
        }
    }
}
