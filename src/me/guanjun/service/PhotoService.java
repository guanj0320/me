package me.guanjun.service;

import me.guanjun.persistent.model.Photo;

import java.util.List;

/**
 * Created by guanj0320 on 14-5-11.
 */
public interface PhotoService {
    List getPhotos(String username, String title, String aid) throws RuntimeException, Exception;
    Photo getPhoto(String pid) throws RuntimeException,Exception;
    boolean removePhoto(String pid) throws RuntimeException,Exception;
    boolean addPhoto(Photo photo) throws RuntimeException,Exception;
    boolean modifyPhoto(Photo photo) throws RuntimeException,Exception;

    List getPhotos(String username, String title, String aid, int offset, int limit) throws RuntimeException, Exception;
    int getCountPhotos(String username, String title, String aid) throws RuntimeException, Exception;
}
