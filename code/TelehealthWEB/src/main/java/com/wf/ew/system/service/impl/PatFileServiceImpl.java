package com.wf.ew.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wf.ew.system.dao.PatFileMapper;
import com.wf.ew.system.model.PatientFile;
import com.wf.ew.system.service.PatFileService;
import org.springframework.stereotype.Service;

@Service
public class PatFileServiceImpl extends ServiceImpl<PatFileMapper, PatientFile> implements PatFileService {
    @Override
    public PatientFile getByPatId(Integer patId) {
        return baseMapper.getByPatId(patId);
    }
}
