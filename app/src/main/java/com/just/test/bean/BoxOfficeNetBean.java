package com.just.test.bean;

import java.io.Serializable;

/**
 * 网票票房排行榜
 * Created by admin on 2017/6/2.
 */

public class BoxOfficeNetBean implements Serializable {

    private String name;//影片名称
    private String totals;//总场次
    private String statistics;//统计场次
    private String averaging;//场均人数
    private String attendance;//上座率
    private String people;//光影总人数
    private String fare;//票价
    private String boxoffice;//总票房

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotals() {
        return totals;
    }

    public void setTotals(String totals) {
        this.totals = totals;
    }

    public String getStatistics() {
        return statistics;
    }

    public void setStatistics(String statistics) {
        this.statistics = statistics;
    }

    public String getAveraging() {
        return averaging;
    }

    public void setAveraging(String averaging) {
        this.averaging = averaging;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getBoxoffice() {
        return boxoffice;
    }

    public void setBoxoffice(String boxoffice) {
        this.boxoffice = boxoffice;
    }
}
