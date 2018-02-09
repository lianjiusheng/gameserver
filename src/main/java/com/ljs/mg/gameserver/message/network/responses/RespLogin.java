package com.ljs.mg.gameserver.message.network.responses;

import com.ljs.mg.core.packet.RespNetMessage;
import com.ljs.mg.gameserver.message.network.Op;

public class RespLogin extends RespNetMessage {

    public RespLogin() {
        super(Op.OP_LOGIN_RESP);
    }
    
}
