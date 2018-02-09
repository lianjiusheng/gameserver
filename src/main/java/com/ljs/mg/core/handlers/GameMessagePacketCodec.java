package com.ljs.mg.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ljs.mg.core.INetMessageFacotry;
import com.ljs.mg.core.packet.NetMessage;
import com.ljs.mg.util.JSONUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class GameMessagePacketCodec extends MessageToMessageCodec<ByteBuf,NetMessage> {

    private Logger log= LogManager.getLogger(getClass());

    private INetMessageFacotry facotry;

    public GameMessagePacketCodec(INetMessageFacotry facotry) {
        this.facotry = facotry;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {

        int op=msg.readInt();
        int length=msg.readInt();
        byte[] jsonData=new byte[length];
        msg.readBytes(jsonData);
        Class<? extends  NetMessage> msgClass= facotry.getOpClass(op);

        if(msgClass==null){
            log.warn("找不到op={}对应的消息类型。",op);
            ctx.channel().close();
            return ;
        }
        try {
         NetMessage netMessage=JSONUtil.readObject(msgClass,jsonData);
         out.add(netMessage);
        } catch (IOException e) {
            log.error("解析op={}到{}错误。",op,msgClass,e);
            ctx.channel().close();
            return ;
        }
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NetMessage msg, List<Object> out) {

        try {
            int op=msg.getOp();
            byte[] jsonData=JSONUtil.getBytes(msg);
            ByteBuf byteBuf=ctx.alloc().buffer(jsonData.length+4+4);
            byteBuf.writeInt(op);
            byteBuf.writeInt(jsonData.length);
            byteBuf.writeBytes(jsonData);
            out.add(byteBuf);
        } catch (JsonProcessingException e) {
            log.error("编码op={}错误。",msg.getOp(),e);
            ctx.channel().close();
        }
    }
}
