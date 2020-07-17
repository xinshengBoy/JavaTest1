package com.just.test.bean;

import java.util.List;

/**
 * Created by user on 2016/12/27.
 */

public class Clubs {
    public String name;
    public List<Rounds> rounds;

    public static class Rounds{
        public String name;
        public List<Matches> matches;

        public static class Matches{
            public String date;
            public static List<Team1> team1;

            public static class Team1{
                public String key;
                public String name;
                public String code;

                public String getKey() {
                    return key;
                }

                public void setKey(String key) {
                    this.key = key;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }
            }
            public static List<Team2> team2;
            public static class  Team2{
                public String key;
                public String name;
                public String code;

                public String getKey() {
                    return key;
                }

                public void setKey(String key) {
                    this.key = key;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }
            }
            public int score1;
            public int score2;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

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

            public static List<Team1> getTeam1() {
                return team1;
            }

            public static void setTeam1(List<Team1> team1) {
                Matches.team1 = team1;
            }

            public static List<Team2> getTeam2() {
                return team2;
            }

            public static void setTeam2(List<Team2> team2) {
                Matches.team2 = team2;
            }
        }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
