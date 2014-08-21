package me.guanjun.service;

import me.guanjun.persistent.model.Column;

import java.util.List;

/**
 * Created by guanj on 2014/4/28.
 */
public interface ColumnService {
    List getColumns(String colname) throws RuntimeException, Exception;
    Column getColumn(String colid) throws RuntimeException,Exception;
    boolean removeColumn(String colid) throws RuntimeException,Exception;
    boolean addColumn(Column column) throws RuntimeException,Exception;
    boolean modifyColumn(Column column) throws RuntimeException,Exception;

    List getColumns(String colname, int offset, int limit) throws RuntimeException, Exception;
    int getCountColumns(String colname) throws RuntimeException, Exception;
}
