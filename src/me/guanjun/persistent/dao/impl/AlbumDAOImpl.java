package me.guanjun.persistent.dao.impl;

import me.guanjun.persistent.dao.AlbumDAO;
import me.guanjun.persistent.model.Album;
import pf.persistent.dao.BaseDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj0320 on 14-5-11.
 */
public class AlbumDAOImpl extends BaseDAO implements AlbumDAO {
    @Override
    public List queryObjs(Map condition) throws Exception {
        return getSqlMapClientTemplate().queryForList("Album.queryObjs",condition);
    }

    @Override
    public List queryObjs(Map condition, int offset, int limit) throws Exception {
        return getSqlMapClientTemplate().queryForList("Album.queryObjs",condition,offset,limit);
    }

    @Override
    public Album queryObj(String aid) throws Exception {
        Map map = new HashMap();
        map.put("aid", aid);
        return (Album)getSqlMapClientTemplate().queryForObject("Album.queryObj", map);
    }

    @Override
    public int queryCountObjs(Map condition) throws Exception {
        return ((Integer)getSqlMapClientTemplate().queryForObject("Album.queryCountObjs",condition)).intValue();
    }

    @Override
    public int queryCountObj(String aid) throws Exception {
        Map map = new HashMap();
        map.put("aid", aid);
        return ((Integer)getSqlMapClientTemplate().queryForObject("Album.queryCountObj", map)).intValue();
    }

    @Override
    public void execInsert(Album album) throws Exception {
        getSqlMapClientTemplate().insert("Album.execInsert", album);
    }

    @Override
    public void execUpdate(Album album) throws Exception {
        getSqlMapClientTemplate().update("Album.execUpdate", album);
    }

    @Override
    public void execDelete(String aid) throws Exception {
        getSqlMapClientTemplate().delete("Album.execDelete", aid);
    }
}
