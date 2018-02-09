package com.ljs.mg.gameserver.message.network;

public class Op {


    //****//
    // 1-10000 为系统消息专用

    //******//

    public static final int  OP_LOGIN_REQ=10001;

    public static final int  OP_LOGIN_RESP=10002;

    public static final int  OP_PLAYER_LIST_RESP=10003;

    public static final int  OP_CREATE_PLAYER_REQ=10004;
    public static final int  OP_CREATE_PLAYER_RESP=10005;

    public static final int OP_ENTRY_GAME_REQ =10006;
    public static final int OP_ENTER_GAME_RESP =10007;



    public static class REQ{


    }
    public static class RESP{


    }

}
