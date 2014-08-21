package me.guanjun.persistent.dao.impl;

import me.guanjun.persistent.dao.ColumnDAO;
import me.guanjun.persistent.model.Column;
import pf.persistent.dao.BaseDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public class ColumnDAOImpl extends BaseDAO implements ColumnDAO {
    @Override
    public List queryObjs(Map condition) throws Exception {
        return getSqlMapClientTemplate().queryForList("Column.queryObjs", condition);
    }

    @Override
    public List queryObjs(Map condition, int offset, int limit) throws Exception {
        return getSqlMapClientTemplate().queryForList("Column.queryObjs",condition,offset,limit);
    }

    @Override
    public Column queryObj(String colid) throws Exception {
        Map map = new HashMap();
        map.put("colid", colid);
        return (Column)getSqlMapClientTemplate().queryForObject("Column.queryObj", map);
    }

    @Override
    public int queryCountObjs(Map condition) throws Exception {
        return ((Integer)getSqlMapClientTemplate().queryForObject("Column.queryCountObjs",condition)).intValue();
    }

    @Override
    public int queryCountObj(String colid) throws Exception {
        Map map = new HashMap();
        map.put("colid", colid);
        return ((Integer)getSqlMapClientTemplate().queryForObject("Column.queryCountObj", map)).intValue();
    }

    @Override
    public void execInsert(Column column) throws Exception {
        getSqlMapClientTemplate().insert("Column.execInsert", column);
    }

    @Override
    public void execUpdate(Column column) throws Exception {
        getSqlMapClientTemplate().update("Column.execUpdate", column);
    }

    @Override
    public void execDelete(String colid) throws Exception {
        getSqlMapClientTemplate().delete("Column.execDelete", colid);
    }
}
