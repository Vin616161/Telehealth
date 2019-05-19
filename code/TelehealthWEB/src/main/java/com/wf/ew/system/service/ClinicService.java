package com.wf.ew.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.wf.ew.system.model.Clinic;

public interface ClinicService extends IService<Clinic> {

    Clinic getByName(String name);

}
