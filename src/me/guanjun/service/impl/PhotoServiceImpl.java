package me.guanjun.service.impl;

import me.guanjun.persistent.dao.PhotoDAO;
import me.guanjun.persistent.model.Photo;
import me.guanjun.service.PhotoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj0320 on 14-5-11.
 */
public class PhotoServiceImpl implements PhotoService {
    private PhotoDAO photoDAO;

    public void setPhotoDAO(PhotoDAO photoDAO) {
        this.photoDAO = photoDAO;
    }

    @Override
    public List getPhotos(String username, String title, String aid) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("username",username);
        condition.put("title",title);
        condition.put("aid",aid);
        return photoDAO.queryObjs(condition);
    }

    @Override
    public Photo getPhoto(String pid) throws RuntimeException, Exception {
        return photoDAO.queryObj(pid);
    }

    @Override
    public boolean removePhoto(String pid) throws RuntimeException, Exception {
        photoDAO.execDelete(pid);
        return true;
    }

    @Override
    public boolean addPhoto(Photo photo) throws RuntimeException, Exception {
        photoDAO.execInsert(photo);
        return true;
    }

    @Override
    public boolean modifyPhoto(Photo photo) throws RuntimeException, Exception {
        photoDAO.execUpdate(photo);
        return true;
    }

    @Override
    public List getPhotos(String username, String title, String aid, int offset, int limit) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("username",username);
        condition.put("title",title);
        condition.put("aid",aid);
        return photoDAO.queryObjs(condition,offset,limit);
    }

    @Override
    public int getCountPhotos(String username, String title, String aid) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("username",username);
        condition.put("title",title);
        condition.put("aid",aid);
        return photoDAO.queryCountObjs(condition);
    }
}
