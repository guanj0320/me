package me.guanjun.service;

import me.guanjun.persistent.model.Article;

import java.util.List;

/**
 * Created by guanj on 2014/4/28.
 */
public interface ArticleService {
    List getArticles(String title, String colid, String property, String username) throws RuntimeException, Exception;
    Article getArticle(String arcid) throws RuntimeException,Exception;
    boolean removeArticle(String arcid) throws RuntimeException,Exception;
    boolean addArticle(Article article) throws RuntimeException,Exception;
    boolean modifyArticle(Article article) throws RuntimeException,Exception;

    List getArticles(String title, String colid, String property, String username, String tid, int offset, int limit) throws RuntimeException, Exception;
    int getCountArticles(String title, String colid, String property, String username, String tid) throws RuntimeException, Exception;

    List getArticlesForHome(String username, int offset, int limit) throws RuntimeException, Exception;
}
