package me.guanjun.service.impl;

import me.guanjun.persistent.dao.ArticleDAO;
import me.guanjun.persistent.dao.CommentDAO;
import me.guanjun.persistent.model.Article;
import me.guanjun.service.ArticleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public class ArticleServiceImpl implements ArticleService {
    private ArticleDAO articleDAO;
    private CommentDAO commentDAO;

    public void setArticleDAO(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    public void setCommentDAO(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    @Override
    public List getArticles(String title, String colid, String property, String username) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("title",title);
        condition.put("colid",colid);
        condition.put("property",property);
        condition.put("username",username);
        return articleDAO.queryObjs(condition);
    }

    @Override
    public Article getArticle(String arcid) throws RuntimeException, Exception {
        return articleDAO.queryObj(arcid);
    }

    @Override
    public boolean removeArticle(String arcid) throws RuntimeException, Exception {
        articleDAO.execDelete(arcid);
        commentDAO.execDeleteForArticle(arcid);
        return true;
    }

    @Override
    public boolean addArticle(Article article) throws RuntimeException, Exception {
        articleDAO.execInsert(article);
        return true;
    }

    @Override
    public boolean modifyArticle(Article article) throws RuntimeException, Exception {
        articleDAO.execUpdate(article);
        return true;
    }

    @Override
    public List getArticles(String title, String colid, String property, String username, String tid, int offset, int limit) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("title",title);
        condition.put("colid",colid);
        condition.put("property",property);
        condition.put("username",username);
        condition.put("tid",tid);
        return articleDAO.queryObjs(condition, offset, limit);
    }

    @Override
    public int getCountArticles(String title, String colid, String property, String username, String tid) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("title",title);
        condition.put("colid",colid);
        condition.put("property",property);
        condition.put("username",username);
        condition.put("tid",tid);
        return articleDAO.queryCountObjs(condition);
    }

    @Override
    public List getArticlesForHome(String username, int offset, int limit) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("username",username);
        return articleDAO.queryArticles(condition, offset, limit);
    }
}
