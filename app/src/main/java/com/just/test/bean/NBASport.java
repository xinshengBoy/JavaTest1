package com.just.test.bean;

/**
 * NBA球员得分情况
 * Created by admin on 2017/5/20.
 */

public class NBASport {

    private String position;//位置
    private String playingTime;//出场时间
    private String shoot;//投篮
    private String threePoints;//三分
    private String freeThrow;//罚球
    private String frontBackboard;//前篮板
    private String backboard;//后篮板
    private String totalBackboard;//总篮板
    private String assists;//助攻
    private String steals;//抢断
    private String blocks;//盖帽
    private String errors;//失误
    private String foul;//犯规
    private String efficiency;//效率值
    private String score;//得分

    public NBASport(String position, String playingTime, String shoot, String threePoints, String freeThrow, String frontBackboard, String backboard, String totalBackboard, String assists, String steals, String blocks, String errors, String foul, String efficiency, String score) {
        this.position = position;
        this.playingTime = playingTime;
        this.shoot = shoot;
        this.threePoints = threePoints;
        this.freeThrow = freeThrow;
        this.frontBackboard = frontBackboard;
        this.backboard = backboard;
        this.totalBackboard = totalBackboard;
        this.assists = assists;
        this.steals = steals;
        this.blocks = blocks;
        this.errors = errors;
        this.foul = foul;
        this.efficiency = efficiency;
        this.score = score;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(String playingTime) {
        this.playingTime = playingTime;
    }

    public String getShoot() {
        return shoot;
    }

    public void setShoot(String shoot) {
        this.shoot = shoot;
    }

    public String getThreePoints() {
        return threePoints;
    }

    public void setThreePoints(String threePoints) {
        this.threePoints = threePoints;
    }

    public String getFreeThrow() {
        return freeThrow;
    }

    public void setFreeThrow(String freeThrow) {
        this.freeThrow = freeThrow;
    }

    public String getFrontBackboard() {
        return frontBackboard;
    }

    public void setFrontBackboard(String frontBackboard) {
        this.frontBackboard = frontBackboard;
    }

    public String getBackboard() {
        return backboard;
    }

    public void setBackboard(String backboard) {
        this.backboard = backboard;
    }

    public String getTotalBackboard() {
        return totalBackboard;
    }

    public void setTotalBackboard(String totalBackboard) {
        this.totalBackboard = totalBackboard;
    }

    public String getAssists() {
        return assists;
    }

    public void setAssists(String assists) {
        this.assists = assists;
    }

    public String getSteals() {
        return steals;
    }

    public void setSteals(String steals) {
        this.steals = steals;
    }

    public String getBlocks() {
        return blocks;
    }

    public void setBlocks(String blocks) {
        this.blocks = blocks;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public String getFoul() {
        return foul;
    }

    public void setFoul(String foul) {
        this.foul = foul;
    }

    public String getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(String efficiency) {
        this.efficiency = efficiency;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
