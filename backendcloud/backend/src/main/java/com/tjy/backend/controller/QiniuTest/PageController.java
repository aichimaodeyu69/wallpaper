package com.tjy.backend.controller.QiniuTest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/upload/")
    public String to(){
        return "upload.html";
    }

}
