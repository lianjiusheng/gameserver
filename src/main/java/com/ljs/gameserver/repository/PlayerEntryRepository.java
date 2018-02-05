package com.ljs.gameserver.repository;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.dispatch.Futures;
import akka.pattern.Patterns;
import com.ljs.gameserver.actor.PlayerActor;
import com.ljs.gameserver.entry.PlayerEntry;
import com.ljs.gameserver.mapper.PlayerEntryMapper;
import com.ljs.gameserver.message.repository.PlayerEntryRepositoryProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.Future;

import java.util.Optional;
import java.util.concurrent.Callable;


@Component("PlayerEntryRepository")
@Scope("prototype")
public class PlayerEntryRepository extends AbstractActor {

    private PlayerEntryMapper playerEntryMapper;

    public PlayerEntryRepository(@Autowired PlayerEntryMapper playerEntryMapper) {
        this.playerEntryMapper = playerEntryMapper;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(PlayerEntryRepositoryProtocol.FindById.class, this::findById).match(PlayerEntryRepositoryProtocol.PlayerEntryLoaded.class, this::handlePlayerEentryLoaded).build();
    }


    private void findById(PlayerEntryRepositoryProtocol.FindById protocol) {

        Optional<ActorRef> actorRefOptional = getContext().findChild(getPlayerActorName(protocol.getId()));
        if (actorRefOptional.isPresent()) {
            PlayerEntryRepositoryProtocol.FindByIdResult rs = new PlayerEntryRepositoryProtocol.FindByIdResult(protocol.getId(),actorRefOptional.get());
            protocol.getHandler().tell(rs, getSelf());
            return;
        }

//        ExecutionContextExecutor exe = context().system().dispatchers().lookup("blocking-io-dispatcher");
//
//        Future<PlayerEntry> future = Futures.future(new Callable<PlayerEntry>()
//        {
//            public PlayerEntry call() throws InterruptedException
//            {
//                return playerEntryMapper.selectByPrimaryKey(protocol.getId());
//            }
//        }, exe).map(e-> new PlayerEntryRepositoryProtocol.PlayerEntryLoaded(protocol.getId(),protocol.getHandler(),e));
//
//        Patterns.pipe(future,getContext().dispatcher()).to(getSelf());

        PlayerEntry playerEntry=playerEntryMapper.selectByPrimaryKey(protocol.getId());
        PlayerEntryRepositoryProtocol.PlayerEntryLoaded rs = new PlayerEntryRepositoryProtocol.PlayerEntryLoaded(protocol.getId(),protocol.getHandler(),playerEntry);
        getSelf().tell(rs, getSelf());
    }


    private void handlePlayerEentryLoaded(PlayerEntryRepositoryProtocol.PlayerEntryLoaded protocol) {

        PlayerEntry entry = protocol.getEntry();
        if (entry == null) {
            PlayerEntryRepositoryProtocol.FindByIdResult rs = new PlayerEntryRepositoryProtocol.FindByIdResult(protocol.getId(),null);
            protocol.getReplyTo().tell(rs, getSelf());
            return;
        }
        ActorRef ref = getContext().actorOf(PlayerActor.props(entry), getPlayerActorName(entry.getId()));

        PlayerEntryRepositoryProtocol.FindByIdResult rs = new PlayerEntryRepositoryProtocol.FindByIdResult(protocol.getId(),ref);
        protocol.getReplyTo().tell(rs, getSelf());
    }


    private String getPlayerActorName(String id) {
        return "player@" + id;
    }

}
