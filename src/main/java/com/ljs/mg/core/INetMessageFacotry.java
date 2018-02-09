package com.ljs.mg.core;

import com.ljs.mg.core.packet.NetMessage;

public interface INetMessageFacotry {

    public < T extends NetMessage> Class<T>  getOpClass(int op);

    public < T extends NetMessage> void registerOpClass(int op,Class<T> msgClass);
}
