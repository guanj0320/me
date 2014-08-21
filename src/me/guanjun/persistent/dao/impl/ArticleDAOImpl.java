package me.guanjun.persistent.dao.impl;

import me.guanjun.persistent.dao.ArticleDAO;
import me.guanjun.persistent.model.Article;
import pf.persistent.dao.BaseDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public class ArticleDAOImpl extends BaseDAO implements ArticleDAO {
    @Override
    public List queryObjs(Map condition) throws Exception {
        return getSqlMapClientTemplate().queryForList("Article.queryObjs", condition);
    }

    @Override
    public List queryObjs(Map condition, int offset, int limit) throws Exception {
        return getSqlMapClientTemplate().queryForList("Article.queryObjs",condition,offset,limit);
    }

    @Override
    public Article queryObj(String arcid) throws Exception {
        Map map = new HashMap();
        map.put("arcid", arcid);
        return (Article)getSqlMapClientTemplate().queryForObject("Article.queryObj", map);
    }

    @Override
    public int queryCountObjs(Map condition) throws Exception {
        return ((Integer)getSqlMapClientTemplate().queryForObject("Article.queryCountObjs",condition)).intValue();
    }

    @Override
    public int queryCountObj(String arcid) throws Exception {
        Map map = new HashMap();
        map.put("arcid", arcid);
        return ((Integer)getSqlMapClientTemplate().queryForObject("Article.queryCountObj", map)).intValue();
    }

    @Override
    public void execInsert(Article article) throws Exception {
        getSqlMapClientTemplate().insert("Article.execInsert", article);
    }

    @Override
    public void execUpdate(Article article) throws Exception {
        getSqlMapClientTemplate().update("Article.execUpdate", article);
    }

    @Override
    public void execDelete(String arcid) throws Exception {
        getSqlMapClientTemplate().delete("Article.execDelete", arcid);
    }

    @Override
    public List queryArticles(Map condition, int offset, int limit) throws Exception {
        return getSqlMapClientTemplate().queryForList("Article.queryArticlesForHome",condition,offset,limit);
    }
}
