package me.guanjun.weixin.util;

/**
 * Created by guanj on 2015/4/15.
 */
public class Constants {

    /**
     * APPID
     */
    public static String APPID = "wxf6a794703b4da4d0";
    /**
     * SECRET
     */
    public static String SECRET = "c5702021d52d863b8d0680c1de29a57a";
    /**
     * 获取ACCESS_TOKEN接口
     */
    public static String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    /**
     * ACCESS_TOKEN有效时间(单位：ms)
     */
    public static int EFFECTIVE_TIME = 700000;
    /**
     * conf.properties文件路径
     */
    public static String CONF_PROPERTIES_PATH = "src/META-INF/weixin/config.properties";
    /**
     * 微信接入token ，用于验证微信接口
     */
    public static String TOKEN = "abc123";
}
