package me.guanjun.service.impl;

import me.guanjun.persistent.dao.TagDAO;
import me.guanjun.persistent.model.Tag;
import me.guanjun.service.TagService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public class TagServiceImpl implements TagService {
    private TagDAO tagDAO;

    public void setTagDAO(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    public List getTags(String tagname) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("tagname",tagname);
        return tagDAO.queryObjs(condition);
    }

    @Override
    public Tag getTag(String tid) throws RuntimeException, Exception {
        return tagDAO.queryObj(tid);
    }

    @Override
    public boolean removeTag(String tid) throws RuntimeException, Exception {
        tagDAO.execDelete(tid);
        return true;
    }

    @Override
    public boolean addTag(Tag tag) throws RuntimeException, Exception {
        tagDAO.execInsert(tag);
        return true;
    }

    @Override
    public boolean modifyTag(Tag tag) throws RuntimeException, Exception {
        tagDAO.execUpdate(tag);
        return true;
    }

    @Override
    public List getTags(String tagname, int offset, int limit) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("tagname",tagname);
        return tagDAO.queryObjs(condition,offset,limit);
    }

    @Override
    public int getCountTags(String tagname) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("tagname",tagname);
        return tagDAO.queryCountObjs(condition);
    }
}
