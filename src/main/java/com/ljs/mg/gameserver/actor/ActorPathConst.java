package com.ljs.mg.gameserver.actor;

public class ActorPathConst {

    public static final String sytemName="actorSystem";

    public static final String WorldActorPath="akka://"+sytemName+"/user/WorldActor";

    public static final String AuthenticationServicePath="akka://"+sytemName+"/user/AuthenticationService";

    public static final String PlayerEntryRepositoryPath="akka://"+sytemName+"/user/PlayerEntryRepository";

    public static final String PlayerEntryPath="akka://"+sytemName+"/user/PlayerEntryRepository/player@";

    public static String getPlayerActorName(String id) {
        return PlayerEntryPath + id;
    }

}
