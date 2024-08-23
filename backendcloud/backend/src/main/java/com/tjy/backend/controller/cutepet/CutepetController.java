package com.tjy.backend.controller.cutepet;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tjy.backend.mapper.CutepetMapper;
import com.tjy.backend.pojo.Cutepet;
import com.tjy.backend.utils.GetRequestExample;
import com.tjy.backend.utils.ImageUtils;
import com.tjy.backend.utils.NetworkImageToMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
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
        return "OK";
    }

    @RequestMapping("/cutepet/all/")
    public List<Cutepet> getAllCutepet(String type,String size) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("type",type);
        queryWrapper.orderByAsc("RAND()");
        queryWrapper.last("LIMIT "+size);


        return cutepetMapper.selectList(queryWrapper);
    }
    @Autowired
    private ImageUtils imageUtils;

    @RequestMapping("/cutepet/changeimageurl/")
    public String ChangeImageUrl() throws Exception {
        List<Cutepet> cutepets = cutepetMapper.selectList(null);
        for(Cutepet cutepet : cutepets){
            String type = cutepet.getType();
            String remote_url = cutepet.getUrl();
            if(remote_url.contains("sealks")){
                continue;
            }
            String dataPath = "wallpaper/"+type;
            String imageUrl = imageUtils.uploadImageQiniuByRemoteUrl(remote_url,dataPath);
            System.out.println(imageUrl);
            cutepet.setUrl(imageUrl);
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.eq("id",cutepet.getId());
            updateWrapper.set("url",imageUrl);
            cutepetMapper.update(null,updateWrapper);
        }
        return "OK";
    }
}
