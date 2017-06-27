package client.beans;

import com.google.common.reflect.Reflection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IDEA
 * User: hztancong
 * Date: 2017/6/26
 * Time: 17:24
 */
public class ServiceProxyBeanFactory {
    private static final Logger log = LoggerFactory.getLogger(ServiceProxyBeanFactory.class);


    public static Object getService(String serviceName) throws ClassNotFoundException {
        Class<?> serviceClass = Class.forName(serviceName);
        return Reflection.newProxy(serviceClass, new ServiceProxy(serviceName));
    }
}

