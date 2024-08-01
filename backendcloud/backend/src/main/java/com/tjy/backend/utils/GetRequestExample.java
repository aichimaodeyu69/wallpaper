package com.tjy.backend.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetRequestExample {
    public static JSONObject getrequest(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法为 GET
            connection.setRequestMethod("GET");

            // 添加请求头，例如接受类型
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("access-key", "000719");
            //连接服务器
            connection.connect();

            // 获取响应码
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // 检查响应码并读取响应
            if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // 打印结果
                String responseStr = response.toString();
                JSONObject jsonObject = JSON.parseObject(responseStr);
//                System.out.println(jsonObject);
                return jsonObject;
            } else {
                System.out.println("GET请求不成功，响应码：" + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
