package com.ljs.gameserver;

import akka.actor.AbstractActor;

public class IntegerActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Integer.class,s->{
            System.out.println("Integer actor say: num "+s);
        }).build();
    }
}
