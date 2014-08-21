package me.guanjun.web.controller;

import me.guanjun.persistent.model.User;
import me.guanjun.service.UserService;
import me.guanjun.web.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pf.tools.DateUtil;
import pf.tools.MD5;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guanj on 2014/4/16.
 */
@Controller
public class LoginController extends BaseController {
    private Log log = LogFactory.getLog(LoginController.class);

    @Resource
    private UserService userService;

    @RequestMapping(value="/admin/logon", method = RequestMethod.POST)
    public ModelAndView logon(String userid,String pwd,String validatecode,Map model){
        ModelAndView modelAndView = new ModelAndView("/admin/login");
        try {
            String v = (String)this.getSession().getAttribute("adminCode");
            if ( v != null) {
                if(v.equals(validatecode)) {
                    User user = userService.getUser(userid);
                    if(null != user) {
                        pwd = new MD5().getMD5ofStr(pwd);
                        if(userid.equals(user.getUsername()) && pwd.equals(user.getPwd())) {
                            user.setLogintime(DateUtil.getSystemTimestamp());
                            user.setLoginip(getRequest().getRemoteAddr());
                            userService.modifyUser(user);
                            getSession().setAttribute("user",user);
                            modelAndView = new ModelAndView("redirect:/admin/home");
                            log.info("user:"+user.getUsername()+";ip:"+user.getLoginip()+" is login.");
                        } else {
                            model.put("msg","您输入的密码不正确，请重新输入！");
                        }
                    } else {
                        model.put("msg","您输入的用户在系统中不存在！");
                    }
                } else {
                    model.put("msg","验证码输入不正确，请重新输入！");
                }
            } else {
                model.put("msg","未输入验证码，请重新输入！");
            }

        } catch (Exception e) {
            model.put("msg",e.getMessage());
            log.error(e.getMessage());
        }
        return modelAndView;
    }


    @RequestMapping(value="/admin/login", method = RequestMethod.GET)
    public String login() {
        return "/admin/login";
    }

    @RequestMapping(value="/admin/logout", method = RequestMethod.GET)
    public String logout() {
        User user = (User)getSession().getAttribute("user");
        getSession().removeAttribute("user");
        log.info("user:"+user.getUsername()+";ip:"+user.getLoginip()+" is logout.");
        return "redirect:/admin/login";
    }

    @RequestMapping(value="/admin/getUser", method = RequestMethod.GET)
    public void getUser(String userid, String pwd, PrintWriter printWriter) {
        Map user = new HashMap();
        user.put("userid",userid);
        user.put("pwd",pwd);
        String json =  JSON.toJSONString(user, SerializerFeature.PrettyFormat);
        printWriter.write(json);
        printWriter.flush();
        printWriter.close();
    }

    @RequestMapping(value="/ex/session", method = RequestMethod.GET)
    public String sessionLost() {
        return "/ex/session";
    }
}
