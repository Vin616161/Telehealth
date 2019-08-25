package com.wf.ew.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wf.ew.system.model.FileEntity;

/**
 * @program: TelehealthWEB
 * @description:
 * @author: guoyang
 * @create: 2019-05-22 14:20
 **/
public interface FileEntityMapper extends BaseMapper<FileEntity> {

    int insertRetId(FileEntity file);
}
