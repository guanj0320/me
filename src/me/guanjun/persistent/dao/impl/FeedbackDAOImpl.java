package me.guanjun.persistent.dao.impl;

import me.guanjun.persistent.dao.FeedbackDAO;
import me.guanjun.persistent.model.Feedback;
import pf.persistent.dao.BaseDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public class FeedbackDAOImpl extends BaseDAO implements FeedbackDAO {
    @Override
    public List queryObjs(Map condition) throws Exception {
        return getSqlMapClientTemplate().queryForList("Feedback.queryObjs",condition);
    }

    @Override
    public List queryObjs(Map condition,int offset, int limit) throws Exception {
        return getSqlMapClientTemplate().queryForList("Feedback.queryObjs",condition,offset,limit);
    }

    @Override
    public Feedback queryObj(String fid) throws Exception {
        Map map = new HashMap();
        map.put("fid", fid);
        return (Feedback)getSqlMapClientTemplate().queryForObject("Feedback.queryObj", map);
    }

    @Override
    public int queryCountObjs(Map condition) throws Exception {
        return ((Integer)getSqlMapClientTemplate().queryForObject("Feedback.queryCountObjs",condition)).intValue();
    }

    @Override
    public int queryCountObj(String fid) throws Exception {
        Map map = new HashMap();
        map.put("fid", fid);
        return ((Integer)getSqlMapClientTemplate().queryForObject("Feedback.queryCountObj", map)).intValue();
    }

    @Override
    public void execInsert(Feedback feedback) throws Exception {
        getSqlMapClientTemplate().insert("Feedback.execInsert", feedback);
    }

    @Override
    public void execUpdate(Feedback feedback) throws Exception {
        getSqlMapClientTemplate().update("Feedback.execUpdate", feedback);
    }

    @Override
    public void execDelete(String fid) throws Exception {
        getSqlMapClientTemplate().delete("Feedback.execDelete", fid);
    }
}
