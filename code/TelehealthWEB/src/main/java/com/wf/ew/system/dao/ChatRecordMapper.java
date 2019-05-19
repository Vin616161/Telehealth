package com.wf.ew.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wf.ew.system.model.ChatRecord;

import java.util.List;


public interface ChatRecordMapper extends BaseMapper<ChatRecord> {

    List<ChatRecord> getByDocIdAndPatId(Integer docId, Integer patId);
}
