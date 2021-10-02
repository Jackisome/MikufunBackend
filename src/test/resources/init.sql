create database if not exists 'mikufun' default charset utf8 collate utf8_general_ci;

-- 只保存下载完成的文件
create table if not exists 'mikufun_resource_information' (
    'file_id'    int NOT NULL AUTO_INCREMENT comment '文件标识',
    'file_name' varchar(1024)   NOT NULL comment '文件名',
    'file_hash' varchar(100)    comment '文件前16MB的MD5值',
    'file_size' int comment '文件大小，单位为byte',
    'video_duration'    int default 0   comment '视频时长，单位为秒',
    'recent_play_time'  timestamp(8) comment '最近播放时间',
    'recent_play_position'  int default 0   comment '最近播放位置，单位为秒',
    'download_time' timestamp(8) default current_timestamp comment '下载时间，实际为文件处理完成的保存时间',
    'exact_match' default 0 comment '对应的番剧名和集数是否精确匹配',
    'resource_id'   int comment '番剧标识',
    'resource_name' varchar(1024)   comment '番剧名',
    'episode_title' varchar(1024)   comment '剧集标题',
    'type' varchar(100) comment '类型',
    'episode_id'    int, comment '弹幕库id',
    'gid'   varchar(100)    NOT NULL comment '下载标识',
    primary key ('file_id')
) comment '下载资源信息表';

-- 文件下载之后还需要做种，处于stopped且已完成转码的文件才能删除
create table if not exists 'mikufun_download_status' (
    'id'    int NULL AUTO_INCREMENT comment '自增主键',
    'gid'   varchar(100) NOT NULL   comment '下载标识',
    'link'  text    NOT NULL    comment '下载链接',
    'path'  text    NOT NULL    comment '文件路径',
    'is_finish' int default 0   comment '文件下载是否已完成',
    'is_source_delete'  int default 0   comment '转码前文件是否已删除',
    'status'    varchar(20) comment '下载状态',
    primary key ('id')
) comment '下载和保存状态信息表';