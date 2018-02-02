package com.ljs.gameserver.message;

public class WorldActorMessage {

    public static class RequestLogin{

        private final String playerId;
        private final String sign;

        public RequestLogin(String playerId, String sign) {
            this.playerId = playerId;
            this.sign = sign;
        }

        public String getPlayerId() {
            return playerId;
        }

        public String getSign() {
            return sign;
        }
    }


    public static class PlayerLogined{


    }

}
