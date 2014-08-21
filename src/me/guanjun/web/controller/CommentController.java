package me.guanjun.web.controller;

import me.guanjun.persistent.model.Comment;
import me.guanjun.persistent.model.User;
import me.guanjun.service.CommentService;
import me.guanjun.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pf.tools.IbatisPage;
import pf.tools.PageException;
import pf.tools.Pageable;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/5/10.
 */
@Controller
public class CommentController extends BaseController {
    @Resource
    private CommentService commentService;

    @RequestMapping(value="/admin/comment/list", method = RequestMethod.GET)
    public String list(String arcid, Map model) {
        try {
            this.list("",arcid,"1","10",model);
//            model.put("writer","");
//            model.put("isreply","0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/comment/list";
    }

    @RequestMapping(value="/admin/comment/list", method = RequestMethod.POST)
    public ModelAndView list(String writer, String arcid, String currentPage, String pageCount, Map model) {
        ModelAndView modelAndView = new ModelAndView("/admin/comment/list");
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
            int record = commentService.getCountComments("",arcid,_writer);

            //得到分页后的结果集
            List<Comment> lst = commentService.getComments("",arcid,_writer,offset,limit);

            //得到分页的对象
            Pageable pg = null;
            try {
                pg = new IbatisPage(lst, record, currentPageInt, pageCountInt);
            } catch (PageException e) {
                pg = null;
            }

            //把分页结果放进request
            getRequest().setAttribute("comments", pg.getResult());
            //把分页对象放进request
            getRequest().setAttribute("pages", pg);
            //把查询条件放回页面
            model.put("currentPage",String.valueOf(currentPageInt));
            model.put("pageCount",String.valueOf(pageCountInt));
            model.put("writer",writer);
            model.put("arcid",arcid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/comment/modify", method = RequestMethod.GET)
    public String modify(@RequestParam String id, Map model) {
        try {
            Comment comment = commentService.getComment(id);

            model.put("comment", comment);
            model.put("opt","modify");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/comment/edit";
    }

    @RequestMapping(value = "/admin/comment/modify", method = RequestMethod.POST)
    public String modify(Comment comment, Map model) {
        try {
            User user = (User) getSession().getAttribute("user");
            commentService.modifyComment(comment);

            model.put("writer","");
            model.put("arcid",comment.getArcid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/comment/list";
    }

    @RequestMapping(value="/admin/comment/remove", method = RequestMethod.POST)
    public ModelAndView remove(String id, String writer, String arcid, String currentPage, String pageCount, Map model) {
        try {
            commentService.removeComment(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list(writer,arcid,currentPage,pageCount,model);
    }
}
