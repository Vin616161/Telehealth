package com.wf.ew.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wf.ew.system.dao.DoctorMapper;
import com.wf.ew.system.model.Doctor;
import com.wf.ew.system.service.DoctorService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements DoctorService {
    @Override
    public Doctor getByDocId(Integer docId) {
        return baseMapper.getByDocId(docId);
    }

    @Override
    public List<Doctor> getOffDoctor(Integer departId, Integer clinicId, String gender, Date date, String doctorName, String insurance, String language){
        return baseMapper.getOffDoctor(departId, clinicId, gender, date, doctorName, insurance, language);
    }
}
