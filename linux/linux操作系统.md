1.sh命令：

```shell
bash [options] [file]
-c string：命令从-c后的字符串读取。
-i：实现脚本交互。
-n：进行shell脚本的语法检查。
-x：实现shell脚本逐条语句的跟踪。
```

2.systemctl enable httpd

如果你们的设备  重启不会自动启动apache  执行一下这个命令

这个之前基础组件部署的时候  这个配置没有在脚本里

3.修改后台admin账户密码

```
passwd admin
```

4.切换目录

```shell
#~当前用户目录
cd ~
cd ./文件夹   #切换到当前目录的某个文件夹 ./表示当前目录
cd ..         #切换到上级目录   
cd ../文件夹  #切换到上级目录中的某个文件夹
```

5.防火墙相关：

```shell
查看防火墙某个端口是否开放
firewall-cmd --query-port=3306/tcp

开放防火墙端口3306
firewall-cmd --zone=public --add-port=3306/tcp --permanent

查看防火墙状态
systemctl status firewalld

关闭防火墙
systemctl stop firewalld

打开防火墙
systemctl start firewalld

开放一段端口
firewall-cmd --zone=public --add-port=40000-45000/tcp --permanent

查看开放的端口列表
firewall-cmd --zone=public --list-ports
```

