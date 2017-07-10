package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IDEA
 * User: hztancong
 * Date: 2017/6/26
 * Time: 17:27
 */
public class DemoServiceImpl implements DemoService {
    private static final Logger log = LoggerFactory.getLogger(DemoServiceImpl.class);


    public String say(String input) {
        return "From Server: You are welcome";

    }

    public MessageDTO loadObject(int messageId) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessageId(1);
        messageDTO.setContent("hello world! from server");
        return messageDTO;
    }
}
