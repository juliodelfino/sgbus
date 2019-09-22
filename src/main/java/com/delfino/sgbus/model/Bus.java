package com.delfino.sgbus.model;

public class Bus {

    public String no;
    public String operator;
    public BusTime next;
    public BusTime next2;
    public BusTime next3;
    public BusTime subsequent;

    @Override
    public String toString() {
        return "Bus{" +
                "no='" + no + '\'' +
                ", operator='" + operator + '\'' +
                ", next=" + next +
                ", next2=" + next2 +
                ", next3=" + next3 +
                ", subsequent=" + subsequent +
                '}';
    }
}
