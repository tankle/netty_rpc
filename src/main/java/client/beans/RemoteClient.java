package client.beans;

import com.google.common.util.concurrent.SettableFuture;
import io.github.tankle.server.common.NettyDecoder;
import io.github.tankle.server.common.NettyEncoder;
import io.github.tankle.server.common.RemoteRequest;
import io.github.tankle.server.common.RemoteResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by IDEA
 * User: hztancong
 * Date: 2017/6/26
 * Time: 16:59
 */
public class RemoteClient {

    private static final Logger log = LoggerFactory.getLogger(RemoteClient.class);

    private ProviderInfo providerInfo;

    public RemoteClient(ProviderInfo providerInfo) {
        this.providerInfo = providerInfo;
    }

    /**
     * 发送消息的具体实现
     * @param request
     * @return
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public RemoteResponse send(RemoteRequest request) throws TimeoutException, ExecutionException, InterruptedException {

        log.info("-----------send remote request!---------------" + request.getMethodName());

        final SettableFuture<RemoteResponse> future = SettableFuture.create();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(bossGroup)   //客户端只有一个group
                    .channel(NioSocketChannel.class) //客户端用NioSocketChannel
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyDecoder(), new NettyEncoder(), new NettyClientHandler(future));
                        }
                    });

            // 绑定监听
            ChannelFuture f = bootstrap.connect(providerInfo.getAddress(), providerInfo.getPort()).sync();
            f.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    log.info("client connect success!");
                }
            });
            // 发送数据
            ChannelFuture writeFuture = f.channel().writeAndFlush(request);
            writeFuture.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    log.info("client write complete!");
                }
            });

            // 接收数据，并设置超时时间， 异步转同步
            return future.get(5, TimeUnit.SECONDS);
        } finally {
            bossGroup.shutdownGracefully();
        }
    }
}
