package me.guanjun.web.controller;

import me.guanjun.persistent.model.Banner;
import me.guanjun.service.BannerService;
import me.guanjun.web.BaseController;
import me.guanjun.web.model.FileMeta;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import pf.constant.SystemConfig;
import pf.tools.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj0320 on 14-5-7.
 */
@Controller
public class BannerController extends BaseController {

    @Resource
    private BannerService bannerService;

    @RequestMapping(value="/admin/banner/list", method = RequestMethod.GET)
    public String list() {
        return "/admin/banner/list";
    }

    @RequestMapping(value="/admin/banner/list", method = RequestMethod.POST)
    public ModelAndView list(String title, String currentPage, String pageCount, Map model) {
        ModelAndView modelAndView = new ModelAndView("/admin/banner/list");
        try {
            //查询条件
            if(null == title) title = "";
            String _title = "%" + title + "%";

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
            int record = bannerService.getCountBanners(_title);

            //得到分页后的结果集
            List<Banner> lst = bannerService.getBanners(_title,offset,limit);

            //得到分页的对象
            Pageable pg = null;
            try {
                pg = new IbatisPage(lst, record, currentPageInt, pageCountInt);
            } catch (PageException e) {
                pg = null;
            }

            //把分页结果放进request
            getRequest().setAttribute("banners", pg.getResult());
            //把分页对象放进request
            getRequest().setAttribute("pages", pg);
            //把查询条件放回页面
            model.put("currentPage",String.valueOf(currentPageInt));
            model.put("pageCount",String.valueOf(pageCountInt));
            model.put("title",title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/banner/add", method = RequestMethod.GET)
    public String add(Map model) {
        Banner banner = new Banner();
        banner.setBid(GenerateUUID.getInstance().getID());
        banner.setTitle("");
        banner.setContent("");
        banner.setPic("");
        banner.setOrd(1);
        banner.setCreatetime(DateUtil.getSystemTimestamp());
        banner.setUpdatetime(DateUtil.getSystemTimestamp());

        model.put("banner", banner);
        model.put("opt","add");
        return "/admin/banner/edit";
    }

    @RequestMapping(value = "/admin/banner/add", method = RequestMethod.POST)
    public String add(Banner banner, MultipartHttpServletRequest request) {
        try {
            //1. build an iterator
            Iterator<String> itr =  request.getFileNames();
            MultipartFile mpf = null;
            LinkedList<FileMeta> files = new LinkedList<FileMeta>();
            FileMeta fileMeta = null;

            //2. get each file
            while(itr.hasNext()) {
                //2.1 get next MultipartFile
                mpf = request.getFile(itr.next());
                System.out.println(mpf.getOriginalFilename() + " uploaded! " + files.size());

                //2.3 create new fileMeta
                fileMeta = new FileMeta();
                fileMeta.setFileName(mpf.getOriginalFilename());
                fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
                fileMeta.setFileType(mpf.getContentType());

                try {
                    fileMeta.setBytes(mpf.getBytes());

                    // copy file to local disk (make sure the path "e.g. D:/temp/files" exists)
//                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream("D:/temp/files/" + mpf.getOriginalFilename()));

                    if ((fileMeta.getFileType().equals("image/pjpeg") || fileMeta.getFileType().equals("image/jpeg"))
                            && fileMeta.getFileName().substring(fileMeta.getFileName().length() - 4).toLowerCase().equals(".jpg")) {
                        // IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg
                        fileMeta.setFileName(GenerateUUID.getInstance().getUUID() + ".jpg");
                    } else if (fileMeta.getFileType().equals("image/png") || fileMeta.getFileType().equals("image/x-png")) {
                        fileMeta.setFileName(GenerateUUID.getInstance().getUUID() + ".png");
                    } else if (fileMeta.getFileType().equals("image/gif")) {
                        fileMeta.setFileName(GenerateUUID.getInstance().getUUID() + ".gif");
                    } else if (fileMeta.getFileType().equals("image/bmp")) {
                        fileMeta.setFileName(GenerateUUID.getInstance().getUUID() + ".bmp");
                    }

                    String outDirPath = SystemConfig.getBannerPath();
                    File savefile = new File(new File(outDirPath), fileMeta.getFileName());
                    if (!savefile.getParentFile().exists()) {
                        savefile.getParentFile().mkdirs();
                    }

                    ImageUtil imageUtil = new ImageUtil(mpf.getInputStream(), savefile.getAbsolutePath());
//                    imageUtil.cutImage(1800,500);
//                    imageUtil.resize(1800,500);
                    imageUtil.resizeByWidth(1280);

                    fileMeta.setFileUrl(getContextPath() + "/banner/" + fileMeta.getFileName());
                } catch (IOException ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }

            }
            banner.setPic(fileMeta.getFileUrl());

            bannerService.addBanner(banner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/banner/list";
    }

    @RequestMapping(value="/admin/banner/modify", method = RequestMethod.GET)
    public String modify(@RequestParam String id, Map model) {
        try {
            Banner banner = bannerService.getBanner(id);
            banner.setUpdatetime(DateUtil.getSystemTimestamp());
            model.put("banner", banner);
            model.put("opt","modify");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/banner/edit";
    }

    @RequestMapping(value = "/admin/banner/modify", method = RequestMethod.POST)
    public String modify(Banner banner, MultipartHttpServletRequest request) {
        try {

            //删除原来的图片
            String sourceDirPath = SystemConfig.getBannerPath();
            String fileName = banner.getPic();
            fileName = fileName.substring(fileName.lastIndexOf("/")+1);
            fileName = sourceDirPath + fileName;
            File file = new File(fileName);
            if(file.exists()) {
                if(file.delete()) {//如果删除成功才会将新的图片上传
                    //1. build an iterator
                    Iterator<String> itr =  request.getFileNames();
                    MultipartFile mpf = null;
                    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
                    FileMeta fileMeta = null;

                    //2. get each file
                    while(itr.hasNext()) {
                        //2.1 get next MultipartFile
                        mpf = request.getFile(itr.next());
                        System.out.println(mpf.getOriginalFilename() + " uploaded! " + files.size());

                        //2.3 create new fileMeta
                        fileMeta = new FileMeta();
                        fileMeta.setFileName(mpf.getOriginalFilename());
                        fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
                        fileMeta.setFileType(mpf.getContentType());

                        try {
                            fileMeta.setBytes(mpf.getBytes());

                            // copy file to local disk (make sure the path "e.g. D:/temp/files" exists)
//                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream("D:/temp/files/" + mpf.getOriginalFilename()));

                            if ((fileMeta.getFileType().equals("image/pjpeg") || fileMeta.getFileType().equals("image/jpeg"))
                                    && fileMeta.getFileName().substring(fileMeta.getFileName().length() - 4).toLowerCase().equals(".jpg")) {
                                // IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg
                                fileMeta.setFileName(GenerateUUID.getInstance().getUUID() + ".jpg");
                            } else if (fileMeta.getFileType().equals("image/png") || fileMeta.getFileType().equals("image/x-png")) {
                                fileMeta.setFileName(GenerateUUID.getInstance().getUUID() + ".png");
                            } else if (fileMeta.getFileType().equals("image/gif")) {
                                fileMeta.setFileName(GenerateUUID.getInstance().getUUID() + ".gif");
                            } else if (fileMeta.getFileType().equals("image/bmp")) {
                                fileMeta.setFileName(GenerateUUID.getInstance().getUUID() + ".bmp");
                            }

                            String outDirPath = SystemConfig.getBannerPath();
                            File savefile = new File(new File(outDirPath), fileMeta.getFileName());
                            if (!savefile.getParentFile().exists()) {
                                savefile.getParentFile().mkdirs();
                            }

                            ImageUtil imageUtil = new ImageUtil(mpf.getInputStream(), savefile.getAbsolutePath());
//                            imageUtil.cutImage(1800,500);
//                            imageUtil.resize(1800,500);
                            imageUtil.resizeByWidth(1280);

                            fileMeta.setFileUrl(getContextPath() + "/banner/" + fileMeta.getFileName());
                        } catch (IOException ex) {
                            // TODO Auto-generated catch block
                            ex.printStackTrace();
                        }

                    }
                    //将新的图片的地址存入数据库
                    banner.setPic(fileMeta.getFileUrl());
                }
            }

            bannerService.modifyBanner(banner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/banner/list";
    }

    @RequestMapping(value="/admin/banner/remove", method = RequestMethod.POST)
    public ModelAndView remove(String id, String title, String currentPage, String pageCount, Map model) {
        try {
            String outDirPath = SystemConfig.getBannerPath();
            String fileName = bannerService.getBanner(id).getPic();
            fileName = fileName.substring(fileName.lastIndexOf("/")+1);
            fileName = outDirPath + fileName;

            bannerService.removeBanner(id);

            File file = new File(fileName);
            if(file.exists()) file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list(title,currentPage,pageCount,model);
    }
}
