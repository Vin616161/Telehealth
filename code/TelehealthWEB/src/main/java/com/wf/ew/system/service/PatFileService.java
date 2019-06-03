package com.wf.ew.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.wf.ew.system.model.PatientFile;

public interface PatFileService extends IService<PatientFile> {

    PatientFile getByPatId(Integer patId);

}
