create database if not exists 'mikufan' default charset utf8 collate utf8_general_ci;

create table if not exists 'mikufan_resource_profile' (
    'file_id'    int NOT NULL AUTO_INCREMENT comment '文件标识',
    'file_name' varchar(1024)   NOT NULL comment '文件名',
    'file_hash' varchar(100)    comment '文件前16MB的MD5值',
    'file_size' int comment '文件大小，单位为byte'
    'exact_match' default 0 comment '对应的番剧名和集数是否精确匹配',
    'resource_id'   int comment '番剧标识',
    'resource_name' varchar(1024)   comment '番剧名',
    'episode_title' varchar(1024)   comment '剧集标题',
    'type' varchar(100) default 'unknwon'   commet '类型',
    'episode_id'    int, comment '弹幕库id',
    primary key ('file_id')
) comment '下载资源信息表';

create table if not exists 'mikufan_gid_link' (
    'id'    int NULL AUTO_INCREMENT comment '自增主键',
    'gid'   varchar(100) NOT NULL   comment '下载标识',
    'link'  text    NOT NULL    comment '下载链接',
    'status'    varchar(10) comment '下载状态',
    primary key ('id')
) comment 'gid与下载链接对应表';