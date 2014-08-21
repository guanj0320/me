package me.guanjun.persistent.dao;

import me.guanjun.persistent.model.Album;

import java.util.List;
import java.util.Map;

/**
 * Created by guanj0320 on 14-5-11.
 */
public interface AlbumDAO {
    public List queryObjs(Map condition) throws Exception;
    public List queryObjs(Map condition, int offset, int limit) throws Exception;
    public Album queryObj(String aid) throws Exception;
    public int queryCountObjs(Map condition) throws Exception;
    public int queryCountObj(String aid) throws Exception;
    public void execInsert(Album album) throws Exception;
    public void execUpdate(Album album) throws Exception;
    public void execDelete(String aid) throws Exception;
}
