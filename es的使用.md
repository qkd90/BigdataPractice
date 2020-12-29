### es的使用

es使用教程：

https://www.elastic.co/guide/cn/elasticsearch/guide/current/running-elasticsearch.html  

1.新建目录加索引：

```shell
curl -XPUT "http://10.222.10.73:9200/label_log-v0-2020_11_19d" -H 'Content-Type: application/json' -d '
{
   "settings":{
    "number_of_shards":3,
    "number_of_replicas":2
  },
  "mappings":{
    "properties":{
      "id":{"type":"long"},
      "name":{"type":"text"},
      "text":{"type":"text"}
    }
  }
}
```

2.新建和删除某个文档

```shell
curl -XDELETE "http://10.58.11.201:9200/label_log-v0-2020_11_18d"
curl -XPUT "http://10.58.11.201:9200/label_log-v0-2020_11_18d"
```

3.网页查看信息