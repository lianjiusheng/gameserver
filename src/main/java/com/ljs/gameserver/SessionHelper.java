package com.ljs.gameserver;


import com.ljs.gameserver.Session;
import com.ljs.gameserver.SessionManger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionHelper {

    private Logger log= LogManager.getLogger(getClass());

    @Autowired
    private SessionManger sessionManger;


    public void writeMessageTo(String sessionId,Object msg){
        Session session= sessionManger.getSession(sessionId);

        if(session!=null){
            session.writeMessage(msg);
        }else{
            log.warn("could not found session ,sessionId={}",sessionId);
        }

    }
}
