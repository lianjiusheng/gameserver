package com.ljs.gameserver.message.network;

public class LoginReq extends RequestNetMessage<LoginReq> {

    private String id;

    public LoginReq() {
        super(Op.OP_LOGIN_REQ);
    }

    public String getId() {
        return id;
    }

    public LoginReq setId(String id) {
        this.id = id;
        return this;
    }


}
