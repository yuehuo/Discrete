package com.qianfeng.pojo;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    private Integer id;

    private String content;

    private Integer uId;

    private Date creationdate;

    private String uName;

    private String uProfile;

    private Integer page;

    private Integer limit;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuProfile() {
        return uProfile;
    }

    public void setuProfile(String uProfile) {
        this.uProfile = uProfile;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}