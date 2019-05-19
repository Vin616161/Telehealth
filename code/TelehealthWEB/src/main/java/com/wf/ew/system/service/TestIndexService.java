package com.wf.ew.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.wf.ew.system.model.TestIndex;

import java.util.List;

public interface TestIndexService extends IService<TestIndex> {

    List<TestIndex> getByPatId(Integer id);

}
