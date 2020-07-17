package com.just.test.bean;

/**
 * 笑话大全
 * Created by admin on 2017/6/1.
 */

public class JokeBean {

    private String content;//内容
    private String updatetime;//更新时间
    private String url;//图片链接
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
