package me.guanjun.service;

import me.guanjun.persistent.model.Album;

import java.util.List;

/**
 * Created by guanj0320 on 14-5-11.
 */
public interface AlbumService {
    List getAlbums(String username, String albumname) throws RuntimeException, Exception;
    Album getAlbum(String aid) throws RuntimeException,Exception;
    boolean removeAlbum(String aid) throws RuntimeException,Exception;
    boolean addAlbum(Album album) throws RuntimeException,Exception;
    boolean modifyAlbum(Album album) throws RuntimeException,Exception;

    List getAlbums(String username, String albumname, int offset, int limit) throws RuntimeException, Exception;
    int getCountAlbums(String username, String albumname) throws RuntimeException, Exception;
}
