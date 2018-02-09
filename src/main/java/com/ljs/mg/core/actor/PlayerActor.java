package com.ljs.mg.core.actor;

import akka.actor.AbstractActor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ljs.mg.util.JSONUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PlayerActor  extends AbstractActor{


    protected Logger log= LogManager.getLogger(getClass());

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(PlayerActorProtocol.PlayerActorMessage.class,this::handleMsg).build();
    }

    private  void handleMsg(PlayerActorProtocol.PlayerActorMessage  msg) {

        try {
            msg.getRequestHandler().handle(msg.getReqNetMessage());
        } catch (Exception e) {
            e.printStackTrace();
            try {
                log.error("handle msg error . req ={}", JSONUtil.getJSONString(msg.getReqNetMessage()));
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }

    }
}
