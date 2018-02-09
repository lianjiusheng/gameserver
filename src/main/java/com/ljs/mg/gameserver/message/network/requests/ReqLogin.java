package com.ljs.mg.gameserver.message.network.requests;

import com.ljs.mg.core.packet.ReqNetMessage;
import com.ljs.mg.gameserver.message.network.Op;

public class ReqLogin extends ReqNetMessage {

    private String accountId;
    private String platform;
    private String openId;
    private long time;
    private String sign;

    public ReqLogin() {
        super(Op.OP_LOGIN_REQ);
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
