package me.guanjun.persistent.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public class Feedback implements Serializable {
    private String fid;
    private String username;
    private String writer;
    private String ip;
    private String content;
    private Date createtime;
    private String email;
    private String pfid;
    private int isreply;
    private Date replytime;
    private String replycontent;

    public Map toMap() {
        Map data = new HashMap();
        data.put("fid",fid);
        data.put("username",username);
        data.put("writer",writer);
        data.put("ip",ip);
        data.put("content",content);
        data.put("createtime",createtime);
        data.put("email",email);
        data.put("pfid",pfid);
        data.put("isreply",isreply);
        data.put("replytime",replytime);
        data.put("replycontent",replycontent);
        return data;
    }

    public String toJsonString() {
        return JSON.toJSONString(toMap());
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPfid() {
        return pfid;
    }

    public void setPfid(String pfid) {
        this.pfid = pfid;
    }

    public int getIsreply() {
        return isreply;
    }

    public void setIsreply(int isreply) {
        this.isreply = isreply;
    }

    public Date getReplytime() {
        return replytime;
    }

    public void setReplytime(Date replytime) {
        this.replytime = replytime;
    }

    public String getReplycontent() {
        return replycontent;
    }

    public void setReplycontent(String replycontent) {
        this.replycontent = replycontent;
    }
}
