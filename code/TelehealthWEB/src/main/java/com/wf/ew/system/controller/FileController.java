package com.wf.ew.system.controller;

import com.wf.ew.common.BaseController;
import com.wf.ew.common.JsonResult;
import com.wf.ew.common.utils.ControllerUtils;
import com.wf.ew.system.model.FileEntity;
import com.wf.ew.system.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Api(value = "文件相关的接口", tags = "file")
@RestController
@RequestMapping("${api.version}/file")
public class FileController extends BaseController {
    @Autowired
    FileService fileService;

    @ApiOperation(value = "上传一个文件", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileType", value = "文件类型信息", required = true, dataType = "String", paramType = "form"),
           @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping(value = "/upload")
    public JsonResult upload(String fileType, @RequestParam("file") MultipartFile multipartFile) {
        FileEntity file = new FileEntity();
        file.setFileType(fileType);
        fileService.upload(file,multipartFile);
        return JsonResult.ok("上传成功").put("fileId", file.getFileId());
    }



    @ApiOperation(value = "下载一个文件", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileId", value = "文件Id", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @GetMapping(value = "/download")
    public void download(Integer fileId, HttpServletResponse response) throws IOException {
        String path = fileService.selectById(fileId).getLocalUrl();
        ControllerUtils.loadResource(response, path, true);
    }
    @ApiOperation(value = "获取一个文件", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileId", value = "文件Id", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @GetMapping
    public void get(Integer fileId, HttpServletResponse response) throws IOException {
        String path = fileService.selectById(fileId).getLocalUrl();
        ControllerUtils.loadResource(response, path, false);
    }

}
