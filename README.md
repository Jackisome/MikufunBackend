# Mikufun：一个私人追番弹幕播放器

Mikufun是一个VPS用户可搭建的私人番剧存储和弹幕播放网站，提供一键部署脚本，随时随地，轻松追番！

演示网站：http://72.44.78.113:7070 （密码：123456，为便于演示，该网站未限制同时登陆人数）

## 特性

+ 番剧搜索、下载与播放，支持播放、过滤弹幕
+ 自动下载，通过配置关键词规则，可以在番剧更新时自动检测并下载，做到登录即看
+ 离线下载与临时网盘，支持云端文件下载和本地文件上传下载
+ 新番时间表，提供更全的番剧内容
+ 收藏夹，具备基本的追剧管理能力
+ 密码管理，只有搭建者拥有网站的完整使用权限，最多可以与三位好友共同分享视频资源

## 快速上手

1. 你需要有一台服务器，内存最好512M及以上，磁盘空间至少2G，上限根据追番数量来确定（一般一集1080P，24分钟的番剧大约在300M以上）。

2. 执行以下命令（暂时只支持CentOS），会安装docker并自动启动容器，等待完成就行。

   ```shell
   bash <(curl -sL https://github.com/Jackisome/MikufunBackend/raw/master/mikufun-init.sh)
   ```

3. 浏览器访问ip:7070，第一次输入的密码作为用户密码，开始愉快的追番之旅！

## 感谢

1. [弹弹Play开放平台API](https://github.com/kaedei/dandanplay-libraryindex/blob/master/api/OpenPlatform.md)：提供番剧信息和视频弹幕匹配
2. [Bangumi开放API](https://bangumi.github.io/api )：提供新番时间表的信息
3. [DPlayer播放器](https://github.com/DIYgod/DPlayer)：支持弹幕播放
4. [FFMPEG](https://github.com/FFmpeg/FFmpeg )：将视频转码为流媒体格式、视频截图、字幕提取等
5. [Aria2](https://github.com/aria2/aria2 )：P2P下载
