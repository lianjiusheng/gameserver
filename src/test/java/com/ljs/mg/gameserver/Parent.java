package com.ljs.mg.gameserver;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class Parent extends AbstractActor {

    private ActorRef stringRef;
    private ActorRef integerRef;

    public Parent() {
        stringRef=getContext().actorOf(Props.create(StringActor.class),"stringActor");
        integerRef =getContext().actorOf(Props.create(IntegerActor.class),"integerActor");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(String.class,this::hanleString).match(Integer.class,this::hanleInteger).build();
    }

    private  void hanleString(String s) {
        System.out.println("parent get string:"+s);
        stringRef.tell(s,getSelf());
    }

    private  void hanleInteger(Integer s) {
        System.out.println("parent get integer:"+s);
        integerRef.tell(s,getSelf());
    }
}
