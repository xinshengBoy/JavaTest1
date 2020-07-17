package com.just.test.bean;

import java.io.Serializable;

/**
 * 电影海报类
 * Created by admin on 2017/5/26.
 */

public class FilmImage implements Serializable{

    private String id;//电影海报id
    private String filmImage_name;//电影海报名称  电影名称
    private String filmImage_date;//电影上映时间
    private String filmImage_url;//电影海报路径
    private byte[] filmImage_picture;//电影图片

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilmImage_name() {
        return filmImage_name;
    }

    public void setFilmImage_name(String filmImage_name) {
        this.filmImage_name = filmImage_name;
    }

    public String getFilmImage_date() {
        return filmImage_date;
    }

    public void setFilmImage_date(String filmImage_date) {
        this.filmImage_date = filmImage_date;
    }

    public String getFilmImage_url() {
        return filmImage_url;
    }

    public void setFilmImage_url(String filmImage_url) {
        this.filmImage_url = filmImage_url;
    }

    public byte[] getFilmImage_picture() {
        return filmImage_picture;
    }

    public void setFilmImage_picture(byte[] filmImage_picture) {
        this.filmImage_picture = filmImage_picture;
    }
}
