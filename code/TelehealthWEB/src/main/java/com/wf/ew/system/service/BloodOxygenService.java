package com.wf.ew.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.wf.ew.system.model.BloodOxygen;

/**
 * @program: TelehealthWEB
 * @description:
 * @author: guoyang
 * @create: 2019-05-22 14:22
 **/
public interface BloodOxygenService extends IService<BloodOxygen> {
    BloodOxygen getLastByLimit(int limit);

}
