package com.ljs.mg.core.packet;

public abstract class RespNetMessage extends NetMessage {

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
