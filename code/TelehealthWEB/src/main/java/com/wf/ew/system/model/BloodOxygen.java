package com.wf.ew.system.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * @program: TelehealthWEB
 * @description: 血氧仪数据实体类
 * @author: guoyang
 * @create: 2019-05-23 14:20
 **/
@TableName("blood_oxygen")
public class BloodOxygen {
    @TableId
    private int id;
    private int patientId;
    private Date recordTime;
    private int bloodOxygen;//血氧
    private double pi;//灌注指数
    private int resvalue;//阻值
    private int heartRate;//心率

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public int getBloodOxygen() {
        return bloodOxygen;
    }

    public void setBloodOxygen(int bloodOxygen) {
        this.bloodOxygen = bloodOxygen;
    }

    public double getPi() {
        return pi;
    }

    public void setPi(double pi) {
        this.pi = pi;
    }

    public int getResvalue() {
        return resvalue;
    }

    public void setResvalue(int resvalue) {
        this.resvalue = resvalue;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }
}
