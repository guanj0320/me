package me.guanjun.persistent.dao;

import me.guanjun.persistent.model.Feedback;

import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public interface FeedbackDAO {
    public List queryObjs(Map condition) throws Exception;
    public List queryObjs(Map condition, int offset, int limit) throws Exception;
    public Feedback queryObj(String fid) throws Exception;
    public int queryCountObjs(Map condition) throws Exception;
    public int queryCountObj(String fid) throws Exception;
    public void execInsert(Feedback feedback) throws Exception;
    public void execUpdate(Feedback feedback) throws Exception;
    public void execDelete(String fid) throws Exception;
}
