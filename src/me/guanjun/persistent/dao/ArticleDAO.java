package me.guanjun.persistent.dao;

import me.guanjun.persistent.model.Article;

import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public interface ArticleDAO {
    public List queryObjs(Map condition) throws Exception;
    public List queryObjs(Map condition, int offset, int limit) throws Exception;
    public Article queryObj(String arcid) throws Exception;
    public int queryCountObjs(Map condition) throws Exception;
    public int queryCountObj(String arcid) throws Exception;
    public void execInsert(Article article) throws Exception;
    public void execUpdate(Article article) throws Exception;
    public void execDelete(String arcid) throws Exception;

    public List queryArticles(Map condition, int offset, int limit) throws Exception;
}
