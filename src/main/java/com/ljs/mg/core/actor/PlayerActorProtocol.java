package com.ljs.mg.core.actor;

import com.ljs.mg.core.NetMessageHandler;
import com.ljs.mg.core.packet.ReqNetMessage;

public class PlayerActorProtocol {


    public static class PlayerActorMessage{

        private ReqNetMessage reqNetMessage;
        private NetMessageHandler requestHandler;

        public PlayerActorMessage( ReqNetMessage reqNetMessage, NetMessageHandler requestHandler) {

            this.reqNetMessage = reqNetMessage;
            this.requestHandler = requestHandler;
        }

        public ReqNetMessage getReqNetMessage() {
            return reqNetMessage;
        }

        public NetMessageHandler getRequestHandler() {
            return requestHandler;
        }

    }

}
