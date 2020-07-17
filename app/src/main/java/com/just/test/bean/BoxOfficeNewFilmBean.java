package com.just.test.bean;

import java.io.Serializable;

/**
 * 最新票房榜
 * Created by admin on 2017/6/2.
 */

public class BoxOfficeNewFilmBean implements Serializable{

    private String rid;//排名
    private String name;//电影名称
    private String wk;//持续时间
    private String wboxoffice;//周票房
    private String tboxoffice;//总票房

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWk() {
        return wk;
    }

    public void setWk(String wk) {
        this.wk = wk;
    }

    public String getWboxoffice() {
        return wboxoffice;
    }

    public void setWboxoffice(String wboxoffice) {
        this.wboxoffice = wboxoffice;
    }

    public String getTboxoffice() {
        return tboxoffice;
    }

    public void setTboxoffice(String tboxoffice) {
        this.tboxoffice = tboxoffice;
    }
}
