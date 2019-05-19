package com.wf.ew.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wf.ew.system.dao.ClinicMapper;
import com.wf.ew.system.model.Clinic;
import com.wf.ew.system.service.ClinicService;
import org.springframework.stereotype.Service;

@Service
public class ClinicServiceImpl extends ServiceImpl<ClinicMapper, Clinic> implements ClinicService {
    @Override
    public Clinic getByName(String name) {
        return baseMapper.getByName(name);
    }
}
