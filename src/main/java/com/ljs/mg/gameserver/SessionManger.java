package com.ljs.mg.gameserver;

import com.google.common.base.Strings;
import com.ljs.mg.core.ISessionManager;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class SessionManger implements ISessionManager {

    public static AttributeKey<Session> sessionAttributeKey = AttributeKey.valueOf("session");
    private Map<String, Session> sessionMap = new ConcurrentHashMap<>();
    private Map<String, Session> playerSessionMap = new ConcurrentHashMap<>();

    @Override
    public void sessionOpen(Channel channel) {
        Session session = new Session(channel);
        channel.attr(sessionAttributeKey).set(session);
        sessionMap.put(channel.id().asLongText(), session);
        session.sessionOpend();
    }

    @Override
    public void sessionClose(Channel channel) {

        channel.attr(sessionAttributeKey).set(null);
        Session session = sessionMap.remove(channel.id().asLongText());
        session.sessionClosed();
        if(!Strings.isNullOrEmpty(session.getPlayerId())){
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
