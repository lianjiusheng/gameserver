package com.ljs.gameserver;

import akka.actor.AbstractActor;

public class StringActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(String.class,s->{
            System.out.println("String actor say: Hello "+s);
        }).build();
    }
}
