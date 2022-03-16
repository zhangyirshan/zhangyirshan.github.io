---
title: ElasticSearch合并索引
date: 2022-03-16 14:42:53
tags: Elasticsearch
categories: Elasticsearch
---

```java
public class MergeIndex {

    private static final Logger logger = Logger.getLogger(MergeIndex.class.getName());

    public static void main(String[] args) {

        try {
            String newIndex = "cn-plat-exchange-transaction-history-prod-000001";
            List<String> indexList = new ArrayList<>();
            indexList.add("cn-plat-exchange-transaction-prod*");
            // 判断删除的索引名是否包含合并的新索引名，如果包含直接结束
            for (String index : indexList) {
                String deleteIndex = index.substring(0, index.length() - 1);
                if (newIndex.contains(deleteIndex)) {
                    logger.debug("请检查删除索引数组是否包含新建索引");
                    logger.debug("删除索引名：" + index + " 新建索引名：" + newIndex);
                    return;
                }
            }
            // 合并索引
            String result = ESUtil.mergeIndex(newIndex, indexList);
            if (!result.contains("error")) {
                // 删除旧索引
                List<String> response = ESUtil.deleteIndex(indexList);
                logger.debug(response);
                // 给新索引添加别名
                String alias = "test-cn-werewolf-game-client";
                String responseAddAlias = ESUtil.addAlias(newIndex, alias);
                logger.debug(responseAddAlias);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

```java
package com.mega.elasticsearch.dao;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: ZYi
 * @Date: 2020/6/12 13:28
 * @Version 1.0
 **/
public class ESUtil {
    private static final String Content_Type = "application/json; charset=UTF-8";
    // 线上es
    private static final String uri = "http://172.16.50.35:9200/";
    private static final String Authorization = "Basic ZWxhc3RpYzpNYW5nb3N0ZWVuMCE=";
    // 本地es
//    private static final String uri = "http://192.168.2.72:9200/";
//    private static final String Authorization = "Basic ZWxhc3RpYzpMQTE5NTRiIQ==";

    public static String mergeIndex(String newIndex, List<String> source) throws IOException {

        URL url = new URL(uri + "_reindex");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoInput(true); // 设置可输入
        connection.setDoOutput(true); // 设置该连接是可以输出的
        connection.setRequestMethod("POST"); // 设置请求方式
        connection.setRequestProperty("Content-Type", Content_Type);
        connection.setRequestProperty("Authorization", Authorization);
        connection.connect();

        StringBuilder reIndex = new StringBuilder("{\"source\":{\"index\":[");

        for (String index : source) {
            reIndex.append("\"").append(index).append("\",");
        }
        reIndex.deleteCharAt(reIndex.lastIndexOf(","));
        reIndex.append("]},\"dest\":{\"index\":");
        reIndex.append("\"").append(newIndex).append("\"");
        reIndex.append("}}");
        PrintWriter pw = new PrintWriter(new BufferedOutputStream(connection.getOutputStream()));
        pw.write(reIndex.toString());
        pw.flush();
        pw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = br.readLine()) != null) { // 读取数据
            result.append(line).append("\n");
        }
        connection.disconnect();

        return result.toString();
    }

    public static List<String> deleteIndex(List<String> indexList) throws IOException {
        List<String> resultList = new ArrayList<>();
        for (String index : indexList) {
            URL url = new URL(uri + index);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true); // 设置可输入
            connection.setDoOutput(true); // 设置该连接是可以输出的
            connection.setRequestMethod("DELETE"); // 设置请求方式
            connection.setRequestProperty("Content-Type", Content_Type);
            connection.setRequestProperty("Authorization", Authorization);
            connection.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) { // 读取数据
                result.append(line).append("\n");
            }
            resultList.add(result.toString());
            connection.disconnect();
        }
        return resultList;
    }

    public static String addAlias(String index, String alias) throws IOException {
        URL url = new URL(uri + index + "/_alias/" + alias);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoInput(true); // 设置可输入
        connection.setDoOutput(true); // 设置该连接是可以输出的
        connection.setRequestMethod("PUT"); // 设置请求方式
        connection.setRequestProperty("Content-Type", Content_Type);
        connection.setRequestProperty("Authorization", Authorization);
        connection.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = br.readLine()) != null) { // 读取数据
            result.append(line).append("\n");
        }
        connection.disconnect();
        return result.toString();
    }
}
```
