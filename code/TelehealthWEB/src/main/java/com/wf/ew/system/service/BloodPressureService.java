package com.wf.ew.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.wf.ew.system.model.BloodPressure;

/**
 * @program: TelehealthWEB
 * @description:
 * @author: guoyang
 * @create: 2019-05-22 14:22
 **/
public interface BloodPressureService extends IService<BloodPressure> {
    BloodPressure getLastByLimit(int limit);
}
