package com.wf.ew.system.model;
import java.util.List;

public class DoctorWithTimeAndUser extends DoctorWithUser {

    private List<DoctorTime> timeList;

    public List<DoctorTime> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<DoctorTime> timeList) {
        this.timeList = timeList;
    }
}
