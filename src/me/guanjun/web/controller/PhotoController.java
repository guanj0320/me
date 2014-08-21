package me.guanjun.web.controller;

import me.guanjun.persistent.model.Photo;
import me.guanjun.persistent.model.User;
import me.guanjun.service.AlbumService;
import me.guanjun.service.PhotoService;
import me.guanjun.web.BaseController;
import me.guanjun.web.model.FileMeta;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj0320 on 14-5-11.
 */
@Controller
public class PhotoController extends BaseController {

    @Resource
    private PhotoService photoService;

    @Resource
    private AlbumService albumService;

    @RequestMapping(value="/admin/photo/list", method = RequestMethod.GET)
    public String list(Map model) {
        try {
            User user = (User)getSession().getAttribute("user");
            String username = user.getUsername();
            List albums = albumService.getAlbums(username ,"");

            model.put("albums", albums);
            model.put("title", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/photo/list";
    }

    @RequestMapping(value="/admin/photo/list", method = RequestMethod.POST)
    public ModelAndView list(String aid, String title, String currentPage, String pageCount, Map model) {
        ModelAndView modelAndView = new ModelAndView("/admin/photo/list");
        try {
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
            int record = photoService.getCountPhotos(username,_title,aid);

            //得到分页后的结果集
            List<Photo> lst = photoService.getPhotos(username,_title,aid,offset,limit);

            //得到分页的对象
            Pageable pg = null;
            try {
                pg = new IbatisPage(lst, record, currentPageInt, pageCountInt);
            } catch (PageException e) {
                pg = null;
            }

            //把分页结果放进request
            getRequest().setAttribute("photos", pg.getResult());
            //把分页对象放进request
            getRequest().setAttribute("pages", pg);
            //把查询条件放回页面
            model.put("currentPage",String.valueOf(currentPageInt));
            model.put("pageCount",String.valueOf(pageCountInt));
            model.put("title",title);
            model.put("aid",aid);
            List albums = albumService.getAlbums(username ,"");
            model.put("albums", albums);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/photo/add", method = RequestMethod.GET)
    public String add(Map model) {
        try {
            User user = (User) getSession().getAttribute("user");
            Photo photo = new Photo();
            photo.setPid(GenerateUUID.getInstance().getID());
            photo.setAid("");
            photo.setUsername(user.getUsername());
            photo.setTitle("");
            photo.setSrc("");
            photo.setPre("");

            photo.setCreatetime(DateUtil.getSystemTimestamp());

            model.put("photo", photo);
            model.put("opt", "add");
            List albums = albumService.getAlbums(user.getUsername() ,"");
            model.put("albums", albums);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/photo/edit";
    }

    @RequestMapping(value = "/admin/photo/add", method = RequestMethod.POST)
    public String add(Photo photo, MultipartHttpServletRequest request) {
        try {
            User user = (User)getSession().getAttribute("user");
            photo.setUsername(user.getUsername());

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

                    String fileName = GenerateUUID.getInstance().getUUID();
                    if ((fileMeta.getFileType().equals("image/pjpeg") || fileMeta.getFileType().equals("image/jpeg"))
                            && fileMeta.getFileName().substring(fileMeta.getFileName().length() - 4).toLowerCase().equals(".jpg")) {
                        // IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg
                        fileMeta.setFileName(fileName + ".jpg");
                        fileMeta.setSfileName("s_"+fileName+".jpg");
                    } else if (fileMeta.getFileType().equals("image/png") || fileMeta.getFileType().equals("image/x-png")) {
                        fileMeta.setFileName(fileName + ".png");
                        fileMeta.setSfileName("s_"+fileName+".png");
                    } else if (fileMeta.getFileType().equals("image/gif")) {
                        fileMeta.setFileName(fileName + ".gif");
                        fileMeta.setSfileName("s_"+fileName+".gif");
                    } else if (fileMeta.getFileType().equals("image/bmp")) {
                        fileMeta.setFileName(fileName + ".bmp");
                        fileMeta.setSfileName("s_"+fileName+".bmp");
                    }

                    //存放照片的目录
                    String outDirPath = SystemConfig.getPhotosPath();
                    //照片目录的URL
                    String urlPath = getContextPath() + "/photos/";

                    //是否选择相册
                    if(!"".equals(photo.getAid())) {
                        outDirPath = outDirPath + photo.getAid() + File.separator;
                        urlPath = urlPath + photo.getAid() + "/";
                    }

                    //原图
                    File srcfile = new File(new File(outDirPath), fileMeta.getFileName());
                    if (!srcfile.getParentFile().exists()) {
                        srcfile.getParentFile().mkdirs();
                    }
                    //保存原图
                    FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(srcfile.getAbsolutePath()));
                    fileMeta.setFileUrl(urlPath + fileMeta.getFileName());

                    //缩略图
                    File prefile = new File(new File(outDirPath), fileMeta.getSfileName());
                    if (!prefile.getParentFile().exists()) {
                        prefile.getParentFile().mkdirs();
                    }
                    //生成缩略图
                    ImageUtil imageUtil = new ImageUtil(mpf.getInputStream(), prefile.getAbsolutePath());
                    //保存缩略图
                    imageUtil.resizeByWidth(200);
                    fileMeta.setSfileUrl(urlPath + fileMeta.getSfileName());

                    photo.setWidth(imageUtil.getSrcWidth());
                    photo.setHeight(imageUtil.getSrcHeight());
                    photo.setSrc(fileMeta.getFileUrl());
                    photo.setPre(fileMeta.getSfileUrl());
                } catch (IOException ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }

            }
            photoService.addPhoto(photo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/photo/list";
    }

    @RequestMapping(value="/admin/photo/modify", method = RequestMethod.GET)
    public String modify(@RequestParam String id, Map model) {
        try {
            User user = (User)getSession().getAttribute("user");

            Photo photo = photoService.getPhoto(id);
            model.put("photo", photo);
            model.put("opt","modify");
            List albums = albumService.getAlbums(user.getUsername() ,"");
            model.put("albums", albums);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/photo/edit";
    }

    @RequestMapping(value = "/admin/photo/modify", method = RequestMethod.POST)
    public String modify(Photo photo, MultipartHttpServletRequest request) {
        try {
            User user = (User)getSession().getAttribute("user");
            photo.setUsername(user.getUsername());

            //删除原来的图片
            String outDirPath = SystemConfig.getPhotosPath();
            //得到原图文件地址
            String fileName = photo.getSrc();
            fileName = photo.getAid() + File.separator + fileName.substring(fileName.lastIndexOf("/")+1);
            fileName = outDirPath + fileName;

            //得到缩略图文件地址
            String sfileName = photo.getPre();
            sfileName = photo.getAid() + File.separator + sfileName.substring(sfileName.lastIndexOf("/")+1);
            sfileName = outDirPath + sfileName;

            File file = new File(fileName);
            File sfile = new File(sfileName);
            if(file.exists() && sfile.exists()) {
                if (file.delete() && sfile.delete()) {
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

                            String filename = GenerateUUID.getInstance().getUUID();
                            if ((fileMeta.getFileType().equals("image/pjpeg") || fileMeta.getFileType().equals("image/jpeg"))
                                    && fileMeta.getFileName().substring(fileMeta.getFileName().length() - 4).toLowerCase().equals(".jpg")) {
                                // IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg
                                fileMeta.setFileName(filename + ".jpg");
                                fileMeta.setSfileName("s_"+filename+".jpg");
                            } else if (fileMeta.getFileType().equals("image/png") || fileMeta.getFileType().equals("image/x-png")) {
                                fileMeta.setFileName(filename + ".png");
                                fileMeta.setSfileName("s_"+filename+".png");
                            } else if (fileMeta.getFileType().equals("image/gif")) {
                                fileMeta.setFileName(filename + ".gif");
                                fileMeta.setSfileName("s_"+filename+".gif");
                            } else if (fileMeta.getFileType().equals("image/bmp")) {
                                fileMeta.setFileName(filename + ".bmp");
                                fileMeta.setSfileName("s_"+filename+".bmp");
                            }


                            //照片目录的URL
                            String urlPath = getContextPath() + "/photos/";

                            //是否选择相册
                            if(!"".equals(photo.getAid())) {
                                outDirPath = outDirPath + photo.getAid() + File.separator;
                                urlPath = urlPath + photo.getAid() + "/";
                            }

                            //原图
                            File srcfile = new File(new File(outDirPath), fileMeta.getFileName());
                            if (!srcfile.getParentFile().exists()) {
                                srcfile.getParentFile().mkdirs();
                            }
                            //保存原图
                            FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(srcfile.getAbsolutePath()));
                            fileMeta.setFileUrl(urlPath + fileMeta.getFileName());

                            //缩略图
                            File prefile = new File(new File(outDirPath), fileMeta.getSfileName());
                            if (!prefile.getParentFile().exists()) {
                                prefile.getParentFile().mkdirs();
                            }
                            //生成缩略图
                            ImageUtil imageUtil = new ImageUtil(mpf.getInputStream(), prefile.getAbsolutePath());
                            //保存缩略图
                            imageUtil.resizeByWidth(200);
                            fileMeta.setSfileUrl(urlPath + fileMeta.getSfileName());

                            photo.setWidth(imageUtil.getSrcWidth());
                            photo.setHeight(imageUtil.getSrcHeight());
                            photo.setSrc(fileMeta.getFileUrl());
                            photo.setPre(fileMeta.getSfileUrl());
                        } catch (IOException ex) {
                            // TODO Auto-generated catch block
                            ex.printStackTrace();
                        }

                    }
                }
            }

            photoService.modifyPhoto(photo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/photo/list";
    }

    @RequestMapping(value="/admin/photo/remove", method = RequestMethod.POST)
    public ModelAndView remove(String id, String aid, String title, String currentPage, String pageCount, Map model) {
        try {
            String outDirPath = SystemConfig.getPhotosPath();
            Photo photo = photoService.getPhoto(id);
            //得到原图文件地址
            String fileName = photo.getSrc();
            fileName = photo.getAid() + File.separator + fileName.substring(fileName.lastIndexOf("/")+1);
            fileName = outDirPath + fileName;

            //得到缩略图文件地址
            String sfileName = photo.getPre();
            sfileName = photo.getAid() + File.separator + sfileName.substring(sfileName.lastIndexOf("/")+1);
            sfileName = outDirPath + sfileName;

            photoService.removePhoto(id);

            File file = new File(fileName);
            if(file.exists()) file.delete();
            file = new File(sfileName);
            if(file.exists()) file.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list(aid,title,currentPage,pageCount,model);
    }
}
