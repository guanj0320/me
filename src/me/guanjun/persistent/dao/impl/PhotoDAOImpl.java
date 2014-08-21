package me.guanjun.persistent.dao.impl;

import me.guanjun.persistent.dao.PhotoDAO;
import me.guanjun.persistent.model.Photo;
import pf.persistent.dao.BaseDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj0320 on 14-5-11.
 */
public class PhotoDAOImpl extends BaseDAO implements PhotoDAO {
    @Override
    public List queryObjs(Map condition) throws Exception {
        return getSqlMapClientTemplate().queryForList("Photo.queryObjs",condition);
    }

    @Override
    public List queryObjs(Map condition, int offset, int limit) throws Exception {
        return getSqlMapClientTemplate().queryForList("Photo.queryObjs",condition,offset,limit);
    }

    @Override
    public Photo queryObj(String pid) throws Exception {
        Map map = new HashMap();
        map.put("pid", pid);
        return (Photo)getSqlMapClientTemplate().queryForObject("Photo.queryObj", map);
    }

    @Override
    public int queryCountObjs(Map condition) throws Exception {
        return ((Integer)getSqlMapClientTemplate().queryForObject("Photo.queryCountObjs",condition)).intValue();
    }

    @Override
    public int queryCountObj(String pid) throws Exception {
        Map map = new HashMap();
        map.put("pid", pid);
        return ((Integer)getSqlMapClientTemplate().queryForObject("Photo.queryCountObj", map)).intValue();
    }

    @Override
    public void execInsert(Photo photo) throws Exception {
        getSqlMapClientTemplate().insert("Photo.execInsert", photo);
    }

    @Override
    public void execUpdate(Photo photo) throws Exception {
        getSqlMapClientTemplate().update("Photo.execUpdate", photo);
    }

    @Override
    public void execDelete(String pid) throws Exception {
        getSqlMapClientTemplate().delete("Photo.execDelete", pid);
    }
}
