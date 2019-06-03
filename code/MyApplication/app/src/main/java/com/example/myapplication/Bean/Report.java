package com.example.myapplication.Bean;


public class Report{
    private String name;
    private String value;
    private String time;
    public Report(){}

    public Report(String name, String value,String time) {
        this.name = name;
        this.value = value;
        this.time=time;
    }
    public void setTime(String time){
        this.time=time;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getTime() {
        return time;
    }
}
