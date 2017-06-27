package io.github.tankle.server;

import io.github.tankle.server.common.NettyDecoder;
import io.github.tankle.server.common.NettyEncoder;
import io.github.tankle.server.service.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IDEA
 * User: hztancong
 * Date: 2017/6/26
 * Time: 16:13
 */
public class RemoteServiceServer {
    private static final Logger log = LoggerFactory.getLogger(RemoteServiceServer.class);


    // 启动netty
    private static volatile boolean started = false;

    static {
        bootstrap();
    }

    // 加载rpc服务
    private static ConcurrentHashMap<String, Object> serviceImplMap = new ConcurrentHashMap<String, Object>();

    public static void addService(String serviceName, Object serviceImpl) {
        serviceImplMap.putIfAbsent(serviceName, serviceImpl);
    }



    public static void bootstrap() {
        if (!started) {
            synchronized (RemoteServiceServer.class) {
                if (!started) {
                    doStartup();
                }
            }
        }

    }

    /**
     * netty boostrap
     */
    private static void doStartup() {
        // netty boostrap

        log.info("do start server");

        // bossGroup 请求转发线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // workerGroup 请求执行线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new NettyDecoder(),
                                        new NettyEncoder(),
                                        new NettyServerHandler());
                    }
                }).option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        // 开始监听
        try {
            ChannelFuture f = bootstrap.bind(8080).sync();
            f.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        started = true;
                        log.info("server started!");
                    }
                }
            });
        } catch (InterruptedException e) {
            log.error("server started failed:"+e.getMessage(), e);
        }
    }

    /**
     * 获得rpc服务的时候，会检测netty服务是否启动
     * @param serviceName
     * @return
     */
    public static Object getActualServiceImpl(String serviceName) {
        if (!started) {
            log.warn("server not started");
            bootstrap();
        }
        log.info("cur map when get:"+serviceImplMap);
        return serviceImplMap.get(serviceName);
    }
}
