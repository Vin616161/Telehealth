package com.wf.ew.system.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * @program: TelehealthWEB
 * @description: 血氧仪测量数据
 * @author: guoyang
 * @create: 2019-05-22 13:37
 **/
@TableName("blood_pressure")

public class BloodPressure {
    @TableId
    private int id;
    private int patientId;
    private Date recordTime;
    private int cuffPressure;//袖带压
    private int systolicPressure;//收缩压
    private int diastolicPressure;//舒张压
    private int heartRate;//心率

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public int getId() {
        return id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getCuffPressure() {
        return cuffPressure;
    }

    public void setCuffPressure(int cuffPressure) {
        this.cuffPressure = cuffPressure;
    }

    public int getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(int systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public int getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(int diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }
}
