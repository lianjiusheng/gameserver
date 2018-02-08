package com.ljs.gameserver.message.repository;

import akka.actor.Actor;
import akka.actor.ActorRef;
import com.ljs.gameserver.entry.PlayerEntry;
import com.ljs.gameserver.entry.PlayerEntrySimpleInfo;
import com.ljs.gameserver.message.AbstractMessage;

import java.util.List;

public class PlayerEntryRepositoryProtocol {

    /**
     * 根据ID查找玩家，查找结果通过
     * {@link FindByIdResult} 消息返回
     */
    public static class FindById extends AbstractMessage{
        private String id;
        /**
         * @param id 要查找的ID
         */
        public FindById(String id) {

            this.id = id;
        }
        public String getId() {
            return id;
        }
    }

    /**
     * 玩家被加载消息，客户端不应该使用该消息
     */
    public static class PlayerEntryLoaded extends AbstractMessage{
        private String id;
        private PlayerEntry entry;
        public PlayerEntryLoaded(String id,PlayerEntry entry) {
            this.id=id;

            this.entry = entry;
        }
        public PlayerEntry getEntry() {
            return entry;
        }

        public String getId() {
            return id;
        }
    }

    /**
     * 按ID查找玩家结果
     */
    public static class FindByIdResult extends AbstractMessage{
        private String id;
        private ActorRef resultRef;
        public FindByIdResult(String id,ActorRef resultRef) {
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
    public static class PlayerEntryCreate extends AbstractMessage{
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
    public static class PlayerEntryCreated extends AbstractMessage{
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

    public static  class  PlayerEntrySaveResult extends AbstractMessage{

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

    public static class FindAccountPlayerList extends AbstractMessage{

        private String accountId;

        public FindAccountPlayerList(String channelId, String accountId) {
            super.setChannelId(channelId);
            this.accountId = accountId;
        }

        public String getAccountId() {
            return accountId;
        }

    }

    public static  class AccountPlayerList extends AbstractMessage{

        private List<PlayerEntrySimpleInfo> simpleInfoList;

        public AccountPlayerList(String channelId,List<PlayerEntrySimpleInfo> simpleInfoList) {
            super.setChannelId(channelId);
            this.simpleInfoList = simpleInfoList;
        }

        public List<PlayerEntrySimpleInfo> getSimpleInfoList() {
            return simpleInfoList;
        }
    }

}
