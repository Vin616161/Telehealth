package com.wf.ew.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wf.ew.system.model.PatientFile;


public interface PatFileMapper extends BaseMapper<PatientFile> {

    PatientFile getByPatId(Integer patId);
}
