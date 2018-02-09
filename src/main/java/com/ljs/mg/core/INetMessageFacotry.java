package com.ljs.mg.core;

import com.ljs.mg.core.packet.NetMessage;

public interface INetMessageFacotry {

    public Class< ? extends NetMessage>  getOpClass(int op);

    public  void registerOpClass(int op,Class<? extends NetMessage> msgClass);
}
