package com.ljs.gameserver.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.ljs.gameserver.message.PlayerActorProtocol;
import com.ljs.gameserver.message.WorldActorMessage;
import com.ljs.gameserver.message.repository.PlayerEntryRepositoryProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component("WorldActor")
@Scope("prototype")
public class WorldActor extends AbstractActor {


    private ActorSystem actorSystem;
    //登录玩家
    private Map<String, ActorRef> onlinePlayers = new HashMap<>();
    //等待登录的玩家
    private Set<String> waiterSet = new HashSet<>();


    public WorldActor(@Autowired  ActorSystem actorSystem){
        this.actorSystem=actorSystem;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(WorldActorMessage.RequestLogin.class, this::login)
                .match(PlayerEntryRepositoryProtocol.FindByIdResult.class, this::load)
                .build();
    }

    private void login(WorldActorMessage.RequestLogin msg) {

        System.out.println("player:"+msg.getPlayerId()+" request login.");

        if (onlinePlayers.containsKey(msg.getPlayerId())) {
            System.out.println("player:"+msg.getPlayerId()+" already login.");
            //getSender().tell(new WorldActorMessage.PlayerLoginFail(1), getSelf());
            return;
        }

        if (waiterSet.contains(msg.getPlayerId())) {
            System.out.println("player:"+msg.getPlayerId()+" is logging");
            //getSender().tell(new WorldActorMessage.PlayerLoginFail(2), getSelf());
            return;
        }

        waiterSet.add(msg.getPlayerId());


        //actorSystem.child("/user/PlayerEntryRepository");

        ActorSelection selection=  actorSystem.actorSelection("akka://actorSystem/user/PlayerEntryRepository");
        selection.tell(new PlayerEntryRepositoryProtocol.FindById(getSelf(),msg.getPlayerId()),getSelf());
    }


    private void load(PlayerEntryRepositoryProtocol.FindByIdResult result) {


        ActorRef playerRef=result.getResultRef();
        if(playerRef==null){
            System.out.println("login fail :" + result.getId());
            waiterSet.remove(result.getId());
            return ;
        }else{
            System.out.println("login success :" + result.getId());
            onlinePlayers.put(result.getId(), playerRef);
            waiterSet.remove(result.getId());
            playerRef.tell(new PlayerActorProtocol.PlayerLoaded(),getSelf());
        }
    }

}
