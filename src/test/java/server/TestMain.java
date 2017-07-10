package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * Created by IDEA
 * User: hztancong
 * Date: 2017/6/26
 * Time: 17:32
 */
public class TestMain {
    private static final Logger log = LoggerFactory.getLogger(TestMain.class);

    public static void main(String[] args) throws InterruptedException {
        log.info("demoServer start");
        new ClassPathXmlApplicationContext("classpath*:applicationContext-server.xml");
        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }
}
