package com.ljs.gameserver.message.network.responses;

import com.ljs.gameserver.message.network.NetMessage;

public class RespNetMessage extends NetMessage {

    private int rs ;
    private String errorMsg;

    public RespNetMessage(int op) {
        super(op);
    }

    public int getRs() {
        return rs;
    }

    public void setRs(int rs) {
        this.rs = rs;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
