package com.tjy.backend.controller.QiniuTest;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.tjy.backend.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class ImageController {

    @Autowired
    private ImageUtils imageUtils;

    @RequestMapping("image/upload/")
    public String uploadImage(@RequestParam(value = "file",required = false) MultipartFile[] multipartFile){
        if(ObjectUtils.isEmpty(multipartFile)){
            return "请选择图片";
        }
        Map<String, List<String>> uploadImagesUrl = imageUtils.uploadImages(multipartFile,"wallpaper");
        return "OK";
    }

}