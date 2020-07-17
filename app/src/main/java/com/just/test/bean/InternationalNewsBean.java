package com.just.test.bean;

/**
 * 国际新闻
 * Created by admin on 2017/6/1.
 */

public class InternationalNewsBean {

    private String ctime;//发表时间
    private String title;//新闻标题
    private String description;//新闻简介
    private String picUrl;//图片链接
    private String url;//详情链接

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
