package com.ljs.gameserver.message;

import com.ljs.gameserver.entry.PlayerEntry;

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


    public static class PlayerLoginFail{

        private int rs;

        public PlayerLoginFail(int rs) {
            this.rs = rs;
        }

        public int getRs() {
            return rs;
        }
    }


    public static class PlayerLoginSucess{

        private PlayerEntry entry;

        public PlayerLoginSucess(PlayerEntry entry) {
            this.entry = entry;
        }

        public PlayerEntry getEntry() {
            return entry;
        }
    }


}
