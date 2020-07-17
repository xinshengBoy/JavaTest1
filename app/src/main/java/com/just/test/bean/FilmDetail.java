package com.just.test.bean;

import java.io.Serializable;

/**
 * 电影列表详情字段
 * Created by admin on 2017/5/23.
 */

public class FilmDetail implements Serializable{

    private  String id;//电影id
    private  String filmname;//电影名称
    private  String filmtype;//电影类别
    private  String filmdirector;//电影导演
    private  String filmstar;//主演
    private  String filmlong;//电影片长
    private  String filmcountry;//电影拍摄国家
    private  String filmdate;//上映时间
    private  String filmurl;//电影播放链接
    private  String filminfo;//电影简介

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilmname() {
        return filmname;
    }

    public void setFilmname(String filmname) {
        this.filmname = filmname;
    }

    public String getFilmtype() {
        return filmtype;
    }

    public void setFilmtype(String filmtype) {
        this.filmtype = filmtype;
    }

    public String getFilmdirector() {
        return filmdirector;
    }

    public void setFilmdirector(String filmdirector) {
        this.filmdirector = filmdirector;
    }

    public String getFilmstar() {
        return filmstar;
    }

    public void setFilmstar(String filmstar) {
        this.filmstar = filmstar;
    }

    public String getFilmlong() {
        return filmlong;
    }

    public void setFilmlong(String filmlong) {
        this.filmlong = filmlong;
    }

    public String getFilmcountry() {
        return filmcountry;
    }

    public void setFilmcountry(String filmcountry) {
        this.filmcountry = filmcountry;
    }

    public String getFilmdate() {
        return filmdate;
    }

    public void setFilmdate(String filmdate) {
        this.filmdate = filmdate;
    }

    public String getFilmurl() {
        return filmurl;
    }

    public void setFilmurl(String filmurl) {
        this.filmurl = filmurl;
    }

    public String getFilminfo() {
        return filminfo;
    }

    public void setFilminfo(String filminfo) {
        this.filminfo = filminfo;
    }
}
