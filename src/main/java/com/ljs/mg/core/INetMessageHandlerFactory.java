package com.ljs.mg.core;

public interface INetMessageHandlerFactory {
    public INetMessageHandler getNetMessageHandler(int op);
}
