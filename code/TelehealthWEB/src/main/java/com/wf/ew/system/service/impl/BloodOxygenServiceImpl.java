package com.wf.ew.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wf.ew.system.dao.BloodOxygenMapper;
import com.wf.ew.system.model.BloodOxygen;
import com.wf.ew.system.service.BloodOxygenService;
import org.springframework.stereotype.Service;

/**
 * @program: TelehealthWEB
 * @description:
 * @author: guoyang
 * @create: 2019-05-22 14:28
 **/
@Service
public class BloodOxygenServiceImpl extends ServiceImpl<BloodOxygenMapper, BloodOxygen> implements BloodOxygenService {

    @Override
    public BloodOxygen getLastByLimit(int limit) {
        return baseMapper.getLastByLimit(limit);
    }
}
