package com.wf.ew.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wf.ew.system.dao.BloodPressureMapper;
import com.wf.ew.system.model.BloodPressure;
import com.wf.ew.system.service.BloodPressureService;
import org.springframework.stereotype.Service;

/**
 * @program: TelehealthWEB
 * @description:
 * @author: guoyang
 * @create: 2019-05-22 14:28
 **/
@Service
public class BloodPressureServiceImpl extends ServiceImpl<BloodPressureMapper, BloodPressure> implements BloodPressureService {

    @Override
    public BloodPressure getLastByLimit(int limit) {
        return baseMapper.getLastByLimit(limit);
    }
}
