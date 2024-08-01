package com.tjy.backend.controller.cutepet;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.tjy.backend.mapper.CutepetMapper;
import com.tjy.backend.pojo.Cutepet;
import com.tjy.backend.utils.GetRequestExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CutepetController {
    @Autowired
    CutepetMapper cutepetMapper;

    @RequestMapping("/cutepet/uploaddata/")
    public String Uploaddata() {
        for(int m = 0;m<1000;m++){
            JSONObject res = GetRequestExample.getrequest("https://tea.qingnian8.com/tools/taoShow?size=10");
            Cutepet cutepet = new Cutepet();
            JSONArray jsonArray = res.getJSONArray("data");
            for(int i=0;i<jsonArray.size();i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Cutepet tmp = cutepetMapper.selectById(jsonObject.getString("_id"));
                if(tmp == null) {
                    cutepet.setId(jsonObject.getString("_id"));
                    cutepet.setUrl(jsonObject.getString("url"));
                    cutepet.setAuthor(jsonObject.getString("shop"));
                    cutepet.setType("taoShow");
                    cutepet.setSource("淘宝");
                    cutepet.setContent(jsonObject.getString("comment"));
                    cutepetMapper.insert(cutepet);
                }
            }
            System.out.println("第"+m + "次");

        }











        return "jj";
    }
}
