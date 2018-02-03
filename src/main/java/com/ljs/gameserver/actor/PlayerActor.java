package com.ljs.gameserver.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.ljs.gameserver.entry.PlayerEntry;

public class PlayerActor  extends AbstractActor{

    private PlayerEntry playerEntry;

    public PlayerActor(PlayerEntry playerEntry){

        this.playerEntry=playerEntry;
    }

    public static Props props(PlayerEntry playerEntry) {
        return Props.create(PlayerActor.class,playerEntry);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().build();
    }



}
