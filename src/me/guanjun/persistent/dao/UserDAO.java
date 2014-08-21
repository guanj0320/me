package me.guanjun.persistent.dao;

import me.guanjun.persistent.model.User;

import java.util.List;

/**
 * Created by guanj on 2014/4/24.
 */
public interface UserDAO {
    public List queryObjs() throws Exception;
    public List queryObjs(int offset, int limit) throws Exception;
    public User queryObj(String username) throws Exception;
    public int queryCountObjs() throws Exception;
    public int queryCountObj(String username) throws Exception;
    public void execInsert(User user) throws Exception;
    public void execUpdate(User user) throws Exception;
    public void execDelete(String username) throws Exception;
}
