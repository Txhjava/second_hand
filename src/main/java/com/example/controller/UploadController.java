package com.example.controller;

import cn.hutool.core.util.StrUtil;
import com.example.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {

    @PostMapping("/icon")
    public Result uploadIcon(@RequestParam("file") MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            // 生成新文件名
            String fileName = createNewFileName("icon" ,originalFilename);
            //保存文件
            file.transferTo(new File("E:\\idea_maven_project\\springboot\\second_hand\\src\\main\\resources\\static",fileName));
            return Result.success(fileName);
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
    }

    @PostMapping("/url")
    public Result updateUrl(@RequestParam("file") MultipartFile file){
        try{
            String originalFilename = file.getOriginalFilename();
            // 生成新文件名
            String fileName = createNewFileName("good" ,originalFilename);
            file.transferTo(new File("E:\\idea_maven_project\\springboot\\second_hand\\src\\main\\resources\\static",fileName));
            return Result.success(fileName);
        } catch (IOException e){
            throw new RuntimeException("文件上传失败", e);
        }
    }

    private String createNewFileName(String prefix ,String originalFilename) {
        // 获取后缀
        String suffix = StrUtil.subAfter(originalFilename, ".", true);
        //生成文件名
        String name = prefix + "-" + System.currentTimeMillis();
        return StrUtil.format("/img/{}/{}.{}", prefix, name, suffix);
    }

}
