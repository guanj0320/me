package me.guanjun.persistent.dao;

import me.guanjun.persistent.model.Column;

import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public interface ColumnDAO {
    public List queryObjs(Map condition) throws Exception;
    public List queryObjs(Map condition, int offset, int limit) throws Exception;
    public Column queryObj(String colid) throws Exception;
    public int queryCountObjs(Map condition) throws Exception;
    public int queryCountObj(String colid) throws Exception;
    public void execInsert(Column column) throws Exception;
    public void execUpdate(Column column) throws Exception;
    public void execDelete(String colid) throws Exception;
}
