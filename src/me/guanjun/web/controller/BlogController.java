package me.guanjun.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import me.guanjun.persistent.model.Article;
import me.guanjun.persistent.model.Comment;
import me.guanjun.persistent.model.Feedback;
import me.guanjun.service.ArticleService;
import me.guanjun.service.CommentService;
import me.guanjun.service.TagService;
import me.guanjun.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pf.tools.*;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/5/10.
 */
@Controller
public class BlogController extends BaseController {
    @Resource
    private ArticleService articleService;
    @Resource
    private CommentService commentService;
    @Resource
    private TagService tagService;

    @RequestMapping(value="/blog", method = {RequestMethod.POST,RequestMethod.GET})
    public ModelAndView list(String tid, String tagname, String currentPage, String pageCount, Map model) {
        ModelAndView modelAndView = new ModelAndView("/blog/list");
        try{

            //查询条件
            String username = "sa";
            String _tagname = "";
            if(tagname!=null) _tagname="%"+tagname+"%";

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
            int record = articleService.getCountArticles("","","",username,_tagname);

            //得到分页后的结果集
            List<Article> lst = articleService.getArticles("","","",username,_tagname,offset,limit);

            //得到分页的对象
            Pageable pg = null;
            try {
                pg = new IbatisPage(lst, record, currentPageInt, pageCountInt);
            } catch (PageException e) {
                pg = null;
            }

            //把分页结果放进request
            getRequest().setAttribute("articles", pg.getResult());
            //把分页对象放进request
            getRequest().setAttribute("pages", pg);
            //把查询条件放回页面
            model.put("currentPage",String.valueOf(currentPageInt));
            model.put("pageCount",String.valueOf(pageCountInt));
            List tags = tagService.getTags("");
            model.put("tags",tags);
            model.put("tid",tid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value="/blog/show", method = RequestMethod.GET)
    public String modify(@RequestParam String id, Map model) {
        try {
            Article article = articleService.getArticle(id);

            List comments = commentService.getComments("",id,"");
            model.put("article", article);
            model.put("comments",comments);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/blog/edit";
    }

    @RequestMapping(value="/blog/comment", method = RequestMethod.POST)
    public void comment(String writer, String email, String content, String arcid, PrintWriter printWriter) {
        try {
            Map map = new HashMap();

            Comment comment = new Comment();
            comment.setCid(GenerateUUID.getInstance().getID());
            comment.setArcid(arcid);
            comment.setWriter(writer);
            comment.setIp(this.getIpAddr());
            comment.setContent(content);
            comment.setEmail(email);
            comment.setCreatetime(DateUtil.getSystemTimestamp());
            comment.setPcid("");

            if (commentService.addComment(comment)) {
                map.put("result", "1");
                map.put("comment",comment);
            } else {
                map.put("result", "0");
            }

            String json = JSON.toJSONString(map, SerializerFeature.PrettyFormat);
            printWriter.write(json);
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
