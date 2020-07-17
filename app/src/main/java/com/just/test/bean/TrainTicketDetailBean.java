package com.just.test.bean;

/**
 * 火车票具体详情
 * Created by admin on 2017/6/17.
 */

public class TrainTicketDetailBean {

    private String train;//车次
    private String from;//始发站
    private String to;//目标站
    private String start;//车次始发站
    private String end;//车次终点站
    private String leave;//发车时间
    private String arrive;//到达时间
    private String time;//耗时
    private Tickets tickets;//余票信息
    public static class Tickets{
        private String rz;//软座
        private String yz;//硬座
        private String wz;//无座
        private String rw;//软卧
        private String yw;//硬卧
        private String swz;//商务座
        private String tz;//特等座
        private String zy;//一等座
        private String ze;//二等座
        private String gr;//高级软卧
        private String qt;//其他

        public String getRz() {
            return rz;
        }

        public void setRz(String rz) {
            this.rz = rz;
        }

        public String getYz() {
            return yz;
        }

        public void setYz(String yz) {
            this.yz = yz;
        }

        public String getWz() {
            return wz;
        }

        public void setWz(String wz) {
            this.wz = wz;
        }

        public String getRw() {
            return rw;
        }

        public void setRw(String rw) {
            this.rw = rw;
        }

        public String getYw() {
            return yw;
        }

        public void setYw(String yw) {
            this.yw = yw;
        }

        public String getSwz() {
            return swz;
        }

        public void setSwz(String swz) {
            this.swz = swz;
        }

        public String getTz() {
            return tz;
        }

        public void setTz(String tz) {
            this.tz = tz;
        }

        public String getZy() {
            return zy;
        }

        public void setZy(String zy) {
            this.zy = zy;
        }

        public String getZe() {
            return ze;
        }

        public void setZe(String ze) {
            this.ze = ze;
        }

        public String getGr() {
            return gr;
        }

        public void setGr(String gr) {
            this.gr = gr;
        }

        public String getQt() {
            return qt;
        }

        public void setQt(String qt) {
            this.qt = qt;
        }
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Tickets getTickets() {
        return tickets;
    }

    public void setTickets(Tickets tickets) {
        this.tickets = tickets;
    }
}
