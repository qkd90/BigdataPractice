#!/bin/bash

#使用 nohup  启动一个父进程关闭之后仍然能够运行的后台进程
nohup java -jar web-demo.jar > web-demo.log &
