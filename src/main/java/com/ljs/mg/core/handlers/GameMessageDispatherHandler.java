package com.ljs.mg.core.handlers;

import com.ljs.mg.core.IGameMessageDispather;
import com.ljs.mg.core.packet.NetMessage;
import com.ljs.mg.core.packet.ReqNetMessage;
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
            if(msg instanceof ReqNetMessage){
                ((ReqNetMessage)msg).setChannelId(ctx.channel().id().asLongText());
            }
            gameMessageDispather.dispath(message);
        }else{
            ctx.fireChannelRead(msg);
        }
    }
}
