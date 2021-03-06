create database if not exists mikufun character set utf8 collate utf8_general_ci;

-- 只保存下载完成的文件
create table if not exists mikufun.mikufun_resource_information
(
    file_id              int AUTO_INCREMENT comment '文件标识',
    file_name            varchar(1024) NOT NULL comment '文件名',
    file_uuid            varchar(1024) NOT NULL comment '文件标识uuid',
    file_hash            varchar(100) comment '文件前16MB的MD5值',
    file_size            bigint comment '文件大小，单位为byte',
    src_file_path       varchar(1024)   NOT NULL comment '源文件路径',
    transfer_format      varchar(100)  NOT NULL comment '文件播放格式',
    video_duration       int           default 0 comment '视频时长，单位为秒',
    image_url            text comment '番剧图片地址',
    subtitle_path        varchar(1024) default '' comment '字幕路径',
    recent_play_time     timestamp     default '2000-01-01 01:00:00' comment '最近播放时间',
    recent_play_position double           default   0.00    comment '最近播放位置，单位为秒',
    download_time        timestamp     default CURRENT_TIMESTAMP comment '下载时间，实际为文件处理完成的保存时间',
    exact_match          int           default 0 comment '对应的番剧名和集数是否精确匹配',
    resource_id          int           default 0 comment '番剧标识',
    resource_name        varchar(1024) default '未识别' comment '番剧名',
    episode_title        varchar(1024) comment '剧集标题',
    type                 varchar(100) comment '类型',
    episode_id           int comment '弹幕库id',
    danmaku_shift        double        default 0.00 comment '弹幕延迟时间',
    gid                  varchar(100)  NOT NULL comment '下载标识',
    primary key (file_id)
) comment '下载资源信息表';

-- 保存不同收藏状态的番剧
CREATE TABLE IF NOT EXISTS mikufun.mikufun_favorite_status_record
(
    `record_id`   int(11)     NOT NULL AUTO_INCREMENT COMMENT 'id',
    `resource_id` int(11)     NOT NULL COMMENT '番剧标识',
    `resource_name` varchar(100)    NOT NULL    COMMENT '番剧名',
    `air_date`  varchar(20) COMMENT '上映日期',
    `description`   varchar(1024)   COMMENT '番剧简介',
    `image_url` varchar(1024)   COMMENT '番剧图片地址',
    `rating`    double  default 0.00    COMMENT '番剧评分',
    `status`      varchar(20) NOT NULL COMMENT '收藏状态',
    `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`record_id`)
);

-- 自动下载规则
create table if not exists mikufun.mikufun_auto_download_rule
(
    rule_id              int AUTO_INCREMENT comment '规则标识',
    rule_name            VARCHAR(100)  NOT NULL comment '规则名',
    keyword              VARCHAR(1024) NOT NULL comment '搜索关键词',
    active_resource_time timestamp default '1990-01-01 01:00:00' comment '只下载该时间之后资源',
    create_time          timestamp default CURRENT_TIMESTAMP comment '规则创建时间',
    update_time          timestamp default '1990-01-01 01:00:00' comment '已下载的最新资源时间',
    active               int       default 0 comment '规则激活状态',
    primary key (rule_id)
) comment '自动下载规则表';