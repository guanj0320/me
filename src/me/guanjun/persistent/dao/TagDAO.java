package me.guanjun.persistent.dao;

import me.guanjun.persistent.model.Tag;

import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public interface TagDAO {
    public List queryObjs(Map condition) throws Exception;
    public List queryObjs(Map condition, int offset, int limit) throws Exception;
    public Tag queryObj(String tid) throws Exception;
    public int queryCountObjs(Map condition) throws Exception;
    public int queryCountObj(String tid) throws Exception;
    public void execInsert(Tag tag) throws Exception;
    public void execUpdate(Tag tag) throws Exception;
    public void execDelete(String tid) throws Exception;
}
