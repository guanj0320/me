package me.guanjun.persistent.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public class Article implements Serializable {
    private String arcid;
    private String colid;
    private String tid;
    private String username;
    private int property;
    private String pic;
    private String description;
    private String content;
    private Date createtime;
    private Date updatetime;
    private int clicl;
    private String source;
    private int ord;
    private String title;

    //多表关系中的中文显示
    private String colname;
    private String tagname;
    private String nickname;

    public Map toMap() {
        Map data = new HashMap();
        data.put("arcid",arcid);
        data.put("colid",colid);
        data.put("tid",tid);
        data.put("username",username);
        data.put("property",property);
        data.put("pic",pic);
        data.put("description",description);
        data.put("content",content);
        data.put("createtime",createtime);
        data.put("updatetime",updatetime);
        data.put("clicl",clicl);
        data.put("source",source);
        data.put("ord",ord);
        data.put("title",title);

        data.put("colname",colname);
        data.put("tagname",tagname);
        data.put("nickname",nickname);
        return data;
    }

    public String toJsonString() {
        return JSON.toJSONString(toMap());
    }

    public String getArcid() {
        return arcid;
    }

    public void setArcid(String arcid) {
        this.arcid = arcid;
    }

    public String getColid() {
        return colid;
    }

    public void setColid(String colid) {
        this.colid = colid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getProperty() {
        return property;
    }

    public void setProperty(int property) {
        this.property = property;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public int getClicl() {
        return clicl;
    }

    public void setClicl(int clicl) {
        this.clicl = clicl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getOrd() {
        return ord;
    }

    public void setOrd(int ord) {
        this.ord = ord;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColname() {
        return colname;
    }

    public void setColname(String colname) {
        this.colname = colname;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
