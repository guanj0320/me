package me.guanjun.persistent.dao.impl;

import me.guanjun.persistent.dao.CommentDAO;
import me.guanjun.persistent.model.Comment;
import pf.persistent.dao.BaseDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public class CommentDAOImpl extends BaseDAO implements CommentDAO {
    @Override
    public List queryObjs(Map condition) throws Exception {
        return getSqlMapClientTemplate().queryForList("Comment.queryObjs",condition);
    }

    @Override
    public List queryObjs(Map condition, int offset, int limit) throws Exception {
        return getSqlMapClientTemplate().queryForList("Comment.queryObjs",condition,offset,limit);
    }

    @Override
    public Comment queryObj(String cid) throws Exception {
        Map map = new HashMap();
        map.put("cid", cid);
        return (Comment)getSqlMapClientTemplate().queryForObject("Comment.queryObj", map);
    }

    @Override
    public int queryCountObjs(Map condition) throws Exception {
        return ((Integer)getSqlMapClientTemplate().queryForObject("Comment.queryCountObjs",condition)).intValue();
    }

    @Override
    public int queryCountObj(String cid) throws Exception {
        Map map = new HashMap();
        map.put("cid", cid);
        return ((Integer)getSqlMapClientTemplate().queryForObject("Comment.queryCountObj", map)).intValue();
    }

    @Override
    public void execInsert(Comment comment) throws Exception {
        getSqlMapClientTemplate().insert("Comment.execInsert", comment);
    }

    @Override
    public void execUpdate(Comment comment) throws Exception {
        getSqlMapClientTemplate().update("Comment.execUpdate", comment);
    }

    @Override
    public void execDelete(String cid) throws Exception {
        getSqlMapClientTemplate().delete("Comment.execDelete", cid);
    }

    @Override
    public void execDeleteForArticle(String arcid) throws Exception {
        getSqlMapClientTemplate().delete("Comment.execDeleteForArticle", arcid);
    }
}
