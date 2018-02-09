package com.ljs.mg.gameserver;

import com.ljs.mg.core.INetMessageHandler;
import com.ljs.mg.core.INetMessageHandlerFactory;
import org.springframework.stereotype.Component;

@Component
public class NetMessageHandlerFactory implements INetMessageHandlerFactory {

    @Override
    public INetMessageHandler getNetMessageHandler(int op) {
        return null;
    }
}
