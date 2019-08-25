package com.wf.ew.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.wf.ew.system.model.FileEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: TelehealthWEB
 * @description:
 * @author: guoyang
 * @create: 2019-05-22 14:22
 **/
public interface FileService extends IService<FileEntity> {

    public Integer upload(FileEntity file, MultipartFile multipartFile);

}
