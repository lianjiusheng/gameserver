package com.ljs.gameserver.actor;

import akka.actor.AbstractActor;
import akka.actor.Actor;
import com.ljs.gameserver.message.WorldActorMessage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WorldActor extends AbstractActor {

    //登录玩家
    private Map<String, Actor> onlinePlayers = new HashMap<>();
    //等待登录的玩家
    private Set<String> waiterSet = new HashSet<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(WorldActorMessage.RequestLogin.class, this::login).build();
    }

    private void login(WorldActorMessage.RequestLogin msg) {


    }




}
