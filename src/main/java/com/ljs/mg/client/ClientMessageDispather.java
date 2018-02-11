package com.ljs.mg.client;

import com.ljs.mg.core.IGameMessageDispather;
import com.ljs.mg.core.INetMessageHandler;
import com.ljs.mg.core.packet.NetMessage;
import com.ljs.mg.core.packet.RespNetMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ClientMessageDispather implements IGameMessageDispather {

    protected Logger log= LogManager.getLogger(getClass());

    private  ClientNetMessageHandlerFactory netMessageHandlerFactory;

    public ClientNetMessageHandlerFactory getNetMessageHandlerFactory() {
        return netMessageHandlerFactory;
    }

    public void setNetMessageHandlerFactory(ClientNetMessageHandlerFactory netMessageHandlerFactory) {
        this.netMessageHandlerFactory = netMessageHandlerFactory;
    }

    @Override
    public void dispath(NetMessage message) {

        int op=message.getOp();

        if(message instanceof RespNetMessage){

            INetMessageHandler messageHandler=netMessageHandlerFactory.getNetMessageHandler(op);
            if(messageHandler==null){
                log.warn("找不到op={}的处理类",op);
                return ;
            }
            try {
                messageHandler.handle(message);
            } catch (Exception e) {
                log.error("处理消息错误",e);
                return ;
            }

        }else{
            log.warn("暂不支持改类型消息，消息类型：",message.getClass());
        }
    }
}
