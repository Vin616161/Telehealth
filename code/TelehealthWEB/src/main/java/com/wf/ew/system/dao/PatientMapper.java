package com.wf.ew.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wf.ew.system.model.Patient;


public interface PatientMapper extends BaseMapper<Patient> {

    Patient getByPatId(Integer patId);
}
