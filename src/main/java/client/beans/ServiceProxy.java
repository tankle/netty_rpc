package client.beans;

import io.github.tankle.server.common.RemoteRequest;
import io.github.tankle.server.common.RemoteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by IDEA
 * User: hztancong
 * Date: 2017/6/26
 * Time: 13:24
 */
public class ServiceProxy implements InvocationHandler {

    private static final Logger log = LoggerFactory.getLogger(ServiceProxy.class);

    private String serviceName;

    public ServiceProxy(String serviceName) {
        this.serviceName = serviceName;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        ProviderInfo provider = ServiceProviderManager.getProvider(serviceName);

        RemoteRequest request = new RemoteRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setServiceName(serviceName);
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setArguments(args);

        RemoteClient client = new RemoteClient(provider);
        RemoteResponse response = client.send(request);

        return response.getResponseValue();
    }
}
