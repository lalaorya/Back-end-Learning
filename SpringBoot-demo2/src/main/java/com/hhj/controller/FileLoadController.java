package com.hhj.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@Api(tags = "文件上传相关接口", description = "提供用户相关的 Rest API")
// 文件上传
@Slf4j
public class FileLoadController {

    @PostMapping("/fileLoad")
    @ApiOperation("文件上传接口")
    public String fileLoad(@RequestPart("file")MultipartFile file,@RequestPart("files") MultipartFile[] files) throws IOException {
        log.info("单个文件：{}\n多个文件：{}",file.getOriginalFilename(),files.length);

        if(!file.isEmpty()){
            // 保存到本地或者服务器
            String filename = file.getOriginalFilename();
            file.transferTo(new File("D:\\"+filename));
        }

        if(files.length>0){
            for(MultipartFile a:files){
                String filename = a.getOriginalFilename();
                a.transferTo(new File("D:\\"+filename));
            }
        }

        return "上传成功";
    }

}
