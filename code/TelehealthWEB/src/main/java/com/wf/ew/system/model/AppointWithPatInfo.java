package com.wf.ew.system.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

//@TableName("appointments")
public class AppointWithPatInfo extends Appointment {

//    @TableId
    private Integer patId;
    private String name;
    private String sex;
    private Integer age;

    @Override
    public Integer getPatId() {
        return patId;
    }

    @Override
    public void setPatId(Integer patId) {
        this.patId = patId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
