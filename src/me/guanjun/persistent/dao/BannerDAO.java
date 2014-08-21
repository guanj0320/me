package me.guanjun.persistent.dao;

import me.guanjun.persistent.model.Banner;

import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public interface BannerDAO {
    public List queryObjs(Map condition) throws Exception;
    public List queryObjs(Map condition, int offset, int limit) throws Exception;
    public Banner queryObj(String bid) throws Exception;
    public int queryCountObjs(Map condition) throws Exception;
    public int queryCountObj(String bid) throws Exception;
    public void execInsert(Banner banner) throws Exception;
    public void execUpdate(Banner banner) throws Exception;
    public void execDelete(String bid) throws Exception;
}
