

1.futurex 的kafka工具位置

```shell
/usr/share/kafka_2.11-2.3.0/bin/
```

2.查看kafka的topic

```shell
/usr/share/kafka_2.11-2.3.0/bin/kafka-topics.sh --zookeeper localhost:2181 --list
```

3.查询某一个topic的信息

```shell
/usr/share/kafka_2.11-2.3.0/bin/kafka-topics.sh --zookeeper localhost:2181 --topic SANGFOR_EVENT_EVENT --describe
```

4.查询消费者组

```shell
/usr/share/kafka_2.11-2.3.0/bin/kafka-consumer-groups.sh  --bootstrap-server localhost:9092 --list
```

5.查询特定组的

```shell
/usr/share/kafka_2.11-2.3.0/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group sip --describe
```

6.新建topic

```shell
/usr/share/kafka_2.11-2.3.0/bin/kafka-topics.sh --create --topic siplogger --zookeeper localhost:9092 --replication-factor 1 --partitions 1 
```

7.