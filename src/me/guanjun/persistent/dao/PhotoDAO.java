package me.guanjun.persistent.dao;

import me.guanjun.persistent.model.Photo;

import java.util.List;
import java.util.Map;

/**
 * Created by guanj0320 on 14-5-11.
 */
public interface PhotoDAO {
    public List queryObjs(Map condition) throws Exception;
    public List queryObjs(Map condition, int offset, int limit) throws Exception;
    public Photo queryObj(String pid) throws Exception;
    public int queryCountObjs(Map condition) throws Exception;
    public int queryCountObj(String pid) throws Exception;
    public void execInsert(Photo photo) throws Exception;
    public void execUpdate(Photo photo) throws Exception;
    public void execDelete(String pid) throws Exception;
}
