package me.guanjun.web.controller;

import me.guanjun.persistent.model.Tag;
import me.guanjun.service.TagService;
import me.guanjun.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pf.tools.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/4/29.
 */
@Controller
public class TagController extends BaseController {

    @Resource
    private TagService tagService;

    @RequestMapping(value="/admin/tag/list", method = RequestMethod.GET)
    public String list() {
        return "/admin/tag/list";
    }
    @RequestMapping(value="/admin/tag/list", method = RequestMethod.POST)
    public ModelAndView list(String tagname, String currentPage, String pageCount, Map model) {
        ModelAndView modelAndView = new ModelAndView("/admin/tag/list");
        try {
            //查询条件
            if(null == tagname) tagname = "";
            String _tagname = "%" + tagname + "%";

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
            int record = tagService.getCountTags(_tagname);

            //得到分页后的结果集
            List<Tag> lst = tagService.getTags(_tagname,offset,limit);

            //得到分页的对象
            Pageable pg = null;
            try {
                pg = new IbatisPage(lst, record, currentPageInt, pageCountInt);
            } catch (PageException e) {
                pg = null;
            }

            //把分页结果放进request
            getRequest().setAttribute("tags", pg.getResult());
            //把分页对象放进request
            getRequest().setAttribute("pages", pg);
            //把查询条件放回页面
            model.put("currentPage",String.valueOf(currentPageInt));
            model.put("pageCount",String.valueOf(pageCountInt));
            model.put("tagname",tagname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/tag/add", method = RequestMethod.GET)
    public String add(Map model) {
        Tag tag = new Tag();
        tag.setTid(GenerateUUID.getInstance().getID());
        tag.setTagname("");
        tag.setNum(0);
        tag.setCreatetime(DateUtil.getSystemTimestamp());
        model.put("tag", tag);
        model.put("opt","add");
        return "/admin/tag/edit";
    }

    @RequestMapping(value = "/admin/tag/add", method = RequestMethod.POST)
    public String add(Tag tag) {
        try {
            tagService.addTag(tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/tag/list";
    }

    @RequestMapping(value="/admin/tag/modify", method = RequestMethod.GET)
    public String modify(@RequestParam String id, Map model) {
        try {
            Tag tag = tagService.getTag(id);
            model.put("tag", tag);
            model.put("opt","modify");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/tag/edit";
    }

    @RequestMapping(value = "/admin/tag/modify", method = RequestMethod.POST)
    public String modify(Tag tag) {
        try {
            tagService.modifyTag(tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/tag/list";
    }

    @RequestMapping(value="/admin/tag/remove", method = RequestMethod.POST)
    public ModelAndView remove(String id, String tagname, String currentPage, String pageCount, Map model) {
        try {
            tagService.removeTag(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list(tagname,currentPage,pageCount,model);
    }
}
