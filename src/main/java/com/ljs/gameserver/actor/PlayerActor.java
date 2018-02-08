package com.ljs.gameserver.actor;

import akka.actor.AbstractActor;
import com.ljs.gameserver.entry.PlayerEntry;
import com.ljs.gameserver.message.PlayerActorProtocol;


public class PlayerActor  extends AbstractActor{

    private PlayerEntry playerEntry;

    public PlayerActor(PlayerEntry playerEntry){
        this.playerEntry=playerEntry;
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder().match(PlayerActorProtocol.Init.class,this::handleInit).build();
    }


    /**
     * 处理初始化请求
     * @param msg
     */
    private void handleInit(PlayerActorProtocol.Init msg){

        getContext().become(receiveBuilder().build());



    }

}
