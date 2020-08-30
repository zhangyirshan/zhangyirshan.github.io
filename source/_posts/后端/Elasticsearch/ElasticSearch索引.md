---
title: ElasticSearch索引
date: 2020-08-28 14:42:53
tags: Elasticsearch
categories: Elasticsearch
---
## 索引的操作

{% asset_img index.png index.png%}

[kibana](https://kibana.53site.com/app/kibana#/management/elasticsearch/index_management/indices?_g=())
创建一个索引的基本流程：

1. 确定索引名
2. 创建索引轮循器
3. 创建索引模板
4. 上传索引
5. 创建索引模式
6. 观察数据

### 创建索引轮循器

Name：名称

#### Hot phase

先选择是否开启rollover
三个max共同限制回滚界限

#### Delect phase

如果开启，超过设定时间会删除索引

### 创建索引模板

#### Logistics

Name: 索引模板的名称
Index patterns：适用于哪些索引例如：rongyun-*适用于所有以rongyun-开头的索引

#### Index settings

lifecycle.name: 轮循器的名称
lifecycle.rollover_alias: 索引别名，用于轮循器迭代标记索引时使用
refresh_interval: 索引刷新时间

```json
{
  "index": {
    "lifecycle": {
      "name": "rongcloud-history",
      "rollover_alias": "rongcloud-history"
    },
    "refresh_interval": "5s"
  }
}
```

#### Mappings

索引中数据的类型

```json
{
  "properties": {
    "dateTime": {
      "type": "date"
    },
    "timestramp": {
      "type": "long"
    },
    "targetId": {
      "type": "integer"
    },
    "sendDate": {
      "type": "date"
    },
    "fromUserId": {
      "type": "integer"
    },
    "groupId": {
      "type": "integer"
    },
    "targetType": {
      "type": "integer"
    },
    "source": {
      "type": "text",
      "fields": {
        "keyword": {
          "ignore_above": 256,
          "type": "keyword"
        }
      }
    },
    "msgUID": {
      "type": "text",
      "fields": {
        "keyword": {
          "ignore_above": 256,
          "type": "keyword"
        }
      }
    },
    "content": {
      "type": "object"
    },
    "@timestamp": {
      "type": "date"
    },
    "uploadDate": {
      "type": "date"
    },
    "classname": {
      "type": "text",
      "fields": {
        "keyword": {
          "ignore_above": 256,
          "type": "keyword"
        }
      }
    },
    "appId": {
      "type": "text",
      "fields": {
        "keyword": {
          "ignore_above": 256,
          "type": "keyword"
        }
      }
    }
  }
}
```

### 上传索引

一般有两种方式来进行索引上传，第一种使用rest请求，第二种使用Java配合的API进行操作（本质还是第一种）

### rest请求

```java
public static void main(String[] args) {

        try {
            String newIndex = "cn-werewolf-game-total-analysis-2020.07.31-000001";
            List<String> indexList = new ArrayList<>();
            indexList.add("cn-werewolf-game-analysis-*");
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
```

```java
public class ESUtil {
    private static final String Content_Type = "application/json; charset=UTF-8";
    // 线上es
    private static final String uri = "http://123.254.33.254:8978/";
    private static final String Authorization = "Basic asdasdwadsaxs-213xzd";

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

    public static String addAlias(String index,String alias) throws IOException {
        URL url = new URL(uri + index+"/_alias/"+alias);
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

#### java操作

```java
package com.mega.rongyunhistory.mapper;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.mega.rongyunhistory.common.ElasticsearchCommon;
import com.mega.rongyunhistory.model.RongCloudEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ElasticsearchMapper {

	private final RestHighLevelClient restHighLevelClient;

	@Autowired
	public ElasticsearchMapper(RestHighLevelClient restHighLevelClient) {
		this.restHighLevelClient = restHighLevelClient;
	}

	/**
	 * 将批量数据上传ES
	 * 
	 * @param rongCloudEntityList 批量数据
	 */
	public void upload2Elasticsearch(List<RongCloudEntity> rongCloudEntityList) {

		BulkRequest bulkRequest = new BulkRequest();
		createIndex();
		for (RongCloudEntity rongCloudEntity : rongCloudEntityList) {
			IndexRequest indexRequest = new IndexRequest(ElasticsearchCommon.INDEXNAME_ALIA);
			indexRequest.source(JSONObject.toJSONString(rongCloudEntity), XContentType.JSON);
			bulkRequest.add(indexRequest);
		}
		try {
			restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
		}
		log.debug("-------上传结束---------");
	}

	/**
	 * ES创建索引
	 */
	@SuppressWarnings("deprecation")
	public void createIndex() {
		if (!isExist(ElasticsearchCommon.INDEXNAME)) {
			CreateIndexRequest createIndexRequest = new CreateIndexRequest();

			createIndexRequest.index(ElasticsearchCommon.INDEXNAME);
			createIndexRequest.alias(new Alias(ElasticsearchCommon.INDEXNAME_ALIA));
			try {
				restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
			} catch (IOException e1) {
				log.error(e1.getLocalizedMessage());
			}
		}
	}

	/**
	 * 判断索引是否存在
	 * 
	 * @param index 索引名
	 * @return 是否存在
	 */
	public boolean isExist(String index) {
		GetIndexRequest getIndexRequest = new GetIndexRequest(index);
		boolean exit = true;
		try {
			exit = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
		}
		return exit;
	}

	/**
	 * 根据时间获取上传时间列表
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Date> selectByDate(Date start, Date end) {
		Set<Date> set = new HashSet<Date>();
		// 创建并设置SearchSourceBuilder对象，也就是设置查询条件
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		boolQueryBuilder.filter(QueryBuilders.rangeQuery("uploadDate").from(start).to(end));
		searchSourceBuilder = searchSourceBuilder.query(boolQueryBuilder);
		searchSourceBuilder.fetchSource("uploadDate", null);
		// 创建并设置SearchRequest对象
		SearchRequest searchRequest = new SearchRequest(ElasticsearchCommon.INDEXNAME_ALIA);
		searchRequest.source(searchSourceBuilder);
		// 查询
		try {
			SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
			SearchHit[] hits = search.getHits().getHits();
			for (SearchHit hit : hits) {
				Map<String, Object> sourceAsMap = hit.getSourceAsMap();
				Date date = new Date((long) sourceAsMap.get("uploadDate"));
				set.add(date);
			}
		} catch (IOException e) {
			log.error("selectByDate查询出错");
			log.error(e.getLocalizedMessage());
		}
		return new ArrayList<>(set);

	}

	public void deletByDate(Date lastUploadDate) {
		DeleteByQueryRequest delete = new DeleteByQueryRequest(ElasticsearchCommon.INDEXNAME_ALIA);
		delete.setQuery(QueryBuilders.rangeQuery("uploadDate").from(lastUploadDate));
		delete.setRefresh(true);
		try {
			BulkByScrollResponse response = restHighLevelClient.deleteByQuery(delete, RequestOptions.DEFAULT);
			log.debug("删除文档返回结果" + response.getStatus().getUpdated());
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
		}
	}
}
```
