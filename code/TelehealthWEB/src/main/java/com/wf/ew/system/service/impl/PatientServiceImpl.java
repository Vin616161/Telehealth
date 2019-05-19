package com.wf.ew.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wf.ew.system.dao.PatientMapper;
import com.wf.ew.system.model.Patient;
import com.wf.ew.system.service.PatientService;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements PatientService {
    @Override
    public Patient getByPatId(Integer patId) {
        return baseMapper.getByPatId(patId);
    }
}
