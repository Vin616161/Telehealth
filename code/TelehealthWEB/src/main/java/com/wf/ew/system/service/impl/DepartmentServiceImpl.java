package com.wf.ew.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wf.ew.system.dao.DepartmentMapper;
import com.wf.ew.system.model.Department;
import com.wf.ew.system.service.DepartmentService;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

}
