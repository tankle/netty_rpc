package client;

import demo.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by IDEA
 * User: hztancong
 * Date: 2017/6/26
 * Time: 17:30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-client.xml"} )
public class TestMain {


    @Autowired
    private DemoService demoService;

    @Test
    public void test(){
        System.out.println("Begin");
        String str = "Hello World! from client";
        String result = demoService.say(str);

        System.out.println(str);
        System.out.println("The result is ++++++++++++++++++++: " + result);
    }

//    @Test
    public void testLoadMessageObject(){
//        MessageDTO messageDTO = demoService.loadObject(1);
//        System.out.println(messageDTO);
    }

//    @Test
//    public void testBatchInvocation() throws InterruptedException {
//        final int THREAD_COUNT = 50;
//        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
//        ExecutorService threadPool = Executors.newFixedThreadPool(10);
//        for (int i = 0; i < THREAD_COUNT; i++) {
//            MockServiceInvocation invocation = new MockServiceInvocation(demoService, latch);
//            threadPool.submit(invocation);
//        }
//        latch.await();
//    }
//
//    private static class MockServiceInvocation implements Runnable {
//
//        private CountDownLatch latch;
//        private DemoService demoService;
//
//        private MockServiceInvocation(DemoService demoService, CountDownLatch latch) {
//            this.demoService = demoService;
//            this.latch = latch;
//        }
//
//        public void run() {
//            String result = demoService.echo("Hello World!");
//            System.out.println(result);
//            latch.countDown();
//        }
//    }
}
