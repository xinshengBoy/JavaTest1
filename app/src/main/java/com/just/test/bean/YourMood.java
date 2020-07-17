package com.just.test.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 你的心情实例化类
 * Created by admin on 2017/9/1.
 */

@Table(name = "YourMood")
public class YourMood {

    @Column(name = "id",isId = true)
    private int ID;

    @Column(name = "mood")
    private String mood;

    @Column(name = "time")
    private String time;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "YourMood{" + "ID=" + ID +",mood=" + mood + ",time=" + time + "}";
    }
}
