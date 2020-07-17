package com.just.test.bean;

import java.util.List;

/**
 * Created by geek on 2016/12/27.
 */
public class Rounds {
    public String name;
    public List<Matches> matches;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Matches> getMatches() {
        return matches;
    }

    public void setMatches(List<Matches> matches) {
        this.matches = matches;
    }
}
