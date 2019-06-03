package com.wf.ew.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wf.ew.system.dao.AppointmentMapper;
import com.wf.ew.system.model.Appointment;
import com.wf.ew.system.service.AppointmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {
    @Override
    public List<Appointment> getByDocId(Integer docId) {
        return baseMapper.getByDocId(docId);
    }
}
