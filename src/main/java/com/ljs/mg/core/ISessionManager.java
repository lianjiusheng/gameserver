package com.ljs.mg.core;


import io.netty.channel.Channel;

public interface ISessionManager {


    public void sessionOpen(Channel channel);

    public void sessionClose(Channel channel);


}
