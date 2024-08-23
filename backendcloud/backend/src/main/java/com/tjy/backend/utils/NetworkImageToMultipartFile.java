package com.tjy.backend.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class NetworkImageToMultipartFile {
    public static MultipartFile convert(String imageUrl) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(imageUrl).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // 读取图片内容
            byte[] imageData = response.body().bytes();

            // 创建输入流
            InputStream inputStream = new ByteArrayInputStream(imageData);

            // 使用 Apache Commons FileUpload 创建 MultipartFile
            FileItemFactory factory = new DiskFileItemFactory();
            org.apache.commons.fileupload.FileItem fileItem = factory.createItem(
                    "file", // 表单字段名
                    "image/jpeg", // 内容类型
                    false,
                    imageUrl.substring(imageUrl.lastIndexOf("/") + 1) // 假设文件名是 URL 的最后一部分
            );
            fileItem.getOutputStream().write(imageData);
            fileItem.getOutputStream().close();

            return (MultipartFile) fileItem;
        }
    }
}
