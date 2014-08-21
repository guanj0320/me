package me.guanjun.persistent.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public class Tag implements Serializable {
    private String tid;
    private String tagname;
    private int num;
    private Date createtime;

    public Map toMap() {
        Map data = new HashMap();
        data.put("tid",tid);
        data.put("tagname",tagname);
        data.put("num",num);
        data.put("createtime",createtime);
        return data;
    }

    public String toJsonString() {
        return JSON.toJSONString(toMap());
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
