package io.github.tankle.server.common;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by IDEA
 * User: hztancong
 * Date: 2017/6/26
 * Time: 16:49
 */
public class Serializer {
    private static final Logger log = LoggerFactory.getLogger(Serializer.class);


    public static byte[] serialize(Object obj) throws IOException {
        log.info(" ------------ encoding start in serialize --------" + obj.toString());
//        obj.
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(bos);
        out.writeObject(obj);
        out.close();
        log.debug("--------------The old obj is " + obj.toString());
        log.debug("---------------The new obj is " + bos.toString());
        return bos.toByteArray();
    }

    public static Object deserialize(byte[] data) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        Hessian2Input in = new Hessian2Input(bis);
        return in.readObject();
    }
}
