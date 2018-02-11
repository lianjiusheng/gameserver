package com.ljs.mg.core;

import com.ljs.mg.core.handlers.GameMessageDispatherHandler;
import com.ljs.mg.core.handlers.GameMessagePacketCodec;
import com.ljs.mg.core.packet.NetMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;


public class Connector {

    private Logger logger= LogManager.getLogger(getClass());

    private int port;

    private String host;

    private INetMessageFacotry messageFacotry;

    private IGameMessageDispather gameMessageDispather;

    private volatile Channel channel;

    private Bootstrap bootstrap;


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public INetMessageFacotry getMessageFacotry() {
        return messageFacotry;
    }

    public void setMessageFacotry(INetMessageFacotry messageFacotry) {
        this.messageFacotry = messageFacotry;
    }

    public IGameMessageDispather getGameMessageDispather() {
        return gameMessageDispather;
    }

    public void setGameMessageDispather(IGameMessageDispather gameMessageDispather) {
        this.gameMessageDispather = gameMessageDispather;
    }

    public void init(){

        NioEventLoopGroup group= new NioEventLoopGroup(1);

        bootstrap=new Bootstrap();
        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {

                        ChannelPipeline pipeline = ch.pipeline();
                        // decoder
                        pipeline.addLast("length-decoder", new LengthFieldBasedFrameDecoder(1024000, 0, 4,0,4));// 1M
                        pipeline.addLast("length-encoder", new LengthFieldPrepender(4));
                        pipeline.addLast(new GameMessagePacketCodec(messageFacotry));//数据包封包解包
                        pipeline.addLast(new GameMessageDispatherHandler(gameMessageDispather));//消息分发
                    }
                });
    }


    public void connect(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                connectImpl();
            }
        },"connect-thread").start();
    }

    private void connectImpl(){

        logger.info("connect to server {}:{}",host,port);
        ChannelFuture future= bootstrap.connect(host,port).syncUninterruptibly();
        logger.info("connect to server {}:{} sucess",host,port);
        channel=future.channel();
        writeBeginMessage();
        channel.closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                logger.info("channel to server {}:{} closed ",host,port);
                channel.eventLoop().schedule(new Runnable() {
                    @Override
                    public void run() {

                        Connector.this.connectImpl();
                    }
                },15L, TimeUnit.SECONDS);
            }
        });

    }

    public void writeMessage(NetMessage message){

        Channel channel=this.channel;
        if(channel!=null){
            channel.writeAndFlush(message);
        }else{
            logger.warn("channel is null to server {}:{}",host,port);
        }
    }

    protected void writeBeginMessage(){
        //TODO 当连接建立是发送第一条消息，例如注册消息
    }


    public Channel getChannel() {
        return channel;
    }
}
