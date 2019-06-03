package com.wf.ew.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wf.ew.system.dao.DoctorTimeMapper;
import com.wf.ew.system.model.DoctorTime;
import com.wf.ew.system.service.DoctorTimeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorTimeServiceImpl extends ServiceImpl<DoctorTimeMapper, DoctorTime> implements DoctorTimeService {
    @Override
    public List<DoctorTime> getByDocId(Integer docId) {
        return baseMapper.getByDocId(docId);
    }

    @Override
    public List<DoctorTime> getOnTimeByDocId(Integer docId) {
        return baseMapper.getOnTimeByDocId(docId);
    }

    @Override
    public List<DoctorTime> getOffTimeByDocId(Integer docId) {
        return baseMapper.getOffTimeByDocId(docId);
    }
}
