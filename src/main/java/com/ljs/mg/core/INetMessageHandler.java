package com.ljs.mg.core;

import com.ljs.mg.core.packet.NetMessage;

public interface INetMessageHandler<T extends NetMessage> {

    public void handle(T message) throws Exception;


}
