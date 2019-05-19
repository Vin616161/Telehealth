package com.wf.ew.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.wf.ew.system.model.Doctor;

import java.sql.Date;
import java.util.List;

public interface DoctorService extends IService<Doctor> {

    Doctor getByDocId(Integer docId);
    List<Doctor> getOffDoctor(Integer departId, Integer clinicId, String gender, Date date,
                              String doctorName, String insurance, String language);

}
