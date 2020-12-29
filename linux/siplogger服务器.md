1.

```
systemctl status sshd
```

2.logstash

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

