package me.guanjun.web.controller;

import me.guanjun.persistent.model.Column;
import me.guanjun.service.ColumnService;
import me.guanjun.web.BaseController;
import org.springframework.stereotype.Controller;
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
public class ColumnController extends BaseController {

    @Resource
    private ColumnService columnService;

    @RequestMapping(value="/admin/column/list", method = RequestMethod.GET)
    public String list() {
        return "/admin/column/list";
    }

    @RequestMapping(value="/admin/column/list", method = RequestMethod.POST)
    public ModelAndView list(String colname, String currentPage, String pageCount, Map model) {
        ModelAndView modelAndView = new ModelAndView("/admin/column/list");
        try{
            //查询条件
            if(null == colname) colname = "";
            String _colname = "%" + colname + "%";

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
            int record = columnService.getCountColumns(_colname);

            //得到分页后的结果集
            List<Column> lst = columnService.getColumns(_colname, offset, limit);

            //得到分页的对象
            Pageable pg = null;
            try {
                pg = new IbatisPage(lst, record, currentPageInt, pageCountInt);
            } catch (PageException e) {
                pg = null;
            }

            //把分页结果放进request
            getRequest().setAttribute("columns", pg.getResult());
            //把分页对象放进request
            getRequest().setAttribute("pages", pg);
            //把查询条件放回页面
            model.put("currentPage",String.valueOf(currentPageInt));
            model.put("pageCount",String.valueOf(pageCountInt));
            model.put("colname",colname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/column/add", method = RequestMethod.GET)
    public String add(Map model) {
        Column column = new Column();
        column.setColid(GenerateUUID.getInstance().getID());
        column.setColname("");
        column.setDescription("");
        column.setOrd(0);
        column.setSeo("");
        column.setPcolid("");
        model.put("column", column);
        model.put("opt","add");
        return "/admin/column/edit";
    }

    @RequestMapping(value = "/admin/column/add", method = RequestMethod.POST)
    public String add(Column column) {
        try {
            columnService.addColumn(column);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/column/list";
    }

    @RequestMapping(value="/admin/column/modify", method = RequestMethod.GET)
    public String modify(@RequestParam String id, Map model) {
        try {
            Column column = columnService.getColumn(id);
            model.put("column", column);
            model.put("opt","modify");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/column/edit";
    }

    @RequestMapping(value = "/admin/column/modify", method = RequestMethod.POST)
    public String modify(Column column) {
        try {
            columnService.modifyColumn(column);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/column/list";
    }

    @RequestMapping(value="/admin/column/remove", method = RequestMethod.POST)
    public ModelAndView remove(String id, String colname, String currentPage, String pageCount, Map model) {
        try {
            columnService.removeColumn(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list(colname,currentPage,pageCount,model);
    }
}
