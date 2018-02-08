package com.ljs.gameserver.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.ljs.gameserver.Config;
import com.ljs.gameserver.message.AuthenticationServiceProtocol;
import com.ljs.gameserver.message.AuthenticationServiceProtocol.AuthenticateRequest;
import com.ljs.gameserver.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("AuthenticationService")
@Scope("prototype")
public class AuthenticationService extends AbstractActor {

    private SessionHelper sessionHelper;

    public AuthenticationService(@Autowired  SessionHelper sessionHelper) {
        this.sessionHelper = sessionHelper;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(AuthenticateRequest.class,this::authenticate).build();
    }

    private void authenticate(AuthenticateRequest msg) {
        String sign=generateSign(msg);

        if(sign.equalsIgnoreCase(msg.getSign())){
            AuthenticationServiceProtocol.AuthenticateSuccess result=new AuthenticationServiceProtocol.AuthenticateSuccess (msg.getChannelId(),msg.getAccountId(),msg.getPlatform(),msg.getOpenId());
            ActorSelection selection= getContext().actorSelection(ActorPathConst.WorldActorPath);
            selection.tell(result,getSelf());
        }else{
            //发送消息给客户端
            AuthenticationServiceProtocol.AuthenticateFail fail=new AuthenticationServiceProtocol.AuthenticateFail(1);
            sessionHelper.writeMessageTo(msg.getChannelId(),fail);
        }
    }


    private String generateSign(AuthenticateRequest msg){

        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder
                .append(msg.getAccountId()).append(";")
                .append(msg.getOpenId()).append(";")
                .append(msg.getPlatform()).append(";")
                .append(msg.getTime()).append(";")
                .append(Config.signkey);

       String sign= Hashing.sha256().hashString(stringBuilder.toString(), Charsets.UTF_8).toString();
       return sign;
    }

}
