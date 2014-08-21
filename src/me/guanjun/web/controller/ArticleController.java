package me.guanjun.web.controller;

import me.guanjun.persistent.model.Article;
import me.guanjun.persistent.model.Column;
import me.guanjun.persistent.model.Tag;
import me.guanjun.persistent.model.User;
import me.guanjun.service.ArticleService;
import me.guanjun.service.ColumnService;
import me.guanjun.service.TagService;
import me.guanjun.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pf.constant.SystemConfig;
import pf.tools.*;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/5/3.
 */
@Controller
public class ArticleController extends BaseController {

    @Resource
    private ArticleService articleService;
    @Resource
    private ColumnService columnService;
    @Resource
    private TagService tagService;

    @RequestMapping(value="/admin/article/list", method = RequestMethod.GET)
    public String list(Map model) {
        try {
            List columns = columnService.getColumns("");
            model.put("columns", columns);
            model.put("title","");
            model.put("property","");
            model.put("colid","");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/article/list";
    }

    @RequestMapping(value="/admin/article/list", method = RequestMethod.POST)
    public ModelAndView list(String title, String colid, String property, String currentPage, String pageCount, Map model) {
        ModelAndView modelAndView = new ModelAndView("/admin/article/list");
        try{
            User user = (User)getSession().getAttribute("user");
            //查询条件
            if(null == title) title = "";
            String _title = "%" + title + "%";
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
            int record = articleService.getCountArticles(_title,colid,property,username,"");

            //得到分页后的结果集
            List<Article> lst = articleService.getArticles(_title,colid,property,username,"",offset,limit);

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
            model.put("title",title);
            model.put("property",property);
            model.put("colid",colid);
            List columns = columnService.getColumns("");
            model.put("columns", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/article/add", method = RequestMethod.GET)
    public String add(Map model) {
        try {
            User user = (User) getSession().getAttribute("user");
            List columns = columnService.getColumns("");

            Article article = new Article();
            article.setArcid(GenerateUUID.getInstance().getID());
            article.setClicl(0);
            article.setColid(columns!=null&&columns.size()>0?((Column) columns.get(0)).getColid():"");
            article.setContent("");
            article.setCreatetime(DateUtil.getSystemTimestamp());
            article.setDescription("");
            article.setOrd(1);
            article.setPic("");
            article.setProperty(2);
            article.setSource("");
            article.setTid("");
            article.setTitle("");
            article.setUpdatetime(DateUtil.getSystemTimestamp());
            article.setUsername(user.getUsername());

            model.put("article", article);
            model.put("opt", "add");
            model.put("columns", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/article/edit";
    }

    @RequestMapping(value = "/admin/article/add", method = RequestMethod.POST)
    public String add(Article article, Map model) {
        try {
            User user = (User) getSession().getAttribute("user");
            article.setUsername(user.getUsername());
            articleService.addArticle(article);

            //处理标签
            if(!"".equals(article.getTid().trim())) {
                List<Tag> list = tagService.getTags("");
                String allTags = "";
                for (Tag tag : list) {
                    allTags += tag.getTagname() + " ";
                }
                String[] tags = article.getTid().split(" ");
                for (String tag : tags) {
                    String str = tag + " ";
                    System.out.println(allTags.indexOf(str));
                    if (allTags.indexOf(str) < 0) {
                        Tag obj = new Tag();
                        obj.setTid(GenerateUUID.getInstance().getID());
                        obj.setTagname(tag);
                        obj.setNum(0);
                        obj.setCreatetime(DateUtil.getSystemTimestamp());
                        tagService.addTag(obj);
                    }
                }
            }

            List columns = columnService.getColumns("");
            model.put("columns", columns);
            model.put("title","");
            model.put("property","");
            model.put("colid","");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/article/list";
    }

    @RequestMapping(value="/admin/article/modify", method = RequestMethod.GET)
    public String modify(@RequestParam String id, Map model) {
        try {
            Article article = articleService.getArticle(id);
            article.setUpdatetime(DateUtil.getSystemTimestamp());

            model.put("article", article);
            model.put("opt","modify");
            List columns = columnService.getColumns("");
            model.put("columns", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/article/edit";
    }

    @RequestMapping(value = "/admin/article/modify", method = RequestMethod.POST)
    public String modify(Article article, Map model) {
        try {
            User user = (User) getSession().getAttribute("user");
            article.setUsername(user.getUsername());
            articleService.modifyArticle(article);

            //处理标签
            if(!"".equals(article.getTid().trim())) {
                List<Tag> list = tagService.getTags("");
                String allTags = "";
                for (Tag tag : list) {
                    allTags += tag.getTagname() + " ";
                }
                String[] tags = article.getTid().split(" ");
                for (String tag : tags) {
                    String str = tag + " ";
                    System.out.println(allTags.indexOf(str));
                    if (allTags.indexOf(str) < 0) {
                        Tag obj = new Tag();
                        obj.setTid(GenerateUUID.getInstance().getID());
                        obj.setTagname(tag);
                        obj.setNum(0);
                        obj.setCreatetime(DateUtil.getSystemTimestamp());
                        tagService.addTag(obj);
                    }
                }
            }

            List columns = columnService.getColumns("");
            model.put("columns", columns);
            model.put("title","");
            model.put("property","");
            model.put("colid","");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/article/list";
    }

    @RequestMapping(value="/admin/article/remove", method = RequestMethod.POST)
    public ModelAndView remove(String id, String title, String colid, String property, String currentPage, String pageCount, Map model) {
        try {
            String outDirPath = SystemConfig.getScaleDrawingPath();
            String fileName = articleService.getArticle(id).getPic();
            fileName = fileName.substring(fileName.lastIndexOf("/")+1);
            fileName = outDirPath + fileName;

            articleService.removeArticle(id);

            File file = new File(fileName);
            if(file.exists()) file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list(title,colid,property,currentPage,pageCount,model);
    }
}
