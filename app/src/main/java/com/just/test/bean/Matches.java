package com.just.test.bean;

import java.util.List;

/**
 * Created by geek on 2016/12/27.
 */
public class Matches {
    public String date;
    public static List<Team> team1;
    public static List<Team> team2;
    public int score1;
    public int score2;

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Team> getTeam1() {
        return team1;
    }

    public void setTeam1(List<Team> team1) {
        Matches.team1 = team1;
    }

    public List<Team> getTeam2() {
        return team2;
    }

    public void setTeam2(List<Team> team2) {
        Matches.team2 = team2;
    }
}
