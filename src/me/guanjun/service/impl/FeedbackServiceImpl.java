package me.guanjun.service.impl;

import me.guanjun.persistent.dao.FeedbackDAO;
import me.guanjun.persistent.model.Feedback;
import me.guanjun.service.FeedbackService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public class FeedbackServiceImpl implements FeedbackService {
    private FeedbackDAO feedbackDAO;

    public void setFeedbackDAO(FeedbackDAO feedbackDAO) {
        this.feedbackDAO = feedbackDAO;
    }

    @Override
    public List getFeedbacks(String username, String writer, String isreply) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("username",username);
        condition.put("writer",writer);
        condition.put("isreply",isreply);
        return feedbackDAO.queryObjs(condition);
    }

    @Override
    public Feedback getFeedback(String fid) throws RuntimeException, Exception {
        return feedbackDAO.queryObj(fid);
    }

    @Override
    public boolean removeFeedback(String fid) throws RuntimeException, Exception {
        feedbackDAO.execDelete(fid);
        return true;
    }

    @Override
    public boolean addFeedback(Feedback feedback) throws RuntimeException, Exception {
        feedbackDAO.execInsert(feedback);
        return true;
    }

    @Override
    public boolean modifyFeedback(Feedback feedback) throws RuntimeException, Exception {
        feedbackDAO.execUpdate(feedback);
        return true;
    }

    @Override
    public List getFeedbacks(String username, String writer, String isreply, int offset, int limit) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("username",username);
        condition.put("writer",writer);
        condition.put("isreply",isreply);
        return feedbackDAO.queryObjs(condition,offset,limit);
    }

    @Override
    public int getCountFeedbacks(String username, String writer, String isreply) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("username",username);
        condition.put("writer",writer);
        condition.put("isreply",isreply);
        return feedbackDAO.queryCountObjs(condition);
    }
}
