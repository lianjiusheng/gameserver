package com.ljs.mg.gameserver;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameserverApplicationTests {

	@Test
	public void contextLoads() {




		ActorSystem system = ActorSystem.create("actorSystem");

		final ActorRef ref=system.actorOf(Props.create(Parent.class));

		new Thread(new Runnable() {
			@Override
			public void run() {
				ref.tell("AAA",ActorRef.noSender());
				ref.tell(111,ActorRef.noSender());
			}
		}).start();
	}

}
