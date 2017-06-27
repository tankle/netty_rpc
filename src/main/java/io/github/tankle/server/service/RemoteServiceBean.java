package io.github.tankle.server.service;

import io.github.tankle.server.RemoteServiceServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用来关联server名字和具体的实现
 * Created by IDEA
 * User: hztancong
 * Date: 2017/6/26
 * Time: 16:12
 */
public class RemoteServiceBean {

    private static final Logger log = LoggerFactory.getLogger(RemoteServiceBean.class);

    private String serviceName;
    private Object serviceImpl;

    public void init() {
        RemoteServiceServer.addService(serviceName, serviceImpl);
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Object getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(Object serviceImpl) {
        this.serviceImpl = serviceImpl;
    }
}
