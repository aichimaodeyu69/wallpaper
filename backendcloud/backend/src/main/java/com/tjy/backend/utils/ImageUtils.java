package com.tjy.backend.utils;

import com.alibaba.fastjson2.JSONObject;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
//import com.qiniu.http.Response;
import lombok.Data;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

@Component
public class ImageUtils {
    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.accessSecretKey}")
    private String accessSecretKey;
    @Value("${qiniu.bucket}")
    private String bucket;
    @Value("${qiniu.imageUrl}")
    private String url;

    /**
     * 处理多文件
     * @param multipartFiles
     * @return
     */
    public Map<String, List<String>> uploadImages(MultipartFile[] multipartFiles,String dataPath){
        Map<String,List<String>> map = new HashMap<>();
        List<String> imageUrls = new ArrayList<>();
        for(MultipartFile file : multipartFiles){
            imageUrls.add(uploadImageQiniu(file,dataPath));
        }
        map.put("imageUrl",imageUrls);
        return map;
    }

    /**
     * 上传图片到七牛云
     * @param multipartFile
     * @return
     */
    public String uploadImageQiniu(MultipartFile multipartFile, String dataPath){
        try {
            //1、获取文件上传的流
            byte[] fileBytes = multipartFile.getBytes();
            //2、创建日期目录分隔
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//            String datePath = dateFormat.format(new Date());
//            System.out.println(datePath);
            //3、获取文件名
            String originalFilename = multipartFile.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = dataPath+"/"+ UUID.randomUUID().toString().replace("-", "")+ suffix;

            //4.构造一个带指定 Region 对象的配置类
            //Region.huabei(根据自己的对象空间的地址选
            Configuration cfg = new Configuration(Region.huadong());
            UploadManager uploadManager = new UploadManager(cfg);

            //5.获取七牛云提供的 token
            Auth auth = Auth.create(accessKey, accessSecretKey);
            String upToken = auth.uploadToken(bucket);
            com.qiniu.http.Response  res = uploadManager.put(fileBytes,filename,upToken);
            if (res.isOK()) {
                String resUrl ="https://"+  url +"/"+ JSONObject.parseObject(res.bodyString()).get("key");
                System.out.println(resUrl);
                return resUrl;
            } else {
                // 处理上传失败的情况
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String uploadImageQiniuByRemoteUrl(String imageUrl, String dataPath){
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(imageUrl).build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Failed to fetch image from URL: " + imageUrl);
            }

            // 读取图片内容
            byte[] imageData = response.body().bytes();


            String suffix = imageUrl.substring(imageUrl.lastIndexOf("."));
            String filename = dataPath+ "/" + UUID.randomUUID().toString().replace("-", "")+ suffix;

            //4.构造一个带指定 Region 对象的配置类
            //Region.huabei(根据自己的对象空间的地址选
            Configuration cfg = new Configuration(Region.huadong());
            UploadManager uploadManager = new UploadManager(cfg);

            //5.获取七牛云提供的 token
            Auth auth = Auth.create(accessKey, accessSecretKey);
            String upToken = auth.uploadToken(bucket);
            com.qiniu.http.Response  res = uploadManager.put(imageData,filename,upToken);
            if (res.isOK()) {
                String resUrl ="https://"+  url +"/"+ JSONObject.parseObject(res.bodyString()).get("key");
//                System.out.println(resUrl);
                return resUrl;
            } else {
                // 处理上传失败的情况
                return null;
            }
//            return url+filename;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}