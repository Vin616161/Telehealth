package com.wf.ew.system.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("patients")
public class Patient {

    @TableId
    private Integer id;  // 用户id

    private Integer patId ;

    private String name;

    private Integer pharmacyId;

    private String medicare;

    private String allergicHistory;

    private String address;//药房地址

    private String emergencyPeople;
    private String emergencyTel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatId() {
        return patId;
    }

    public void setPatId(Integer patId) {
        this.patId = patId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Integer pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public String getMedicare() {
        return medicare;
    }

    public void setMedicare(String medicare) {
        this.medicare = medicare;
    }

    public String getAllergicHistory() {
        return allergicHistory;
    }

    public void setAllergicHistory(String allergicHistory) {
        this.allergicHistory = allergicHistory;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmergencyPeople() {
        return emergencyPeople;
    }

    public void setEmergencyPeople(String emergencyPeople) {
        this.emergencyPeople = emergencyPeople;
    }

    public String getEmergencyTel() {
        return emergencyTel;
    }

    public void setEmergencyTel(String emergencyTel) {
        this.emergencyTel = emergencyTel;
    }
}
