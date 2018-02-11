package com.ljs.mg.gameserver.actor;

public class ActorPathConst {

    public static final String sytemName="actorSystem";

    public static final String AuthenticationServicePath="akka://"+sytemName+"/user/AuthenticationService";

    public static final String PlayerCreatorActor="akka://"+sytemName+"/user/PlayerCreatorActor";

    public static final String PlayerLoaderActor="akka://"+sytemName+"/user/PlayerLoaderActor";

    public static final String PlayerEntryPath="akka://"+sytemName+"/user/player@";

    public static String getPlayerActorName(String id) {
        return PlayerEntryPath + id;
    }

}
