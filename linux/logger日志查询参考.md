#### 一、logger日志查询参考

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
    
    
    ​    
- 前端页面无法访问（443端口访问失败）

```shell
#fantom.httpd 没启动成功查看日志： 
tailf /home/fantom/var/logs/apache/error-20xx-xx-xx.log
#fantom.httped 启动成功，仍然无法访问
#查看waf的状态： 
systemctl status sangfor_waf
```


