package com.ljs.mg.core;

import com.ljs.mg.core.handlers.GameMessageDispatherHandler;
import com.ljs.mg.core.handlers.GameMessagePacketCodec;
import com.ljs.mg.core.handlers.SessionHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class Server {

    private String host;

    private int port;

    private INetMessageFacotry messageFacotry;

    private IGameMessageDispather gameMessageDispather;

    private ISessionManager sessionManager;


    public void start() {

        NioEventLoopGroup parent = new NioEventLoopGroup(1);
        NioEventLoopGroup child = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(parent, child)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {

                        ChannelPipeline pipeline = ch.pipeline();

                        // decoder
                        pipeline.addLast("length-decoder", new LengthFieldBasedFrameDecoder(1024000, 0, 4));// 1M
                        pipeline.addLast("length-encoder", new LengthFieldPrepender(4));

                        pipeline.addLast(new GameMessagePacketCodec(messageFacotry));//数据包封包解包
                        pipeline.addLast(new SessionHandler(sessionManager));//会话管理
                        pipeline.addLast(new GameMessageDispatherHandler(gameMessageDispather));//消息分发
                    }
                });
        serverBootstrap.bind(host, port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.cause()!=null) {
                    child.shutdownGracefully();
                    parent.shutdownGracefully();
                    System.exit(1);
                }
            }
        }).channel().closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {

                child.shutdownGracefully();
                parent.shutdownGracefully();
                System.exit(0);
            }
        });
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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

    public ISessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
}
