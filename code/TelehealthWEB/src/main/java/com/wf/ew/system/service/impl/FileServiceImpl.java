package com.wf.ew.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wf.ew.common.config.FileConfig;
import com.wf.ew.system.dao.FileEntityMapper;
import com.wf.ew.system.model.FileEntity;
import com.wf.ew.system.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: TelehealthWEB
 * @description:
 * @author: guoyang
 * @create: 2019-05-22 14:28
 **/
@Service
public class FileServiceImpl extends ServiceImpl<FileEntityMapper, FileEntity> implements FileService {

    private final static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public Integer upload(FileEntity file, MultipartFile multipartFile) {
        if (file.getFileName() == null) {
            file.setFileName(multipartFile.getOriginalFilename());
        }
        if (file.getSize() == 0) {
            file.setSize(multipartFile.getSize());
        }
        if (file.getSuffix() == null) {
            String fileName = file.getFileName();
            file.setSuffix(fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length()));
        }
        if (file.getFileType() == null) {
            file.setFileType(multipartFile.getContentType());
        }
        if (file.getCreateTime() == null) {
            file.setCreateTime(new Date(System.currentTimeMillis()));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateString = sdf.format(new Date());
        String localUrl = FileConfig.getStoragePath() + System.currentTimeMillis() + file.getFileName();
        file.setLocalUrl(localUrl);
        try {
            multipartFile.transferTo(new java.io.File(localUrl));
        } catch (IOException e) {
            logger.error("save file error: " + e.getMessage());
            return -1;
        }
        int fileId = baseMapper.insertRetId(file);
        return fileId;
    }
}
