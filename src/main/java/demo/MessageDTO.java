package demo;

import java.io.Serializable;

/**
 * Created by IDEA
 * User: hztancong
 * Date: 2017/6/26
 * Time: 17:27
 */
public class MessageDTO implements Serializable{
    private int messageId;
    private String content;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
