package com.ljs.gameserver;

import com.google.common.base.Strings;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Session {

    private static Logger log= LogManager.getLogger(Session.class);
    private volatile Channel channel;
    private ChannelId channelId;

    private String playerId;

    public Session(Channel channel) {
        this.channel = channel;
        this.channelId=channel.id();
    }

    public Channel getChannel() {
        return channel;
    }


    public String getPlayerId() {
        return playerId;
    }


    public void setPlayerId(String playerId){
        this.playerId=playerId;
    }

    public boolean isAuth(){
        return !Strings.isNullOrEmpty(playerId);
    }

    public void writeMessage(Object msg){

        if(channel!=null) {
            channel.writeAndFlush(msg);
        }else{
            log.warn("channel is null ,channelId={}.",channelId.asLongText());
        }
    }

    /**
     * 会话打开
     */
    public void sessionOpend(){
        log.info("session opend ,channelId={}.",channelId.asLongText());
    }

    /**
     * 会话关闭
     * */
    public void sessionClosed(){
        log.info("session opend ,channelId={}.",channelId.asLongText());
        channel=null;
    }

    /**
     * 重新绑定sockek
     * @param channel
     */
    public void rebind(Channel channel){

        ChannelId oldChannelId=this.channelId;
        this.channel=channel;
        this.channelId=channel.id();
        log.info("session rebind , old channelId={}， new channelId ={}",oldChannelId.asLongText(),channelId.asLongText());
    }
}
