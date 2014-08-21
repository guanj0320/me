package me.guanjun.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import me.guanjun.persistent.model.Article;
import me.guanjun.persistent.model.Photo;
import me.guanjun.service.AlbumService;
import me.guanjun.service.PhotoService;
import me.guanjun.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pf.tools.IbatisPage;
import pf.tools.PageException;
import pf.tools.Pageable;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/5/12.
 */
@Controller
public class PicWallController extends BaseController {
    @Resource
    private PhotoService photoService;

    @Resource
    private AlbumService albumService;

    @RequestMapping(value="/json/pic", method = RequestMethod.GET)
    public void pic(String aid, String page, PrintWriter printWriter) {
        try {
            //组织分页的内容
            int pageInt = 1;
            if (page != null && !"".equals(page)) {
                try {
                    pageInt = Integer.parseInt(page);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            int offset = (pageInt-1) * 20;
            int limit = 20; //一次显示20张图

            List photos = photoService.getPhotos("","",aid,offset,limit);
            String json = JSON.toJSONString(photos, SerializerFeature.PrettyFormat);
            printWriter.write(json);
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value="/wall", method = RequestMethod.GET)
    public String wall(String aid, Map model) {
        try {
            List albums = albumService.getAlbums("sa","");
            model.put("albums",albums);
            model.put("aid",aid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/photo/list";
    }
    @RequestMapping(value="/wall/show", method = RequestMethod.GET)
    public String show(String id, Map model) {
        try {
            Photo photo = photoService.getPhoto(id);
            model.put("title", photo.getTitle());
            model.put("src", photo.getSrc());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/photo/show";

    }
}
