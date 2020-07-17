package com.just.test.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 一定要给表命名，如下面的history
 * Created by Administrator on 2017/2/24.
 */

@Table(name = "history")
public class SearchHistory {

    @Column(name = "id",isId = true,autoGen = true,property = "NOT NULL")
    private int id;
    @Column(name = "history")
    private String history;

    public SearchHistory(String history){
        this.history = history;
    }

    public SearchHistory(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return "SearchHistory{" +
                "id=" + id +
                ", history='" + history + '\'' +
                '}';
    }
}
