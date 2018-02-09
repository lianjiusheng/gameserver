package com.ljs.mg.gameserver.message.network.responses;

import com.ljs.mg.core.packet.RespNetMessage;
import com.ljs.gameserver.message.network.Op;
import com.ljs.mg.gameserver.message.network.Op;

public class LoginResp extends RespNetMessage {

    public LoginResp() {
        super(Op.OP_LOGIN_RESP);
    }
    
}
