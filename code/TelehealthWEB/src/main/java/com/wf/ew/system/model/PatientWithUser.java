package com.wf.ew.system.model;

import java.sql.Date;


//@TableName("doctors")
public class PatientWithUser extends Patient{

//    @TableId
    private String realName;
    private Integer age;
    private String sex;
    private String phone;
    private String medicare;
    private String pharmacy;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getMedicare() {
        return medicare;
    }

    @Override
    public void setMedicare(String medicare) {
        this.medicare = medicare;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }
}
