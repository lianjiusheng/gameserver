package com.ljs.gameserver.repository;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.dispatch.Futures;
import akka.pattern.Patterns;
import com.ljs.gameserver.springakka.SpringExtension;
import com.ljs.mg.core.actor.PlayerActor;
import com.ljs.gameserver.entry.PlayerEntry;
import com.ljs.gameserver.entry.PlayerEntrySimpleInfo;
import com.ljs.gameserver.mapper.PlayerEntryMapper;
import com.ljs.gameserver.message.repository.PlayerEntryRepositoryProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.Future;

import java.util.List;
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
        return receiveBuilder()
                .match(PlayerEntryRepositoryProtocol.FindById.class, this::findById)
                .match(PlayerEntryRepositoryProtocol.PlayerEntryLoaded.class, this::handlePlayerEentryLoaded)
                .match(PlayerEntryRepositoryProtocol.PlayerEntryCreate.class,this::handlePlayerEntryCreate)
                .match(PlayerEntryRepositoryProtocol.PlayerEntrySaveResult.class,this::handlePlayerEentrySave)
                .match(PlayerEntryRepositoryProtocol.FindAccountPlayerList.class,this::findAccountPlayerList)
                .build();
    }

    private  void findAccountPlayerList(PlayerEntryRepositoryProtocol.FindAccountPlayerList protocol) {

        ExecutionContextExecutor exe = context().system().dispatchers().lookup("blocking-io-dispatcher");

        Future<PlayerEntryRepositoryProtocol.AccountPlayerList>  future=  Futures.future(new Callable<List<PlayerEntrySimpleInfo>>()
        {
            public List<PlayerEntrySimpleInfo> call() throws InterruptedException {
                return playerEntryMapper.selectAcountPlayerList(protocol.getAccountId());
            }
        },exe).map(e->new PlayerEntryRepositoryProtocol.AccountPlayerList(protocol.getChannelId(),e) ,exe);

        Patterns.pipe(future,getContext().dispatcher()).to(getSender(),getSelf());
     }


    private  void handlePlayerEentrySave(PlayerEntryRepositoryProtocol.PlayerEntrySaveResult protocol) {
        PlayerEntryRepositoryProtocol.PlayerEntryCreated resultMsg=null;

        if(protocol.getRs()==0){
            resultMsg=new PlayerEntryRepositoryProtocol.PlayerEntryCreated(protocol.getEntry().getId(),null);
        }else{
            PlayerEntry entry=protocol.getEntry();
            ActorRef ref = getContext().actorOf(SpringExtension.getInstance().get(getContext().getSystem()).props("default"), getPlayerActorName(entry.getId()));
            resultMsg=new PlayerEntryRepositoryProtocol.PlayerEntryCreated(protocol.getEntry().getId(),ref);
        }
        protocol.getReplyTo().tell(resultMsg,getSelf());
    }

    private void handlePlayerEntryCreate(PlayerEntryRepositoryProtocol.PlayerEntryCreate protocol) {

        ExecutionContextExecutor exe = context().system().dispatchers().lookup("blocking-io-dispatcher");

        Future<PlayerEntryRepositoryProtocol.PlayerEntrySaveResult>  future=  Futures.future(new Callable<Integer>()
        {
            public Integer call() throws InterruptedException
            {
                return playerEntryMapper.insert(protocol.getEntry());
            }
        }, exe).map(e-> new PlayerEntryRepositoryProtocol.PlayerEntrySaveResult(e,protocol.getEntry(),protocol.getHandler()),exe);

        Patterns.pipe(future,getContext().dispatcher()).to(getSelf());
    }

    private void findById(PlayerEntryRepositoryProtocol.FindById protocol) {

        Optional<ActorRef> actorRefOptional = getContext().findChild(getPlayerActorName(protocol.getId()));
        if (actorRefOptional.isPresent()) {
            PlayerEntryRepositoryProtocol.FindByIdResult rs = new PlayerEntryRepositoryProtocol.FindByIdResult(protocol.getId(),actorRefOptional.get());
            getSender().tell(rs, getSelf());
            return;
        }

        ExecutionContextExecutor exe = context().system().dispatchers().lookup("blocking-io-dispatcher");

        Future<PlayerEntryRepositoryProtocol.PlayerEntryLoaded>  future=  Futures.future(new Callable<PlayerEntry>()
        {
            public PlayerEntry call() throws InterruptedException
            {
                return playerEntryMapper.selectByPrimaryKey(protocol.getId());
            }
        }, exe).map(e->new PlayerEntryRepositoryProtocol.PlayerEntryLoaded(protocol.getId(),e),exe);

        Patterns.pipe(future,getContext().dispatcher()).to(getSelf(),getSender());

    }


    private void handlePlayerEentryLoaded(PlayerEntryRepositoryProtocol.PlayerEntryLoaded protocol) {

        PlayerEntry entry = protocol.getEntry();
        if (entry == null) {
            PlayerEntryRepositoryProtocol.FindByIdResult rs = new PlayerEntryRepositoryProtocol.FindByIdResult(protocol.getId(),null);
            getSender().tell(rs, getSelf());
            return;
        }
        ActorRef ref = getContext().actorOf(Props.create(PlayerActor.class,entry), getPlayerActorName(entry.getId()));
        PlayerEntryRepositoryProtocol.FindByIdResult rs = new PlayerEntryRepositoryProtocol.FindByIdResult(protocol.getId(),ref);
        getSender().tell(rs, getSelf());
    }


    public static String getPlayerActorName(String id) {
        return "player@" + id;
    }

}
