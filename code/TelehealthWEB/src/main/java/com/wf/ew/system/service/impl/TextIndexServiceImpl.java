package com.wf.ew.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wf.ew.system.dao.TestIndexMapper;
import com.wf.ew.system.model.TestIndex;
import com.wf.ew.system.service.TestIndexService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextIndexServiceImpl extends ServiceImpl<TestIndexMapper, TestIndex> implements TestIndexService {
    @Override
    public List<TestIndex> getByPatId(Integer id) {
        return baseMapper.getByPatId(id);
    }
}
