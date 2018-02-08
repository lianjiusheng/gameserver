package com.ljs.gameserver;

import io.netty.channel.*;
import io.netty.util.AttributeKey;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ChannelHandler.Sharable
@Component
public class SessionManger extends ChannelInboundHandlerAdapter {

    public static AttributeKey<Session> sessionAttributeKey = AttributeKey.valueOf("session");
    private Map<String, Session> sessionMap = new ConcurrentHashMap<>();
    private Map<String, Session> playerSessionMap = new ConcurrentHashMap<>();


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        Session session = new Session(channel);
        channel.attr(sessionAttributeKey).set(session);
        sessionMap.put(channel.id().asLongText(), session);
        session.sessionOpend();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        channel.attr(sessionAttributeKey).set(null);
        Session session = sessionMap.remove(channel.id().asLongText());
        session.sessionClosed();
        if(session.isAuth()){
            playerSessionMap.remove(session.getPlayerId());
        }
    }

    /**
     * 设置会话绑定的玩家
     * @param sessionId
     * @param playerId
     */
    public void setSessionPlayerId(String sessionId, String playerId) {
        Session session = sessionMap.get(sessionId);
        session.setPlayerId(playerId);
        playerSessionMap.put(playerId, session);
    }

    /**
     * 重新绑定会话用的channel
     * @param playerId
     * @param sessionId
     */
    public void rebind(String playerId, String sessionId) {

        Session session = playerSessionMap.get(playerId);
        Session newSession = sessionMap.get(sessionId);
        Channel newChannel = newSession.getChannel();
        newChannel.attr(sessionAttributeKey).set(session);
        session.rebind(newChannel);
        sessionMap.put(newChannel.id().asLongText(), session);
    }


    public Session getSession(String sessionId){
        return sessionMap.get(sessionId);
    }
}
