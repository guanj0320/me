package me.guanjun.persistent.dao.impl;

import me.guanjun.persistent.dao.TagDAO;
import me.guanjun.persistent.model.Tag;
import pf.persistent.dao.BaseDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public class TagDAOImpl extends BaseDAO implements TagDAO {
    @Override
    public List queryObjs(Map condition) throws Exception {
        return getSqlMapClientTemplate().queryForList("Tag.queryObjs",condition);
    }

    @Override
    public List queryObjs(Map condition, int offset, int limit) throws Exception {
        return getSqlMapClientTemplate().queryForList("Tag.queryObjs",condition,offset,limit);
    }

    @Override
    public Tag queryObj(String tid) throws Exception {
        Map map = new HashMap();
        map.put("tid", tid);
        return (Tag)getSqlMapClientTemplate().queryForObject("Tag.queryObj", map);
    }

    @Override
    public int queryCountObjs(Map condition) throws Exception {
        return ((Integer)getSqlMapClientTemplate().queryForObject("Tag.queryCountObjs", condition)).intValue();
    }

    @Override
    public int queryCountObj(String tid) throws Exception {
        Map map = new HashMap();
        map.put("tid", tid);
        return ((Integer)getSqlMapClientTemplate().queryForObject("Tag.queryCountObj", map)).intValue();
    }

    @Override
    public void execInsert(Tag tag) throws Exception {
        getSqlMapClientTemplate().insert("Tag.execInsert", tag);
    }

    @Override
    public void execUpdate(Tag tag) throws Exception {
        getSqlMapClientTemplate().update("Tag.execUpdate", tag);
    }

    @Override
    public void execDelete(String tid) throws Exception {
        getSqlMapClientTemplate().delete("Tag.execDelete", tid);
    }
}
