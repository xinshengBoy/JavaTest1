package com.just.test.bean;

import java.io.Serializable;

/**
 * 网络爬虫获取歌曲信息
 * Created by admin on 2017/7/12.
 */

public class SearchMusic implements Serializable{

    private String musicName;//歌曲名称
    private String airtistName;//歌手名称
    private String musicPath;//歌曲路径
    private String albumName;//专辑名称
    private String smallAlumUrl;//小图url
    private String bigAlumUrl;//大图url
    private String musicId;//歌曲id
    private String lrcUrl;//歌词地址

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getAirtistName() {
        return airtistName;
    }

    public void setAirtistName(String airtistName) {
        this.airtistName = airtistName;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getSmallAlumUrl() {
        return smallAlumUrl;
    }

    public void setSmallAlumUrl(String smallAlumUrl) {
        this.smallAlumUrl = smallAlumUrl;
    }

    public String getBigAlumUrl() {
        return bigAlumUrl;
    }

    public void setBigAlumUrl(String bigAlumUrl) {
        this.bigAlumUrl = bigAlumUrl;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public String getLrcUrl() {
        return lrcUrl;
    }

    public void setLrcUrl(String lrcUrl) {
        this.lrcUrl = lrcUrl;
    }
}
