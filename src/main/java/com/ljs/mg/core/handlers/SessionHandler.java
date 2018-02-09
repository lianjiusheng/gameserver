package com.ljs.mg.core.handlers;

import com.ljs.mg.core.ISessionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class SessionHandler extends ChannelInboundHandlerAdapter {

   private ISessionManager sessionManager;

    public SessionHandler(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        sessionManager.sessionOpen(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        sessionManager.sessionClose(ctx.channel());
    }

}
