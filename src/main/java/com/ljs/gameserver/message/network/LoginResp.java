package com.ljs.gameserver.message.network;

public class LoginResp extends NetMessage {

    private int rs;

    public int getRs() {
        return rs;
    }

    public void setRs(int rs) {
        this.rs = rs;
    }

    public LoginResp() {
        super(Op.OP_LOGIN_RESP);

    }
    
}
