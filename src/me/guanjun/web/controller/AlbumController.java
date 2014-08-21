package me.guanjun.web.controller;

import me.guanjun.persistent.model.Album;
import me.guanjun.persistent.model.Banner;
import me.guanjun.persistent.model.User;
import me.guanjun.service.AlbumService;
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
 * Created by guanj0320 on 14-5-11.
 */
@Controller
public class AlbumController extends BaseController {
    @Resource
    private AlbumService albumService;

    @RequestMapping(value="/admin/album/list", method = RequestMethod.GET)
    public String list() {
        return "/admin/album/list";
    }

    @RequestMapping(value="/admin/album/list", method = RequestMethod.POST)
    public ModelAndView list(String albumname, String currentPage, String pageCount, Map model) {
        ModelAndView modelAndView = new ModelAndView("/admin/album/list");
        try {
            User user = (User)getSession().getAttribute("user");
            String username = user.getUsername();
            //查询条件
            if(null == albumname) albumname = "";
            String _albumname = "%" + albumname + "%";

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
            int record = albumService.getCountAlbums(username,_albumname);

            //得到分页后的结果集
            List<Album> lst = albumService.getAlbums(username,_albumname,offset,limit);

            //得到分页的对象
            Pageable pg = null;
            try {
                pg = new IbatisPage(lst, record, currentPageInt, pageCountInt);
            } catch (PageException e) {
                pg = null;
            }

            //把分页结果放进request
            getRequest().setAttribute("albums", pg.getResult());
            //把分页对象放进request
            getRequest().setAttribute("pages", pg);
            //把查询条件放回页面
            model.put("currentPage",String.valueOf(currentPageInt));
            model.put("pageCount",String.valueOf(pageCountInt));
            model.put("albumname",albumname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/album/add", method = RequestMethod.GET)
    public String add(Map model) {
        User user = (User)getSession().getAttribute("user");
        Album album = new Album();
        album.setAid(GenerateUUID.getInstance().getID());
        album.setUsername(user.getUsername());
        album.setAlbumname("");
        album.setPic("");
        album.setCreatetime(DateUtil.getSystemTimestamp());

        model.put("album", album);
        model.put("opt","add");
        return "/admin/album/edit";
    }

    @RequestMapping(value = "/admin/album/add", method = RequestMethod.POST)
    public String add(Album album, MultipartHttpServletRequest request) {
        try {
            User user = (User)getSession().getAttribute("user");
            album.setUsername(user.getUsername());

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

                    String outDirPath = SystemConfig.getAlbumsPath();
                    File savefile = new File(new File(outDirPath), fileMeta.getFileName());
                    if (!savefile.getParentFile().exists()) {
                        savefile.getParentFile().mkdirs();
                    }

                    ImageUtil imageUtil = new ImageUtil(mpf.getInputStream(), savefile.getAbsolutePath());
//                    imageUtil.cutImage(1800,500);
//                    imageUtil.resize(1800,500);
                    imageUtil.resizeByWidth(200);

                    fileMeta.setFileUrl(getContextPath() + "/albums/" + fileMeta.getFileName());
                } catch (IOException ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }

            }
            album.setPic(fileMeta.getFileUrl());

            albumService.addAlbum(album);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/album/list";
    }

    @RequestMapping(value="/admin/album/modify", method = RequestMethod.GET)
    public String modify(@RequestParam String id, Map model) {
        try {
            Album album = albumService.getAlbum(id);

            model.put("album", album);
            model.put("opt","modify");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/album/edit";
    }

    @RequestMapping(value = "/admin/album/modify", method = RequestMethod.POST)
    public String modify(Album album, MultipartHttpServletRequest request) {
        try {

            //删除原来的图片
            String sourceDirPath = SystemConfig.getBannerPath();
            String fileName = album.getPic();
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
                            imageUtil.resizeByWidth(200);

                            fileMeta.setFileUrl(getContextPath() + "/album/" + fileMeta.getFileName());
                        } catch (IOException ex) {
                            // TODO Auto-generated catch block
                            ex.printStackTrace();
                        }

                    }
                    //将新的图片的地址存入数据库
                    album.setPic(fileMeta.getFileUrl());
                }
            }

            albumService.modifyAlbum(album);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/album/list";
    }

    @RequestMapping(value="/admin/album/remove", method = RequestMethod.POST)
    public ModelAndView remove(String id, String albumname, String currentPage, String pageCount, Map model) {
        try {
            String outDirPath = SystemConfig.getBannerPath();
            String fileName = albumService.getAlbum(id).getPic();
            fileName = fileName.substring(fileName.lastIndexOf("/")+1);
            fileName = outDirPath + fileName;

            albumService.removeAlbum(id);

            File file = new File(fileName);
            if(file.exists()) file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list(albumname,currentPage,pageCount,model);
    }
}
