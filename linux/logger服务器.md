#### 一、logger日志排查问题思路

在测试接口的时候，同时查看日志，能够发现一些被吞掉的错误信息。

##### 1.如何查看页面对应的后台日志？
- 日志路径：/var/log
- 业务日志路径: /var/log/today 
- 前端展示日志路径： /var/log/today/sis_模块名_ci.log 
    ex:资产模块前端日志：/var/log/today/sis_asset_ci.log
- 关键业务日志：
        
    ```shell
    logstash代理日志： /var/log/today/sis_logstash_manager.log 
    #主要负责logstash进程的启动，重启，关闭，以及pipelines的配置生成
    
    logstash运行日志：/var/log/logstash-logs/logstash-plain.log
    # 能够查看管道的运行状态，是否起来，以及日志过滤的错误信息
    ```


##### 2.系统起不来，如何查看？
- 首先查看mongo服务是否起来（identifier 依赖于mongo）
      systemctl status mongod #查看mongo服务
        tailf /var/log/mongodb/mongodb.log #查看mongo日志
  
- 查看identifier服务是否起来（大多数程序依赖idt,主要提供资产相关的服务） 
  
        systemctl status identifier # 查看id是否起来 
- 查看后台进程运行状态
  ```shell
    python /home/fantom/fantom.py sv status 
    #注意，这些进程大多作为服务的代理，状态在running中，并不意味着相应的业务进程已经起来。
    #正常的结果如下：
    /home/fantom/apps/agent2/bin/device_predict/main.py                   
    /home/fantom/apps/sec_thirdparty/bin/rule_engine/siem_rule_engine.py  
    /home/fantom/apps/sec_thirdparty/bin/siem/audit_event.py              
    /home/fantom/apps/seclib/bin/updcenter/main.py                        
    /home/fantom/apps/super/bin/scheduler/monitor.py                      
    fantom.elasticsearch                                                  
    fantom.httpd                                                          
    fantom.scheduler                                                      
    logstash_manager                                                      
  ```
  
- 前端页面无法访问（443端口访问失败）

```shell
#fantom.httpd 没启动成功查看日志： 
tailf /home/fantom/var/logs/apache/error-20xx-xx-xx.log
#fantom.httped 启动成功，仍然无法访问
#查看waf的状态： 
systemctl status sangfor_waf
```

二、常用命令

1.查看sshd服务

```
systemctl status sshd
systemctl status rsyslog
```

2.检索包含logstash的所有进程，ps为检索进程，

```shell
ps -aux | grep logstash
```

3.修改系统时间：

```shell
1.日期20200712
date -s 20200712
2.设置时间
date -s 18:30:50
3.一步设置时间
date -s "20200712 18:30:50"
4.最后都要记得保存
hwclock --systohc
```

4.

![这里写图片描述](https://imgconvert.csdnimg.cn/aHR0cDovL2ltZy5ibG9nLmNzZG4ubmV0LzIwMTgwMTA1MTUwNDA5Mzgz?x-oss-process=image/format,png)

解析“drwxrwxrwx"，这个权限说明一共10位。

第一位代表文件类型，有两个数值：“d”和“-”，“d”代表目录，“-”代表非目录。

后面9位可以拆分为3组来看，分别对应不同用户，2-4位代表所有者user的权限说明，5-7位代表组群group的权限说明，8-10位代表其他人other的权限说明。

r代表可读权限，w代表可写权限，x代表可执行权限。

"drwxrwxrwx”表示所有用户都对这个目录有可读可写可执行权限。



5.数据源接入：

```shell
1.管道的配置文件位置
cd /home/fantom/apps/agent2/var/run/logstash-config/pipelines
查看配置文件.cfg后缀，就是储存配置设置的可以用文本编辑器打开的
```

```shell
2.py脚本的位置
cd /home/fantom/apps/agent2/bin/collector/script
```

6.



