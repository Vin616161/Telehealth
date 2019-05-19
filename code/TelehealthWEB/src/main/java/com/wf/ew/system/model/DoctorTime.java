package com.wf.ew.system.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.sql.Date;
import java.sql.Time;

@TableName("doctor_time")
public class DoctorTime {

    @TableId
    private Integer id;

    private Integer docId;

    private Date date;//年月日

    private Time beginTime; //时分秒

    private Time endTime;

    private Integer type;

    private Integer appointed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Time beginTime) {
        this.beginTime = beginTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAppointed() {
        return appointed;
    }

    public void setAppointed(Integer appointed) {
        this.appointed = appointed;
    }
}
