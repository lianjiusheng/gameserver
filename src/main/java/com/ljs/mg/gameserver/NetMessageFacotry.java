package com.ljs.mg.gameserver;

import com.ljs.mg.core.INetMessageFacotry;
import com.ljs.mg.core.packet.NetMessage;
import com.ljs.mg.gameserver.message.network.Op;
import com.ljs.mg.gameserver.message.network.requests.ReqCreatePlayer;
import com.ljs.mg.gameserver.message.network.requests.ReqEnterGame;
import com.ljs.mg.gameserver.message.network.requests.ReqLogin;
import com.ljs.mg.gameserver.message.network.responses.RespCreatePlayer;
import com.ljs.mg.gameserver.message.network.responses.RespEnterGame;
import com.ljs.mg.gameserver.message.network.responses.RespLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class NetMessageFacotry implements INetMessageFacotry {

    protected Logger log= LogManager.getLogger(getClass());

    private Map<Integer,Class<? extends NetMessage>> netMessageMap=new HashMap<>();

    @Override
    public Class<? extends NetMessage> getOpClass(int op) {
        return netMessageMap.get(op);
    }

    @Override
    public void registerOpClass(int op, Class<? extends NetMessage> msgClass) {
        netMessageMap.put(op,msgClass);
        log.info("registerOpClass op={},class={}",op,msgClass.getName());
    }


    @PostConstruct
    public void init(){
        registerOpClass(Op.OP_LOGIN_REQ, ReqLogin.class);
        registerOpClass(Op.OP_LOGIN_RESP, RespLogin.class);
        registerOpClass(Op.OP_CREATE_PLAYER_REQ, ReqCreatePlayer.class);
        registerOpClass(Op.OP_CREATE_PLAYER_RESP, RespCreatePlayer.class);
        registerOpClass(Op.OP_ENTRY_GAME_REQ, ReqEnterGame.class);
        registerOpClass(Op.OP_ENTER_GAME_RESP, RespEnterGame.class);
    }
}
