package com.ljs.gameserver.actor;

import akka.actor.AbstractActor;

public class PlayerActor  extends AbstractActor{


    @Override
    public Receive createReceive() {
        return receiveBuilder().build();
    }



}
