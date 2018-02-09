package com.ljs.mg.gameserver.message;

/**
 * 认证服务器协议
 */
public class AuthenticationServiceProtocol {

    /**
     * 认证请求
     */
    public static class AuthenticateRequest extends AbstractMessage{

        private String channelId;
        private String accountId;
        private String platform;
        private String openId;
        private long time;
        private String sign;

        public AuthenticateRequest(String channelId,String accountId, String platform, String openId, long time, String sign) {

            this.channelId=channelId;
            this.accountId = accountId;
            this.platform = platform;
            this.openId = openId;
            this.time = time;
            this.sign = sign;
        }


        public String getChannelId() {
            return channelId;
        }

        public String getAccountId() {
            return accountId;
        }

        public String getPlatform() {
            return platform;
        }

        public String getOpenId() {
            return openId;
        }

        public long getTime() {
            return time;
        }

        public String getSign() {
            return sign;
        }
    }


    /**
     * 认证失败
     */
    public static class AuthenticateFail extends AbstractMessage{

        private int reason;
        public AuthenticateFail(int reason) {
            this.reason = reason;
        }

        public int getReason() {
            return reason;
        }

    }

    /**
     * 认证成功
     */
    public static class AuthenticateSuccess extends AbstractMessage{

        private String channelId;
        private String accountId;
        private String platform;
        private String openId;

        public AuthenticateSuccess(String channelId, String accountId, String platform, String openId) {
            this.channelId=channelId;
            this.accountId = accountId;
            this.platform = platform;
            this.openId = openId;
        }

        public String getChannelId() {
            return channelId;
        }

        public String getOpenId() {
            return openId;
        }

        public String getAccountId() {
            return accountId;
        }

        public String getPlatform() {
            return platform;
        }
    }


}
