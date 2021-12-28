#!/bin/bash
ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
yum -y update
yum install -y yum-utils device-mapper-persistent-data lvm2
yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
yum install -y docker-ce docker-ce-cli containerd.io
systemctl start docker
systemctl enable docker.service
docker network create --driver bridge --subnet 172.1.0.0/16 mikufun
docker run --name mikufun-mysql --restart=always --net mikufun -v ~/mikufun/mysql/conf:/etc/mysql/conf.d -v ~/mikufun/mysql/logs:/logs -e TZ=Asia/Shanghai -v ~/mikufun/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -dit jackiesome/mikufun-mysql:latest
docker run --name mikufun-backend --restart=always --net mikufun -p 9090:80 -v ~/mikufun/resource:/docker -dit jackiesome/offlinedownload:latest
docker run --name mikufun-front --restart=always --net mikufun -d -p 7070:80 jackiesome/front:latest