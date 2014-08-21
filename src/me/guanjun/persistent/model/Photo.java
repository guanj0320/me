package me.guanjun.persistent.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guanj0320 on 14-5-11.
 */
public class Photo implements Serializable {
    private String pid;
    private String aid;
    private String username;
    private String title;
    private String src;
    private String pre;
    private int width;
    private int height;
    private Date createtime;

    public Map toMap() {
        Map data = new HashMap();
        data.put("pid",pid);
        data.put("aid",aid);
        data.put("username",username);
        data.put("title",title);
        data.put("src",src);
        data.put("pre",pre);
        data.put("width",width);
        data.put("height",height);
        data.put("createtime",createtime);
        return data;
    }

    public String toJsonString() {
        return JSON.toJSONString(toMap());
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
