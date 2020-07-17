package com.just.test.bean;

import java.util.List;

/**
 * 时光轴数据类
 * Created by admin on 2017/6/9.
 */

public class TimerLineBean {

    private String timer;//日期
    private List<TimerLineDetailBean> child;//内容

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public List<TimerLineDetailBean> getChild() {
        return child;
    }

    public void setChild(List<TimerLineDetailBean> child) {
        this.child = child;
    }
}
