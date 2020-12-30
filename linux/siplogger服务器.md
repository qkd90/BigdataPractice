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

```
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



5.

