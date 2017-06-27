package client.beans;

import com.google.common.util.concurrent.SettableFuture;
import io.github.tankle.server.common.RemoteResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IDEA
 * User: hztancong
 * Date: 2017/6/26
 * Time: 17:01
 */
public class NettyClientHandler  extends SimpleChannelInboundHandler<RemoteResponse> {

    private static final Logger log = LoggerFactory.getLogger(NettyClientHandler.class);

    private SettableFuture<RemoteResponse> future;

    public NettyClientHandler(SettableFuture<RemoteResponse> future) {
        this.future = future;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RemoteResponse msg) throws Exception {
        future.set(msg);
    }
}
