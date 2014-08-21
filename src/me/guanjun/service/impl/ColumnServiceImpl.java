package me.guanjun.service.impl;

import me.guanjun.persistent.dao.ColumnDAO;
import me.guanjun.persistent.model.Column;
import me.guanjun.service.ColumnService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public class ColumnServiceImpl implements ColumnService {
    private ColumnDAO columnDAO;

    public void setColumnDAO(ColumnDAO columnDAO) {
        this.columnDAO = columnDAO;
    }

    @Override
    public List getColumns(String colname) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("colname",colname);
        return columnDAO.queryObjs(condition);
    }

    @Override
    public Column getColumn(String colid) throws RuntimeException, Exception {
        return columnDAO.queryObj(colid);
    }

    @Override
    public boolean removeColumn(String colid) throws RuntimeException, Exception {
        columnDAO.execDelete(colid);
        return true;
    }

    @Override
    public boolean addColumn(Column column) throws RuntimeException, Exception {
        columnDAO.execInsert(column);
        return true;
    }

    @Override
    public boolean modifyColumn(Column column) throws RuntimeException, Exception {
        columnDAO.execUpdate(column);
        return true;
    }

    @Override
    public List getColumns(String colname, int offset, int limit) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("colname",colname);
        return columnDAO.queryObjs(condition, offset, limit);
    }

    @Override
    public int getCountColumns(String colname) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("colname",colname);
        return columnDAO.queryCountObjs(condition);
    }
}
