package me.guanjun.web.controller;

import me.guanjun.web.BaseController;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.http.HttpServletResponse;

import me.guanjun.web.model.FileMeta;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pf.constant.SystemConfig;
import pf.tools.FileUtil;
import pf.tools.GenerateUUID;
import pf.tools.ImageUtil;

/**
 * Created by guanj on 2014/5/4.
 */
@Controller
@RequestMapping("/controller")
public class FileController extends BaseController {
    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;

    /***************************************************
     * URL: /controller/upload
     * upload(): receives files
     * @param request : MultipartHttpServletRequest auto passed
     * @param response : HttpServletResponse auto passed
     * @return LinkedList<FileMeta> as json format
     ****************************************************/
    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public @ResponseBody LinkedList<FileMeta> upload(MultipartHttpServletRequest request, HttpServletResponse response) {

        //1. build an iterator
        Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf = null;

        //2. get each file
        while(itr.hasNext()){

            //2.1 get next MultipartFile
            mpf = request.getFile(itr.next());
            System.out.println(mpf.getOriginalFilename() +" uploaded! "+files.size());

            //2.2 if files > 10 remove the first from the list
            if(files.size() >= 10)
                files.pop();

            //2.3 create new fileMeta
            fileMeta = new FileMeta();
            fileMeta.setFileName(mpf.getOriginalFilename());
            fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
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

                String outDirPath = SystemConfig.getScaleDrawingPath();
                File savefile = new File(new File(outDirPath), fileMeta.getFileName());
                if (!savefile.getParentFile().exists()) {
                    savefile.getParentFile().mkdirs();
                }

                ImageUtil imageUtil = new ImageUtil(mpf.getInputStream(),savefile.getAbsolutePath());
                imageUtil.resizeByWidth(400);

                fileMeta.setFileUrl(getContextPath()+"/scale_drawing/"+fileMeta.getFileName());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //2.4 add to files
            files.add(fileMeta);
        }
        // result will be like this
        // [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
        return files;
    }

    @RequestMapping(value="/uploadOne", method = RequestMethod.POST)
    public @ResponseBody FileMeta uploadOne(MultipartHttpServletRequest request, HttpServletResponse response) {

        //1. build an iterator
        Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf = null;

        //2. get each file
        while(itr.hasNext()){
            //2.1 get next MultipartFile
            mpf = request.getFile(itr.next());
            System.out.println(mpf.getOriginalFilename() +" uploaded! "+files.size());

            //2.3 create new fileMeta
            fileMeta = new FileMeta();
            fileMeta.setFileName(mpf.getOriginalFilename());
            fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
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

                String outDirPath = SystemConfig.getScaleDrawingPath();
                File savefile = new File(new File(outDirPath), fileMeta.getFileName());
                if (!savefile.getParentFile().exists()) {
                    savefile.getParentFile().mkdirs();
                }

                ImageUtil imageUtil = new ImageUtil(mpf.getInputStream(),savefile.getAbsolutePath());
                imageUtil.resizeByWidth(400);

                fileMeta.setFileUrl(getContextPath()+"/scale_drawing/"+fileMeta.getFileName());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // result will be like this
        // [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
        return fileMeta;
    }

    /***************************************************
     * URL: /controller/get/{value}
     * get(): get file as an attachment
     * @param response : passed by the server
     * @param value : value from the URL
     * @return void
     ****************************************************/
    @RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
    public void get(HttpServletResponse response,@PathVariable String value){
        FileMeta getFile = files.get(Integer.parseInt(value));
        try {
            response.setContentType(getFile.getFileType());
            response.setHeader("Content-disposition", "attachment; filename=\""+getFile.getFileName()+"\"");
            FileCopyUtils.copy(getFile.getBytes(), response.getOutputStream());
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/del/{value}", method = RequestMethod.GET)
    public void del(HttpServletResponse response,@PathVariable String value){
        FileMeta getFile = files.get(Integer.parseInt(value));
        try {
            String outDirPath = SystemConfig.getScaleDrawingPath();
            String fileName = outDirPath + getFile.getFileName();
            File file = new File(fileName);
            if(file.exists()) file.delete();
        }catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
