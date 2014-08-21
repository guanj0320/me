package me.guanjun.persistent.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guanj on 2014/4/24.
 */
public class User implements Serializable {
    private String uid;
    private String username;
    private String pwd;
    private String nickname;
    private String bindaccount;
    private Date logintime;
    private String loginip;
    private Date createtime;
    private Date updatetime;
    private String email;

    public Map toMap() {
        Map data = new HashMap();
        data.put("uid",uid);
        data.put("username",username);
        data.put("pwd",pwd);
        data.put("nickname",nickname);
        data.put("bindaccount",bindaccount);
        data.put("logintime",logintime);
        data.put("loginip",loginip);
        data.put("createtime",createtime);
        data.put("updatetime",updatetime);
        data.put("email",email);
        return data;
    }

    public String toJsonString() {
        return JSON.toJSONString(toMap());
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBindaccount() {
        return bindaccount;
    }

    public void setBindaccount(String bindaccount) {
        this.bindaccount = bindaccount;
    }

    public Date getLogintime() {
        return logintime;
    }

    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }

    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
