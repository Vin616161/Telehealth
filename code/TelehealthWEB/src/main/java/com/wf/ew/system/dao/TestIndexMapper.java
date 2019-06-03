package com.wf.ew.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wf.ew.system.model.TestIndex;

import java.util.List;


public interface TestIndexMapper extends BaseMapper<TestIndex> {

    List<TestIndex> getByPatId(Integer id);
}
