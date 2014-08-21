package me.guanjun.service.impl;

import me.guanjun.persistent.dao.AlbumDAO;
import me.guanjun.persistent.model.Album;
import me.guanjun.service.AlbumService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj0320 on 14-5-11.
 */
public class AlbumServiceImpl implements AlbumService {
    private AlbumDAO albumDAO;

    public void setAlbumDAO(AlbumDAO albumDAO) {
        this.albumDAO = albumDAO;
    }

    @Override
    public List getAlbums(String username, String albumname) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("username",username);
        condition.put("albumname",albumname);
        return albumDAO.queryObjs(condition);
    }

    @Override
    public Album getAlbum(String aid) throws RuntimeException, Exception {
        return albumDAO.queryObj(aid);
    }

    @Override
    public boolean removeAlbum(String aid) throws RuntimeException, Exception {
        albumDAO.execDelete(aid);
        return true;
    }

    @Override
    public boolean addAlbum(Album album) throws RuntimeException, Exception {
        albumDAO.execInsert(album);
        return true;
    }

    @Override
    public boolean modifyAlbum(Album album) throws RuntimeException, Exception {
        albumDAO.execUpdate(album);
        return true;
    }

    @Override
    public List getAlbums(String username, String albumname, int offset, int limit) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("username",username);
        condition.put("albumname",albumname);
        return albumDAO.queryObjs(condition,offset,limit);
    }

    @Override
    public int getCountAlbums(String username, String albumname) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("username",username);
        condition.put("albumname",albumname);
        return albumDAO.queryCountObjs(condition);
    }
}
