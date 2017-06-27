package io.github.tankle.server.service;

import io.github.tankle.server.RemoteServiceServer;
import io.github.tankle.server.common.RemoteRequest;
import io.github.tankle.server.common.RemoteResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by IDEA
 * User: hztancong
 * Date: 2017/6/26
 * Time: 16:41
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RemoteRequest> {

    private static final Logger log = LoggerFactory.getLogger(NettyServerHandler.class);

    /**
     * 根据名字调用 相关rpc 服务进行处理
     * @param ctx
     * @param request
     * @throws Exception
     */
    protected void channelRead0(ChannelHandlerContext ctx, RemoteRequest request) throws Exception {

        Object actualServiceImpl = RemoteServiceServer.getActualServiceImpl(request.getServiceName());

        log.info("--------------------Begin handle the request --------------------" );

        if (actualServiceImpl != null) {

            RemoteResponse response = new RemoteResponse();
            response.setRequestId(request.getRequestId());

            //根据request中的方法名，参数类型，参数等信息，反射调用serviceImpl

            Class<?> serviceInterface = actualServiceImpl.getClass();
            Method method = serviceInterface.getMethod(request.getMethodName(), request.getParameterTypes());
            log.info("Begin invoke the method" + method.getName());
            //反射计算得到结果
            Object result = method.invoke(actualServiceImpl, request.getArguments());

            log.info("get result from server:"+result);

            response.setResponseValue(result);

            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE)
                    .addListener(new ChannelFutureListener() {
                        public void operationComplete(ChannelFuture future) throws Exception {
                            log.info("server write back success");
                        }
            });


        }
    }
}
