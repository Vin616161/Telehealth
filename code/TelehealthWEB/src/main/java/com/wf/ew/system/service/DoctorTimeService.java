package com.wf.ew.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.wf.ew.system.model.DoctorTime;

import java.util.List;

public interface DoctorTimeService extends IService<DoctorTime> {

    List<DoctorTime> getByDocId(Integer docId);
    List<DoctorTime> getOnTimeByDocId(Integer docId);
    List<DoctorTime> getOffTimeByDocId(Integer docId);


}
