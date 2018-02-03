package com.ljs.gameserver.actor;

import akka.actor.AbstractActor;
import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.dispatch.Futures;
import akka.pattern.Patterns;
import com.ljs.gameserver.entry.PlayerEntry;
import com.ljs.gameserver.message.WorldActorMessage;
import com.ljs.gameserver.repository.PlayerEntryRepository;
import com.ljs.gameserver.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import scala.concurrent.Future;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

@Component("WorldActor")
@Scope("prototype")
public class WorldActor extends AbstractActor {

    private PlayerEntryRepository playerEntryRepository;

    //登录玩家
    private Map<String, ActorRef> onlinePlayers = new HashMap<>();
    //等待登录的玩家
    private Set<String> waiterSet = new HashSet<>();

    public WorldActor(@Autowired PlayerEntryRepository playerEntryRepository) {
        this.playerEntryRepository = playerEntryRepository;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(WorldActorMessage.RequestLogin.class, this::login).match(PlayerEntry.class, this::load).build();
    }

    private void login(WorldActorMessage.RequestLogin msg) {


        if (onlinePlayers.containsKey(msg.getPlayerId())) {

            getSender().tell(new WorldActorMessage.PlayerLoginFail(1), getSelf());
            return;
        }


        if (waiterSet.contains(msg.getPlayerId())) {
            getSender().tell(new WorldActorMessage.PlayerLoginFail(2), getSelf());
            return;
        }

        waiterSet.add(msg.getPlayerId());

        Future<PlayerEntry> future = Futures.future(new Callable<PlayerEntry>() {

            @Override
            public PlayerEntry call() throws Exception {

                PlayerEntry playerEntry = playerEntryRepository.findById(msg.getPlayerId());

                return playerEntry;
            }
        }, getContext().dispatcher());

        Patterns.pipe(future, getContext().dispatcher()).to(getSelf());


    }


    private void load(PlayerEntry msg) {


        System.out.println("login success :" + msg.getId());

        Props props = PlayerActor.props(msg);

        ActorRef playerActorRef = getContext().actorOf(props, "player@" + msg.getId());

        onlinePlayers.put(msg.getId(), playerActorRef);
        getSender().tell(new WorldActorMessage.PlayerLoginSucess(msg), getSelf());
    }


}
