package com.ljs.mg.client;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.ljs.mg.core.Connector;
import com.ljs.mg.gameserver.Config;
import com.ljs.mg.gameserver.message.network.requests.ReqLogin;

public class Client {

    public static String host = "127.0.0.1";
    public static int port = 10000;

    public static Client client;


    private Connector connector;


    public Client(String host, int port) {
        this.connector = new Connector();
        connector.setHost(host);
        connector.setPort(port);

        ClientNetMessageFacotry clientNetMessageFacotry=new ClientNetMessageFacotry();
        clientNetMessageFacotry.init();

        connector.setMessageFacotry(clientNetMessageFacotry);

        ClientMessageDispather clientMessageDispather = new ClientMessageDispather();
        clientMessageDispather.setNetMessageHandlerFactory(new ClientNetMessageHandlerFactory());
        connector.setGameMessageDispather(clientMessageDispather);
    }


    public void start() {
        this.connector.init();
        this.connector.connect();
    }


    public Connector getConnector() {
        return connector;
    }

    public static void main(String[] args) {

        client = new Client(host, port);

        client.start();


        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (client.getConnector().getChannel() != null) {

            ReqLogin reqLogin = new ReqLogin();
            reqLogin.setAccountId("1");
            reqLogin.setTime(System.currentTimeMillis());
            reqLogin.setOpenId("");
            reqLogin.setPlatform("");

            String sign = generateSign(reqLogin);
            reqLogin.setSign(sign);

            client.getConnector().writeMessage(reqLogin);

            try {
                Thread.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

       }


    }

    private static String generateSign(ReqLogin msg) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(msg.getAccountId()).append(";")
                .append(msg.getOpenId()).append(";")
                .append(msg.getPlatform()).append(";")
                .append(msg.getTime()).append(";")
                .append(Config.signkey);

        String sign = Hashing.sha256().hashString(stringBuilder.toString(), Charsets.UTF_8).toString();
        return sign;
    }

}
