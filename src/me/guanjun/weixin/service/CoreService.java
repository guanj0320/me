package me.guanjun.weixin.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import me.guanjun.weixin.entity.autoReply.MessageResponse;
import me.guanjun.weixin.robot.TulingApiProcess;
import me.guanjun.weixin.util.MessageUtil;

/**
 * Created by guanj on 2015/4/15.
 */
public class CoreService {

    /**
     * 处理微信发来的请求
     *
     * @param request
     * @return
     */
    public static String processRequest(HttpServletRequest request) {
        String respMessage = null;
        try {
            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";

            // xml请求解析
            // 调用消息工具类MessageUtil解析微信发来的xml格式的消息，解析的结果放在HashMap里；
            Map<String, String> requestMap = MessageUtil.parseXml(request);

            // 从HashMap中取出消息中的字段；
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 消息内容
            String content = requestMap.get("Content");
            // 从HashMap中取出消息中的字段；


            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                //微信聊天机器人测试 2015-3-31
                if(content!=null){
                    respContent = TulingApiProcess.getTulingResult(content);
                    if(respContent==""||null==respContent){
                        MessageResponse.getTextMessage(fromUserName , toUserName , "服务号暂时无法回复，请稍后再试！");
                    }
                    //return FormatXmlProcess.formatXmlAnswer(toUserName, fromUserName, respContent);
                    return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);
                }
//				else if (content.startsWith("ZY")) {//查作业
//					String keyWord = content.replaceAll("^ZY" , "").trim();
//					if ("".equals(keyWord)) {
//						respContent = AutoReply.getXxtUsage("24");
//					} else {
//						return XxtService.getHomework("24" , fromUserName , toUserName , keyWord);
//					}
//				} else if (content.startsWith("SJX")) {//收件箱
//					String keyWord = content.replaceAll("^SJX" , "").trim();
//					if ("".equals(keyWord)) {
//						respContent = AutoReply.getXxtUsage("25");
//					} else {
//						return XxtService.getRecvBox("25" , fromUserName , toUserName , keyWord);
//					}
//				}
//				return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {// 事件推送
                String eventType = requestMap.get("Event");// 事件类型

                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {// 订阅
                    respContent = "欢迎关注几个意思！";
                    return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {// 取消订阅
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {// 自定义菜单点击事件
                    String eventKey = requestMap.get("EventKey");// 事件KEY值，与创建自定义菜单时指定的KEY值对应

                    return MenuClickService.getClickResponse(eventKey , fromUserName , toUserName);
                }
            }





            //开启微信声音识别测试 2015-3-30
            else if(msgType.equals("voice"))
            {
                String recvMessage = requestMap.get("Recognition");
                //respContent = "收到的语音解析结果："+recvMessage;
                if(recvMessage!=null){
                    respContent = TulingApiProcess.getTulingResult(recvMessage);
                }else{
                    respContent = "您说的太模糊了，能不能重新说下呢？";
                }
                return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);
            }
            //拍照功能
            else if(msgType.equals("pic_sysphoto"))
            {

            }
            else
            {
                return MessageResponse.getTextMessage(fromUserName, toUserName, "返回为空");
            }





        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return respMessage;
    }
}
