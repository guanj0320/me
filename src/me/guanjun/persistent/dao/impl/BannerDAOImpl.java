package me.guanjun.persistent.dao.impl;

import me.guanjun.persistent.dao.BannerDAO;
import me.guanjun.persistent.model.Banner;
import pf.persistent.dao.BaseDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public class BannerDAOImpl extends BaseDAO implements BannerDAO {
    @Override
    public List queryObjs(Map condition) throws Exception {
        return getSqlMapClientTemplate().queryForList("Banner.queryObjs",condition);
    }

    @Override
    public List queryObjs(Map condition, int offset, int limit) throws Exception {
        return getSqlMapClientTemplate().queryForList("Banner.queryObjs",condition,offset,limit);
    }

    @Override
    public Banner queryObj(String bid) throws Exception {
        Map map = new HashMap();
        map.put("bid", bid);
        return (Banner)getSqlMapClientTemplate().queryForObject("Banner.queryObj", map);
    }

    @Override
    public int queryCountObjs(Map condition) throws Exception {
        return ((Integer)getSqlMapClientTemplate().queryForObject("Banner.queryCountObjs",condition)).intValue();
    }

    @Override
    public int queryCountObj(String bid) throws Exception {
        Map map = new HashMap();
        map.put("bid", bid);
        return ((Integer)getSqlMapClientTemplate().queryForObject("Banner.queryCountObj", map)).intValue();
    }

    @Override
    public void execInsert(Banner banner) throws Exception {
        getSqlMapClientTemplate().insert("Banner.execInsert", banner);
    }

    @Override
    public void execUpdate(Banner banner) throws Exception {
        getSqlMapClientTemplate().update("Banner.execUpdate", banner);
    }

    @Override
    public void execDelete(String bid) throws Exception {
        getSqlMapClientTemplate().delete("Banner.execDelete", bid);
    }
}
