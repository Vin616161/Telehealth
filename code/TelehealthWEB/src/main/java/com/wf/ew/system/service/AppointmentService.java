package com.wf.ew.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.wf.ew.system.model.Appointment;

import java.util.List;

public interface AppointmentService extends IService<Appointment> {

    List<Appointment> getByDocId(Integer docId);

}
