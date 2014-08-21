package me.guanjun.service.impl;

import me.guanjun.persistent.dao.CommentDAO;
import me.guanjun.persistent.model.Comment;
import me.guanjun.service.CommentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public class CommentServiceImpl implements CommentService {
    private CommentDAO commentDAO;

    public void setCommentDAO(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    @Override
    public List getComments(String cid,String arcid,String writer) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("cid",cid);
        condition.put("arcid",arcid);
        condition.put("writer",writer);
        return commentDAO.queryObjs(condition);
    }

    @Override
    public Comment getComment(String cid) throws RuntimeException, Exception {
        return commentDAO.queryObj(cid);
    }

    @Override
    public boolean removeComment(String cid) throws RuntimeException, Exception {
        commentDAO.execDelete(cid);
        return true;
    }

    @Override
    public boolean addComment(Comment comment) throws RuntimeException, Exception {
        commentDAO.execInsert(comment);
        return true;
    }

    @Override
    public boolean modifyComment(Comment comment) throws RuntimeException, Exception {
        commentDAO.execUpdate(comment);
        return true;
    }

    @Override
    public List getComments(String cid, String arcid, String writer, int offset, int limit) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("cid",cid);
        condition.put("arcid",arcid);
        condition.put("writer",writer);
        return commentDAO.queryObjs(condition,offset,limit);
    }

    @Override
    public int getCountComments(String cid, String arcid,String writer) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("cid",cid);
        condition.put("arcid",arcid);
        condition.put("writer",writer);
        return commentDAO.queryCountObjs(condition);
    }
}
