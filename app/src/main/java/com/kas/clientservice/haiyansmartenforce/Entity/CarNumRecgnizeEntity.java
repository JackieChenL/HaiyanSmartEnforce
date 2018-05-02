package com.kas.clientservice.haiyansmartenforce.Entity;

/**
 * 描述：
 * 时间：2018-04-27
 * 公司：COMS
 */

public class CarNumRecgnizeEntity {

    /**
     * status : 0
     * msg : ok
     * result : {"number":"苏E703Y5","color":"蓝色","type":"02","score":"","position":""}
     */

    public String status;
    public String msg;
    public ResultBean result;

    public static class ResultBean {
        /**
         * number : 苏E703Y5
         * color : 蓝色
         * type : 02
         * score :
         * position :
         */

        public String number;
        public String color;
        public String type;
        public String score;
        public String position;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
