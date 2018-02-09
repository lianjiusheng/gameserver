package com.ljs.mg.core.actor;

import com.ljs.mg.core.INetMessageHandler;
import com.ljs.mg.core.packet.ReqNetMessage;

public class PlayerActorProtocol {


    public static class PlayerActorMessage{

        private ReqNetMessage reqNetMessage;
        private INetMessageHandler requestHandler;

        public PlayerActorMessage( ReqNetMessage reqNetMessage, INetMessageHandler requestHandler) {

            this.reqNetMessage = reqNetMessage;
            this.requestHandler = requestHandler;
        }

        public ReqNetMessage getReqNetMessage() {
            return reqNetMessage;
        }

        public INetMessageHandler getRequestHandler() {
            return requestHandler;
        }

    }

}
