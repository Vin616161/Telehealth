package com.wf.ew.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wf.ew.system.model.Clinic;


public interface ClinicMapper extends BaseMapper<Clinic> {

    Clinic getByName(String name);
}
