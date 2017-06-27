package io.github.tankle.server.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by IDEA
 * User: hztancong
 * Date: 2017/6/26
 * Time: 16:48
 */
public class NettyDecoder extends ByteToMessageDecoder {
    private static final Logger log = LoggerFactory.getLogger(NettyDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        log.info("try decode:" + in.toString());

        if (in.readableBytes() < 4) {
            log.info("no enough readable bytes");
            return;
        }

        int dataLength = in.readInt();
        if (dataLength < 0) {
            ctx.close();
        }

        log.info("try decode data length:"+dataLength);

        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
        }

        log.info("try decode doDecode");

        byte[] data = new byte[dataLength];
        in.readBytes(data);

        Object deserialized = Serializer.deserialize(data);
        out.add(deserialized);

    }
}
