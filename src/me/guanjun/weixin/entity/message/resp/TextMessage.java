package me.guanjun.weixin.entity.message.resp;

/**
 * Created by guanj on 2015/4/15.
 */
public class TextMessage extends BaseMessage {

    // 消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
