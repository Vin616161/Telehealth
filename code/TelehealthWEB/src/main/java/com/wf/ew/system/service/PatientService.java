package com.wf.ew.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.wf.ew.system.model.Patient;

public interface PatientService extends IService<Patient> {

    Patient getByPatId(Integer patId);

}
