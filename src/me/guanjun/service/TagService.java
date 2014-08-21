package me.guanjun.service;

import me.guanjun.persistent.model.Tag;

import java.util.List;

/**
 * Created by guanj on 2014/4/28.
 */
public interface TagService {
    List getTags(String tagname) throws RuntimeException, Exception;
    Tag getTag(String tid) throws RuntimeException,Exception;
    boolean removeTag(String tid) throws RuntimeException,Exception;
    boolean addTag(Tag tag) throws RuntimeException,Exception;
    boolean modifyTag(Tag tag) throws RuntimeException,Exception;

    List getTags(String tagname, int offset, int limit) throws RuntimeException,Exception;
    int getCountTags(String tagname) throws RuntimeException,Exception;
}
