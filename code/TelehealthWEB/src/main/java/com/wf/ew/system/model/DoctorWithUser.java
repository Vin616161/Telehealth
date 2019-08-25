package com.wf.ew.system.model;

import java.sql.Date;
import java.util.List;


//@TableName("doctors")
public class DoctorWithUser extends Doctor {

    //    @TableId
    private String username;
    private String nickName;
    private String sex;
    private Date birthday;
    private String phone;
    private String email;
    private String department;
    private String clinic;
    private String address;
    private List<DoctorTime> availableTimes;

    public List<DoctorTime> getAvailableTimes() { return availableTimes; }

    public void setAvailableTimes(List<DoctorTime> availableTimes) { this.availableTimes = availableTimes; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
