package me.guanjun.service;

import me.guanjun.persistent.model.Feedback;

import java.util.List;

/**
 * Created by guanj on 2014/4/28.
 */
public interface FeedbackService {
    List getFeedbacks(String username, String writer, String isreply) throws RuntimeException, Exception;
    Feedback getFeedback(String fid) throws RuntimeException,Exception;
    boolean removeFeedback(String fid) throws RuntimeException,Exception;
    boolean addFeedback(Feedback feedback) throws RuntimeException,Exception;
    boolean modifyFeedback(Feedback feedback) throws RuntimeException,Exception;

    List getFeedbacks(String username, String writer, String isreply, int offset, int limit) throws RuntimeException, Exception;
    int getCountFeedbacks(String username, String writer, String isreply) throws RuntimeException, Exception;
}
