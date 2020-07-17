package com.just.test.bean;

/**
 * 时光轴二级item，时光轴内每个日期的具体内容
 * Created by admin on 2017/6/9.
 */

public class TimerLineDetailBean {

    private String finishTime;//预计完成时间
    private boolean isFinished;//是否完成

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
