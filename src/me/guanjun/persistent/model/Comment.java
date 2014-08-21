package me.guanjun.persistent.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public class Comment implements Serializable {
    private String cid;
    private String arcid;
    private String writer;
    private String ip;
    private String content;
    private Date createtime;
    private String email;
    private String pcid;

    public Map toMap() {
        Map data = new HashMap();
        data.put("cid",cid);
        data.put("arcid",arcid);
        data.put("writer",writer);
        data.put("ip",ip);
        data.put("content",content);
        data.put("createtime",createtime);
        data.put("email",email);
        data.put("pcid",pcid);
        return data;
    }

    public String toJsonString() {
        return JSON.toJSONString(toMap());
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getArcid() {
        return arcid;
    }

    public void setArcid(String arcid) {
        this.arcid = arcid;
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

    public String getPcid() {
        return pcid;
    }

    public void setPcid(String pcid) {
        this.pcid = pcid;
    }
}
