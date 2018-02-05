package com.ljs.gameserver.message.repository;

import akka.actor.Actor;
import akka.actor.ActorRef;
import com.ljs.gameserver.entry.PlayerEntry;

public class PlayerEntryRepositoryProtocol {

    public static class FindById{
        private String id;
        private ActorRef handler;

        public FindById(ActorRef handler,String id) {
            this. handler=handler;
            this.id = id;
        }
        public String getId() {
            return id;
        }
        public ActorRef getHandler() {
            return handler;
        }
    }

    public static class PlayerEntryLoaded{
        private String id;
        private ActorRef replyTo;
        private PlayerEntry entry;
        public PlayerEntryLoaded(String id,ActorRef replyTo,PlayerEntry entry) {
            this.id=id;
            this.replyTo=replyTo;
            this.entry = entry;
        }
        public PlayerEntry getEntry() {
            return entry;
        }
        public ActorRef getReplyTo() {
            return replyTo;
        }

        public String getId() {
            return id;
        }
    }

    public static class FindByIdResult{
        private String id;
        private ActorRef resultRef;
        public FindByIdResult( String id,ActorRef resultRef) {
            this.id=id;
            this.resultRef = resultRef;
        }
        public ActorRef getResultRef() {
            return resultRef;
        }
        public String getId() {
            return id;
        }
    }
}
