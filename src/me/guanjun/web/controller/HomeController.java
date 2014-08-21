package me.guanjun.web.controller;

import com.alibaba.fastjson.JSONObject;
import me.guanjun.service.ArticleService;
import me.guanjun.service.BannerService;
import me.guanjun.web.BaseController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pf.tools.DateUtil;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Created by guanj on 2014/4/24.
 */
@Controller
public class HomeController extends BaseController {

    private static final Log log = LogFactory.getLog(HomeController.class);

    @Resource
    private BannerService bannerService;

    @Resource
    private ArticleService articleService;

    @RequestMapping(value="/home", method = RequestMethod.GET)
     public String home(Map model) {
        //个人首页需要从数据库中取的操作
        try {

            model.put("banners",this.getBanners());
            model.put("articles",this.getArticles());
//            log.debug("===weather==start===================================================");
//
//            model.put("weather",this.getWeather(""));
//            log.debug("===weather==end===================================================");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "home";
    }

    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public String adminHome() {
        //个人首页需要从数据库中取的操作

        return "/admin/home";
    }

    private List getBanners() throws Exception {
       return bannerService.getBanners("%%",0,4);
    }

    private List getArticles() throws Exception {
        return articleService.getArticlesForHome("",0,5);
    }

//    private Map getWeather(String ip) throws Exception {
//        Map result = new HashMap();
//
//        String url = "http://v.juhe.cn/weather/ip?ip=";//url为请求的api接口地址
//        String key= "################################";//申请的对应key
//        String urlAll = new StringBuffer(url).append(ip).append("&key=").append(key).toString();
//        String charset ="UTF-8";
//        String jsonResult = getResult(urlAll, charset);//得到JSON字符串
//        log.debug("===weather==json===================================================");
//
//        log.debug(jsonResult);
//
//        JSONObject object = JSONObject.parseObject(jsonResult);//转化为JSON类
//        String code = object.getString("error_code");//得到错误码
//        //错误码判断
//        if(code.equals("0")){
//            //根据需要取得数据
//            JSONObject array = object.getJSONObject("result");
//
//            //得到当前实况天气
//            JSONObject sk = array.getJSONObject("sk");
//            System.out.println(sk.getString("temp"));//当前温度
//            System.out.println(sk.getString("wind_direction"));//当前风向
//            System.out.println(sk.getString("wind_strength"));//当前风力
//            System.out.println(sk.getString("humidity"));//当前湿度
//            System.out.println(sk.getString("time"));//更新时间
//
//            //得到今天天气
//            JSONObject today = array.getJSONObject("today");
//            System.out.println(today.getString("city"));//城市
//            String today_date = today.getString("date_y");//日期
//            System.out.println(today_date);
//            System.out.println(today.getString("week"));//星期
//            System.out.println(today.getString("temperature"));//今日温度
//            System.out.println(today.getString("weather"));//今日天气
//            System.out.println(today.getString("wind"));//风
//            //得到天气标识
//            JSONObject weatherid = today.getJSONObject("weather_id");
//            String fa = weatherid.getString("fa");//标识
//            String fb = weatherid.getString("fb");//如果fa不等于fb，说明是组合天气
//            System.out.println(fa);
//            System.out.println(fb);
//
//            result.put("temp",sk.getString("temp"));
//            result.put("wind_direction",sk.getString("wind_direction"));
//            result.put("wind_strength",sk.getString("wind_strength"));
//            result.put("humidity",sk.getString("humidity"));
//            result.put("time",sk.getString("time"));
//
//            result.put("city",today.getString("city"));
//            result.put("date_y",today.getString("date_y"));
//            result.put("week",today.getString("week"));
//            result.put("temperature",today.getString("temperature"));
//            result.put("weather",today.getString("weather"));
//            result.put("wind",today.getString("wind"));
//            result.put("fa",weatherid.getString("fa"));
//            result.put("fb",weatherid.getString("fb"));
//
//
//            //得到未来6天天气
//            Date t = DateUtil.toDate(today_date, "yyyy年MM月dd日");
//            JSONObject future = array.getJSONObject("future");
//            String next1 = DateUtil.getDate(DateUtil.addDaysToDate(t,1),"yyyyMMdd");
//            String next2 = DateUtil.getDate(DateUtil.addDaysToDate(t,2),"yyyyMMdd");
//            String next3 = DateUtil.getDate(DateUtil.addDaysToDate(t,3),"yyyyMMdd");
//            String next4 = DateUtil.getDate(DateUtil.addDaysToDate(t,4),"yyyyMMdd");
//            String next5 = DateUtil.getDate(DateUtil.addDaysToDate(t,5),"yyyyMMdd");
//            String next6 = DateUtil.getDate(DateUtil.addDaysToDate(t,6),"yyyyMMdd");
//
//            List futureList = new ArrayList();
//
//            //未来第1天天气
//            JSONObject day1 = future.getJSONObject("day_"+next1);
//            System.out.println(day1.getString("date"));//日期
//            System.out.println(day1.getString("week"));//星期
//            System.out.println(day1.getString("temperature"));//温度
//            System.out.println(day1.getString("weather"));//天气
//            JSONObject weatherid1 = day1.getJSONObject("weather_id");
//            String fa1 = weatherid1.getString("fa");//标识
//            String fb1 = weatherid1.getString("fb");//如果fa不等于fb，说明是组合天气
//            System.out.println(fa1);
//            System.out.println(fb1);
//            System.out.println(day1.getString("wind"));//风
//
//            Map day1Map = new HashMap();
//            day1Map.put("date",day1.getString("date"));
//            day1Map.put("week",day1.getString("week"));
//            day1Map.put("temperature",day1.getString("temperature"));
//            day1Map.put("weather",day1.getString("weather"));
//            day1Map.put("wind",day1.getString("wind"));
//            day1Map.put("fa",weatherid1.getString("fa"));
//            day1Map.put("fb",weatherid1.getString("fb"));
//            futureList.add(day1Map);
//
//            //未来第2天天气
//            JSONObject day2 = future.getJSONObject("day_"+next2);
//            System.out.println(day2.getString("date"));//日期
//            System.out.println(day2.getString("week"));//星期
//            System.out.println(day2.getString("temperature"));//温度
//            System.out.println(day2.getString("weather"));//天气
//            JSONObject weatherid2 = day2.getJSONObject("weather_id");
//            String fa2 = weatherid2.getString("fa");//标识
//            String fb2 = weatherid2.getString("fb");//如果fa不等于fb，说明是组合天气
//            System.out.println(fa2);
//            System.out.println(fb2);
//            System.out.println(day2.getString("wind"));//风
//
//            Map day2Map = new HashMap();
//            day2Map.put("date",day2.getString("date"));
//            day2Map.put("week",day2.getString("week"));
//            day2Map.put("temperature",day2.getString("temperature"));
//            day2Map.put("weather",day2.getString("weather"));
//            day2Map.put("wind",day2.getString("wind"));
//            day2Map.put("fa",weatherid2.getString("fa"));
//            day2Map.put("fb",weatherid2.getString("fb"));
//            futureList.add(day2Map);
//
//            //未来第3天天气
//            JSONObject day3 = future.getJSONObject("day_"+next3);
//            System.out.println(day3.getString("date"));//日期
//            System.out.println(day3.getString("week"));//星期
//            System.out.println(day3.getString("temperature"));//温度
//            System.out.println(day3.getString("weather"));//天气
//            JSONObject weatherid3 = day3.getJSONObject("weather_id");
//            String fa3 = weatherid3.getString("fa");//标识
//            String fb3 = weatherid3.getString("fb");//如果fa不等于fb，说明是组合天气
//            System.out.println(fa3);
//            System.out.println(fb3);
//            System.out.println(day3.getString("wind"));//风
//
//            Map day3Map = new HashMap();
//            day3Map.put("date",day3.getString("date"));
//            day3Map.put("week",day3.getString("week"));
//            day3Map.put("temperature",day3.getString("temperature"));
//            day3Map.put("weather",day3.getString("weather"));
//            day3Map.put("wind",day3.getString("wind"));
//            day3Map.put("fa",weatherid3.getString("fa"));
//            day3Map.put("fb",weatherid3.getString("fb"));
//            futureList.add(day3Map);
//
//            //未来第4天天气
//            JSONObject day4 = future.getJSONObject("day_"+next4);
//            System.out.println(day4.getString("date"));//日期
//            System.out.println(day4.getString("week"));//星期
//            System.out.println(day4.getString("temperature"));//温度
//            System.out.println(day4.getString("weather"));//天气
//            JSONObject weatherid4 = day4.getJSONObject("weather_id");
//            String fa4 = weatherid4.getString("fa");//标识
//            String fb4 = weatherid4.getString("fb");//如果fa不等于fb，说明是组合天气
//            System.out.println(fa4);
//            System.out.println(fb4);
//            System.out.println(day4.getString("wind"));//风
//
//            Map day4Map = new HashMap();
//            day4Map.put("date",day4.getString("date"));
//            day4Map.put("week",day4.getString("week"));
//            day4Map.put("temperature",day4.getString("temperature"));
//            day4Map.put("weather",day4.getString("weather"));
//            day4Map.put("wind",day4.getString("wind"));
//            day4Map.put("fa",weatherid4.getString("fa"));
//            day4Map.put("fb",weatherid4.getString("fb"));
//            futureList.add(day4Map);
//
//            //未来第5天天气
//            JSONObject day5 = future.getJSONObject("day_"+next5);
//            System.out.println(day5.getString("date"));//日期
//            System.out.println(day5.getString("week"));//星期
//            System.out.println(day5.getString("temperature"));//温度
//            System.out.println(day5.getString("weather"));//天气
//            JSONObject weatherid5 = day5.getJSONObject("weather_id");
//            String fa5 = weatherid5.getString("fa");//标识
//            String fb5 = weatherid5.getString("fb");//如果fa不等于fb，说明是组合天气
//            System.out.println(fa5);
//            System.out.println(fb5);
//            System.out.println(day5.getString("wind"));//风
//
//            Map day5Map = new HashMap();
//            day5Map.put("date",day5.getString("date"));
//            day5Map.put("week",day5.getString("week"));
//            day5Map.put("temperature",day5.getString("temperature"));
//            day5Map.put("weather",day5.getString("weather"));
//            day5Map.put("wind",day5.getString("wind"));
//            day5Map.put("fa",weatherid5.getString("fa"));
//            day5Map.put("fb",weatherid5.getString("fb"));
//            futureList.add(day5Map);
//
//            //未来第6天天气
//            JSONObject day6 = future.getJSONObject("day_"+next6);
//            System.out.println(day6.getString("date"));//日期
//            System.out.println(day6.getString("week"));//星期
//            System.out.println(day6.getString("temperature"));//温度
//            System.out.println(day6.getString("weather"));//天气
//            JSONObject weatherid6 = day6.getJSONObject("weather_id");
//            String fa6 = weatherid6.getString("fa");//标识
//            String fb6 = weatherid6.getString("fb");//如果fa不等于fb，说明是组合天气
//            System.out.println(fa6);
//            System.out.println(fb6);
//            System.out.println(day6.getString("wind"));//风
//
//            Map day6Map = new HashMap();
//            day6Map.put("date",day6.getString("date"));
//            day6Map.put("week",day6.getString("week"));
//            day6Map.put("temperature",day6.getString("temperature"));
//            day6Map.put("weather",day6.getString("weather"));
//            day6Map.put("wind",day6.getString("wind"));
//            day6Map.put("fa",weatherid6.getString("fa"));
//            day6Map.put("fb",weatherid6.getString("fb"));
//            futureList.add(day6Map);
//
//            result.put("futureList",futureList);
//
//            return result;
//
//        }else{
//            System.out.println("error_code:"+code+",reason:"+object.getString("reason"));
//            return null;
//        }
//    }
//    /**
//     *
//     * @param urlAll:请求接口
//     * @param charset:字符编码
//     * @return 返回json结果
//     */
//    private String getResult(String urlAll,String charset) throws Exception {
//        String result = "{\n" +
//                "    \"resultcode\": \"200\",\n" +
//                "    \"reason\": \"查询成功!\",\n" +
//                "    \"result\": {\n" +
//                "        \"sk\": {\t\n" +
//                "            \"temp\": \"21\",\n" +
//                "            \"wind_direction\": \"西风\",\n" +
//                "            \"wind_strength\": \"2级\",\n" +
//                "            \"humidity\": \"4%\",\n" +
//                "            \"time\": \"14:25\"\n" +
//                "        },\n" +
//                "        \"today\": {\n" +
//                "            \"city\": \"天津\",\n" +
//                "            \"date_y\": \"2014年03月21日\",\n" +
//                "            \"week\": \"星期五\",\n" +
//                "            \"temperature\": \"8℃~20℃\",\n" +
//                "            \"weather\": \"晴转霾\",\n" +
//                "            \"weather_id\": {\n" +
//                "                \"fa\": \"00\",\n" +
//                "                \"fb\": \"53\"\n" +
//                "            },\n" +
//                "            \"wind\": \"西南风微风\",\n" +
//                "            \"dressing_index\": \"较冷\",\n" +
//                "            \"dressing_advice\": \"建议着大衣、呢外套加毛衣、卫衣等服装。\",\n" +
//                "            \"uv_index\": \"中等\",\n" +
//                "            \"comfort_index\": \"\",\n" +
//                "            \"wash_index\": \"较适宜\",\n" +
//                "            \"travel_index\": \"适宜\",\n" +
//                "            \"exercise_index\": \"较适宜\",\n" +
//                "            \"drying_index\": \"\"\n" +
//                "        },\n" +
//                "        \"future\": {\n" +
//                "            \"day_20140321\": {\n" +
//                "                \"temperature\": \"8℃~20℃\",\n" +
//                "                \"weather\": \"晴转霾\",\n" +
//                "                \"weather_id\": {\n" +
//                "                    \"fa\": \"00\",\n" +
//                "                    \"fb\": \"53\"\n" +
//                "                },\n" +
//                "                \"wind\": \"西南风微风\",\n" +
//                "                \"week\": \"星期五\",\n" +
//                "                \"date\": \"20140321\"\n" +
//                "            },\n" +
//                "            \"day_20140322\": {\n" +
//                "                \"temperature\": \"9℃~21℃\",\n" +
//                "                \"weather\": \"霾转多云\",\n" +
//                "                \"weather_id\": {\n" +
//                "                    \"fa\": \"53\",\n" +
//                "                    \"fb\": \"01\"\n" +
//                "                },\n" +
//                "                \"wind\": \"东北风微风转东南风微风\",\n" +
//                "                \"week\": \"星期六\",\n" +
//                "                \"date\": \"20140322\"\n" +
//                "            },\n" +
//                "            \"day_20140323\": {\n" +
//                "                \"temperature\": \"9℃~19℃\",\n" +
//                "                \"weather\": \"阴\",\n" +
//                "                \"weather_id\": {\n" +
//                "                    \"fa\": \"02\",\n" +
//                "                    \"fb\": \"02\"\n" +
//                "                },\n" +
//                "                \"wind\": \"南风微风\",\n" +
//                "                \"week\": \"星期日\",\n" +
//                "                \"date\": \"20140323\"\n" +
//                "            },\n" +
//                "            \"day_20140324\": {\n" +
//                "                \"temperature\": \"8℃~19℃\",\n" +
//                "                \"weather\": \"晴转多云\",\n" +
//                "                \"weather_id\": {\n" +
//                "                    \"fa\": \"00\",\n" +
//                "                    \"fb\": \"01\"\n" +
//                "                },\n" +
//                "                \"wind\": \"西南风微风转南风微风\",\n" +
//                "                \"week\": \"星期一\",\n" +
//                "                \"date\": \"20140324\"\n" +
//                "            },\n" +
//                "            \"day_20140325\": {\n" +
//                "                \"temperature\": \"9℃~20℃\",\n" +
//                "                \"weather\": \"多云\",\n" +
//                "                \"weather_id\": {\n" +
//                "                    \"fa\": \"01\",\n" +
//                "                    \"fb\": \"01\"\n" +
//                "                },\n" +
//                "                \"wind\": \"南风微风\",\n" +
//                "                \"week\": \"星期二\",\n" +
//                "                \"date\": \"20140325\"\n" +
//                "            },\n" +
//                "            \"day_20140326\": {\n" +
//                "                \"temperature\": \"10℃~19℃\",\n" +
//                "                \"weather\": \"多云\",\n" +
//                "                \"weather_id\": {\n" +
//                "                    \"fa\": \"01\",\n" +
//                "                    \"fb\": \"01\"\n" +
//                "                },\n" +
//                "                \"wind\": \"南风微风\",\n" +
//                "                \"week\": \"星期三\",\n" +
//                "                \"date\": \"20140326\"\n" +
//                "            },\n" +
//                "            \"day_20140327\": {\n" +
//                "                \"temperature\": \"11℃~20℃\",\n" +
//                "                \"weather\": \"阴转多云\",\n" +
//                "                \"weather_id\": {\n" +
//                "                    \"fa\": \"02\",\n" +
//                "                    \"fb\": \"01\"\n" +
//                "                },\n" +
//                "                \"wind\": \"南风微风转无持续风向微风\",\n" +
//                "                \"week\": \"星期四\",\n" +
//                "                \"date\": \"20140327\"\n" +
//                "            }\n" +
//                "        }\n" +
//                "    },\n" +
//                "    \"error_code\": 0\n" +
//                "}";
////        BufferedReader reader = null;
////        String result = null;
////        StringBuffer sbf = new StringBuffer();
////        String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";//模拟浏览器
////        try {
////            URL url = new URL(urlAll);
////            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
////            connection.setRequestMethod("GET");
////            connection.setReadTimeout(30000);
////            connection.setConnectTimeout(30000);
////            connection.setRequestProperty("User-agent",userAgent);
////            connection.connect();
////            InputStream is = connection.getInputStream();
////            reader = new BufferedReader(new InputStreamReader(
////                    is, charset));
////            String strRead = null;
////            while ((strRead = reader.readLine()) != null) {
////                sbf.append(strRead);
////                sbf.append("\r\n");
////            }
////            reader.close();
////            result = sbf.toString();
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        return result;
//    }
}
