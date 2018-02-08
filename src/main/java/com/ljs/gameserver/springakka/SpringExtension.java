package com.ljs.gameserver.springakka;

import akka.actor.AbstractExtensionId;
import akka.actor.ExtendedActorSystem;

public class SpringExtension  extends AbstractExtensionId<SpringExt>{


    /**
     * The identifier used to access the SpringExtension.
     */
    private static SpringExtension instance = new SpringExtension();

    private SpringExtension(){}

    /**
     * Is used by Akka to instantiate the Extension identified by this
     * ExtensionId, internal use only.
     */
    @Override
    public SpringExt createExtension(ExtendedActorSystem system) {
        return new SpringExt();
    }


    public static SpringExtension getInstance() {
        return instance;
    }
}
