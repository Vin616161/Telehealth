package com.wf.ew.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wf.ew.system.model.DoctorTime;

import java.util.List;


public interface DoctorTimeMapper extends BaseMapper<DoctorTime> {

    List<DoctorTime> getByDocId(Integer docId);
    List<DoctorTime> getOnTimeByDocId(Integer docId);
    List<DoctorTime> getOffTimeByDocId(Integer docId);
}
