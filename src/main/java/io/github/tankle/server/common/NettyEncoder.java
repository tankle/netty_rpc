package io.github.tankle.server.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IDEA
 * User: hztancong
 * Date: 2017/6/26
 * Time: 16:50
 */
public class NettyEncoder extends MessageToByteEncoder {
    private static final Logger log = LoggerFactory.getLogger(NettyEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

        log.info("encoding start --------" + msg.toString());

        byte[] bytes = Serializer.serialize(msg);

        log.info("-------------------------msg encoded length:"+bytes.length);

        out.writeInt(bytes.length);
        out.writeBytes(bytes);

        log.info("msg encoded length:"+bytes.length);

    }
}
