package com.ljs.mg.client;

import com.ljs.mg.core.INetMessageHandler;
import com.ljs.mg.core.INetMessageHandlerFactory;
import org.springframework.stereotype.Component;

public class ClientNetMessageHandlerFactory implements INetMessageHandlerFactory {

    @Override
    public INetMessageHandler getNetMessageHandler(int op) {
        return null;
    }
}
