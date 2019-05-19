package com.wf.ew.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wf.ew.system.dao.ChatRecordMapper;
import com.wf.ew.system.model.ChatRecord;
import com.wf.ew.system.service.ChatRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRecordServiceImpl extends ServiceImpl<ChatRecordMapper, ChatRecord> implements ChatRecordService {
    @Override
    public List<ChatRecord> getByDocIdAndPatId(Integer docId, Integer patId) {
        return baseMapper.getByDocIdAndPatId(docId, patId);
    }
}
