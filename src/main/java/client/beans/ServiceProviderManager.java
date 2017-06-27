package client.beans;

/**
 * Created by IDEA
 * User: hztancong
 * Date: 2017/6/26
 * Time: 16:58
 */
public class ServiceProviderManager {

    public static ProviderInfo getProvider(String serviceName) {
//        String ip = "backend9.photo.163.org";
        String ip = "127.0.0.1";
        return new ProviderInfo(ip, 8080);
    }

}
