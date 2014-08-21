package me.guanjun.persistent.dao;

import me.guanjun.persistent.model.Comment;

import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public interface CommentDAO {
    public List queryObjs(Map condition) throws Exception;
    public List queryObjs(Map condition,int offset, int limit) throws Exception;
    public Comment queryObj(String cid) throws Exception;
    public int queryCountObjs(Map condition) throws Exception;
    public int queryCountObj(String cid) throws Exception;
    public void execInsert(Comment comment) throws Exception;
    public void execUpdate(Comment comment) throws Exception;
    public void execDelete(String cid) throws Exception;

    public void execDeleteForArticle(String arcid) throws Exception;
}
