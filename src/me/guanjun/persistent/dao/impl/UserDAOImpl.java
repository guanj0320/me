package me.guanjun.persistent.dao.impl;

import me.guanjun.persistent.dao.UserDAO;
import me.guanjun.persistent.model.User;
import pf.persistent.dao.BaseDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/4/24.
 */
public class UserDAOImpl extends BaseDAO implements UserDAO {
    @Override
    public List queryObjs() throws Exception {
        return getSqlMapClientTemplate().queryForList("User.queryObjs");
    }

    @Override
    public List queryObjs(int offset, int limit) throws Exception {
        return getSqlMapClientTemplate().queryForList("User.queryObjs",offset,limit);
    }

    @Override
    public User queryObj(String username) throws Exception {
        Map map = new HashMap();
        map.put("username", username);
        return (User)getSqlMapClientTemplate().queryForObject("User.queryObj", map);
    }

    @Override
    public int queryCountObjs() throws Exception {
        return ((Integer)getSqlMapClientTemplate().queryForObject("User.queryCountObjs")).intValue();
    }

    @Override
    public int queryCountObj(String username) throws Exception {
        Map map = new HashMap();
        map.put("username", username);
        return ((Integer)getSqlMapClientTemplate().queryForObject("User.queryCountObj", map)).intValue();
    }

    @Override
    public void execInsert(User user) throws Exception {
        getSqlMapClientTemplate().insert("User.execInsert", user);
    }

    @Override
    public void execUpdate(User user) throws Exception {
        getSqlMapClientTemplate().update("User.execUpdate", user);
    }

    @Override
    public void execDelete(String username) throws Exception {
        getSqlMapClientTemplate().delete("User.execDelete", username);
    }
}
