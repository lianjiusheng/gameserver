package com.ljs.mg.core.handlers;

import com.ljs.mg.core.IGameMessageDispather;
import com.ljs.mg.core.packet.NetMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class GameMessageDispatherHandler extends ChannelInboundHandlerAdapter {

    private IGameMessageDispather gameMessageDispather;

    public GameMessageDispatherHandler(IGameMessageDispather gameMessageDispather) {
        this.gameMessageDispather = gameMessageDispather;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof NetMessage){
            NetMessage message=(NetMessage) msg;
            gameMessageDispather.dispath(message);
        }else{
            ctx.fireChannelRead(msg);
        }
    }
}
