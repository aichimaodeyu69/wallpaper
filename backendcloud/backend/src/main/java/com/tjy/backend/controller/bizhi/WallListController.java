package com.tjy.backend.controller.bizhi;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController

@RequestMapping("/api/bizhi/")
public class WallListController {
    @RequestMapping("wallList/")
    public List<String> getList(){
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        return list;
    }
}
