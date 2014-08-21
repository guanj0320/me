package me.guanjun.service;

import me.guanjun.persistent.model.Comment;

import java.util.List;

/**
 * Created by guanj on 2014/4/28.
 */
public interface CommentService {
    List getComments(String cid,String arcid,String writer) throws RuntimeException, Exception;
    Comment getComment(String cid) throws RuntimeException,Exception;
    boolean removeComment(String cid) throws RuntimeException,Exception;
    boolean addComment(Comment comment) throws RuntimeException,Exception;
    boolean modifyComment(Comment comment) throws RuntimeException,Exception;

    List getComments(String cid,String arcid,String writer, int offset, int limit) throws RuntimeException, Exception;
    int getCountComments(String cid,String arcid,String writer) throws RuntimeException, Exception;

}
