package me.guanjun.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import me.guanjun.persistent.model.Feedback;
import me.guanjun.persistent.model.User;
import me.guanjun.service.FeedbackService;
import me.guanjun.web.BaseController;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pf.ee.mail.MailService;
import pf.tools.*;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj0320 on 14-5-6.
 */
@Controller
public class FeedbackController extends BaseController {

    @Resource
    private FeedbackService feedbackService;

    @Resource
    private MailService mailService;

    @RequestMapping(value="/admin/feedback/list", method = RequestMethod.GET)
    public String list(Map model) {
        try {
            this.list("","0","1","10",model);
//            model.put("writer","");
//            model.put("isreply","0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/feedback/list";
    }

    @RequestMapping(value="/admin/feedback/list", method = RequestMethod.POST)
    public ModelAndView list(String writer, String isreply, String currentPage, String pageCount, Map model) {
        ModelAndView modelAndView = new ModelAndView("/admin/feedback/list");
        try{
            User user = (User)getSession().getAttribute("user");
            //查询条件
            if(null == writer) writer = "";
            String _writer = "%" + writer + "%";
            String username = user.getUsername();

            //组织分页的内容
            int currentPageInt = 1;
            int pageCountInt = 10;
            if (currentPage != null && !"".equals(currentPage)) {
                try {
                    currentPageInt = Integer.parseInt(currentPage);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            if (pageCount != null && !"".equals(pageCount)) {
                try {
                    pageCountInt = Integer.parseInt(pageCount);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            int offset = (currentPageInt-1) * pageCountInt;
            int limit = pageCountInt;

            //获取所有记录数用于分页
            int record = feedbackService.getCountFeedbacks(username,_writer,isreply);

            //得到分页后的结果集
            List<Feedback> lst = feedbackService.getFeedbacks(username,_writer,isreply,offset,limit);

            //得到分页的对象
            Pageable pg = null;
            try {
                pg = new IbatisPage(lst, record, currentPageInt, pageCountInt);
            } catch (PageException e) {
                pg = null;
            }

            //把分页结果放进request
            getRequest().setAttribute("feedbacks", pg.getResult());
            //把分页对象放进request
            getRequest().setAttribute("pages", pg);
            //把查询条件放回页面
            model.put("currentPage",String.valueOf(currentPageInt));
            model.put("pageCount",String.valueOf(pageCountInt));
            model.put("writer",writer);
            model.put("isreply",isreply);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value="/feedback/add", method = RequestMethod.POST)
    public void add(String writer, String email, String content, String validatecode, PrintWriter printWriter) {
        try {
            Map map = new HashMap();
            String v = (String)this.getSession().getAttribute("code");
            if ( v != null) {
                if(v.equals(validatecode)) {
                    Feedback feedback = new Feedback();
                    feedback.setFid(GenerateUUID.getInstance().getID());
                    feedback.setUsername("sa");
                    feedback.setWriter(writer);
                    feedback.setIp(this.getIpAddr());
                    feedback.setContent(content);
                    feedback.setCreatetime(DateUtil.getSystemTimestamp());
                    feedback.setEmail(email);

                    if (feedbackService.addFeedback(feedback)) {
                        map.put("result", "1");
                    } else {
                        map.put("result", "0");
                    }
                } else {
                    map.put("result","-1");
                }
            } else {
                map.put("result", "-1");
            }
            String json = JSON.toJSONString(map, SerializerFeature.PrettyFormat);
            printWriter.write(json);
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value="/admin/feedback/modify", method = RequestMethod.GET)
    public String modify(@RequestParam String id, Map model) {
        try {
            Feedback feedback = feedbackService.getFeedback(id);
            feedback.setReplytime(DateUtil.getSystemTimestamp());

            model.put("feedback", feedback);
            model.put("opt","modify");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/feedback/edit";
    }

    @RequestMapping(value = "/admin/feedback/modify", method = RequestMethod.POST)
    public String modify(Feedback feedback, Map model) {
        try {
            User user = (User) getSession().getAttribute("user");
            feedback.setUsername(user.getUsername());
            feedback.setReplytime(DateUtil.getSystemTimestamp());
            feedback.setIsreply(1);

            feedbackService.modifyFeedback(feedback);

            Map replyMap = new HashMap();
            replyMap.put("writer",feedback.getWriter());
            replyMap.put("createtime",DateUtil.parseDate(feedback.getCreatetime(),"yyyy-MM-dd"));
            replyMap.put("replycontent",feedback.getReplycontent());
            replyMap.put("username",user.getUsername());
            replyMap.put("nickname",user.getNickname());
            replyMap.put("email",user.getEmail());

            //发回复的邮件
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(feedback.getEmail());
            msg.setFrom(user.getEmail());
            msg.setSubject("Thanks for your message");
            mailService.send(msg, "feedbackMailTemplate.ftl", replyMap);

            model.put("writer","");
            model.put("isreply","0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/feedback/list";
    }

    @RequestMapping(value = "/admin/feedback/giveup", method = RequestMethod.POST)
    public String giveup(Feedback feedback, Map model) {
        try {
            User user = (User) getSession().getAttribute("user");
            feedback.setUsername(user.getUsername());
            feedback.setReplytime(DateUtil.getSystemTimestamp());
            feedback.setIsreply(1);

            feedbackService.modifyFeedback(feedback);

            model.put("writer","");
            model.put("isreply","0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/feedback/list";
    }

    @RequestMapping(value="/admin/feedback/remove", method = RequestMethod.POST)
    public ModelAndView remove(String id, String writer, String isreply, String currentPage, String pageCount, Map model) {
        try {
            feedbackService.removeFeedback(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list(writer,isreply,currentPage,pageCount,model);
    }

    @RequestMapping(value="/admin/feedback/getcount", method = RequestMethod.GET)
    public void getcount(PrintWriter printWriter) {
        try {
            User user = (User) getSession().getAttribute("user");
            int count = feedbackService.getCountFeedbacks(user.getUsername(), "%%", "0");
            Map map = new HashMap();
            map.put("count", count);
            String json = JSON.toJSONString(map, SerializerFeature.PrettyFormat);
            printWriter.write(json);
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
