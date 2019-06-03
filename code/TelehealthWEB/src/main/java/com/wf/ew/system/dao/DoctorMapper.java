package com.wf.ew.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wf.ew.system.model.Doctor;

import java.sql.Date;
import java.util.List;


public interface DoctorMapper extends BaseMapper<Doctor> {

    Doctor getByDocId(Integer docId);
    List<Doctor> getOffDoctor(Integer departId, Integer clinicId, String gender, Date date,
                              String doctorName, String insurance, String language);
}
