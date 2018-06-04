package com.kas.clientservice.haiyansmartenforce.tcsf.bean;



public class CpBean {

    public String  status;
    public String msg;
    public  EntityBean result;



            public  class EntityBean{

                public  String number;
                public  String color;
                public String type;
                public  String score;
                public  String position;

                @Override
                public String toString() {
                    return "EntityBean{" +
                            "number='" + number + '\'' +
                            ", color='" + color + '\'' +
                            ", type='" + type + '\'' +
                            ", score='" + score + '\'' +
                            ", position='" + position + '\'' +
                            '}';
                }

            }

    @Override
    public String toString() {
        return "CpBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
