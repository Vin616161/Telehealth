package com.wf.ew.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wf.ew.system.dao.PharmacyMapper;
import com.wf.ew.system.model.Pharmacy;
import com.wf.ew.system.service.PharmacyService;
import org.springframework.stereotype.Service;

@Service
public class PharmacyServiceImpl extends ServiceImpl<PharmacyMapper, Pharmacy> implements PharmacyService {
}
