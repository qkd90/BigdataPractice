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

