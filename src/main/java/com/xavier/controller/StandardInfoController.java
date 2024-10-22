package com.xavier.controller;

import com.xavier.service.StandardInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @description: 用户Application
 * @author: ex_wuzr11
 * @date: 2024/10/22 16:22
 */
@RestController
@RequestMapping("/standard/info")
public class StandardInfoController {

    @Resource
    private StandardInfoService standardInfoService;

    @PostMapping("/uploadFile")
    public void uploadFile(MultipartFile file) {
        standardInfoService.analysisFile(file);
    }

}
