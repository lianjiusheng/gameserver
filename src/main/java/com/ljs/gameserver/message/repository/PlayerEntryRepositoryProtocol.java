package com.ljs.gameserver.message.repository;

import akka.actor.Actor;
import akka.actor.ActorRef;
import com.ljs.gameserver.entry.PlayerEntry;

public class PlayerEntryRepositoryProtocol {

    /**
     * 根据ID查找玩家，查找结果通过
     * {@link FindByIdResult} 消息返回
     */
    public static class FindById{
        private String id;
        private ActorRef handler;

        /**
         * @param handler 接收消息的ActorRef
         * @param id 要查找的ID
         */
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

    /**
     * 玩家被加载消息，客户端不应该使用该消息
     */
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

    /**
     * 按ID查找玩家结果
     */
    public static class FindByIdResult{
        private String id;
        private ActorRef resultRef;
        public FindByIdResult( String id,ActorRef resultRef) {
            this.id=id;
            this.resultRef = resultRef;
        }

        /**
         * 玩家ActorRef
         * @return 如果玩家不存在则返回null
         */
        public ActorRef getResultRef() {
            return resultRef;
        }

        /**
         * 玩家ID
         * @return
         */
        public String getId() {
            return id;
        }
    }


    /**
     * 请求创建玩家，创建结果通过{@link PlayerEntryCreated}消息返回
     */
    public static class PlayerEntryCreate{
        private ActorRef handler;
        private PlayerEntry entry;

        public PlayerEntryCreate(ActorRef handler, PlayerEntry entry) {
            this.handler = handler;
            this.entry = entry;
        }

        public ActorRef getHandler() {
            return handler;
        }
        public PlayerEntry getEntry() {
            return entry;
        }
    }

    /**
     * 创建玩家结果消息
     */
    public static class PlayerEntryCreated{
        private String id;
        private ActorRef resultRef;

        public PlayerEntryCreated(String id, ActorRef resultRef) {
            this.id = id;
            this.resultRef = resultRef;
        }
        public String getId() {
            return id;
        }
        public ActorRef getResultRef() {
            return resultRef;
        }
    }

    public static  class  PlayerEntrySaveResult{

        private int rs;
        private PlayerEntry entry;
        private ActorRef replyTo;

        public PlayerEntrySaveResult(int rs, PlayerEntry entry, ActorRef replyTo) {
            this.rs = rs;
            this.entry = entry;
            this.replyTo=replyTo;
        }

        public PlayerEntry getEntry() {
            return entry;
        }

        public int getRs() {
            return rs;
        }

        public ActorRef getReplyTo() {
            return replyTo;
        }
    }

}
