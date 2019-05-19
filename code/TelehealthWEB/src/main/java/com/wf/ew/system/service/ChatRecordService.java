package com.wf.ew.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.wf.ew.system.model.ChatRecord;

import java.util.List;

public interface ChatRecordService extends IService<ChatRecord> {

    List<ChatRecord> getByDocIdAndPatId(Integer docId, Integer patId);

}
