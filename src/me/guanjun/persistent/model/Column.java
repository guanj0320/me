package me.guanjun.persistent.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public class Column implements Serializable {
    private String colid;
    private String colname;
    private String description;
    private String seo;
    private int ord;
    private String pcolid;

    public Map toMap() {
        Map data = new HashMap();
        data.put("colid",colid);
        data.put("colname",colname);
        data.put("description",description);
        data.put("seo",seo);
        data.put("ord",ord);
        data.put("pcolid",pcolid);
        return data;
    }

    public String toJsonString() {
        return JSON.toJSONString(toMap());
    }

    public String getColid() {
        return colid;
    }

    public void setColid(String colid) {
        this.colid = colid;
    }

    public String getColname() {
        return colname;
    }

    public void setColname(String colname) {
        this.colname = colname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeo() {
        return seo;
    }

    public void setSeo(String seo) {
        this.seo = seo;
    }

    public int getOrd() {
        return ord;
    }

    public void setOrd(int ord) {
        this.ord = ord;
    }

    public String getPcolid() {
        return pcolid;
    }

    public void setPcolid(String pcolid) {
        this.pcolid = pcolid;
    }
}
