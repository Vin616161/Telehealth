package com.wf.ew.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wf.ew.system.model.Appointment;

import java.util.List;


public interface AppointmentMapper extends BaseMapper<Appointment> {

    List<Appointment> getByDocId(Integer docId);
}
