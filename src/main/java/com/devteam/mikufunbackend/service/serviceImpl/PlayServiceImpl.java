package com.devteam.mikufunbackend.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.devteam.mikufunbackend.constant.RuntimeVariable;
import com.devteam.mikufunbackend.dao.ResourceInformationDao;
import com.devteam.mikufunbackend.entity.*;
import com.devteam.mikufunbackend.handle.FileIdException;
import com.devteam.mikufunbackend.service.serviceInterface.PlayService;
import com.devteam.mikufunbackend.service.serviceInterface.TransferService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

@Service
public class PlayServiceImpl implements PlayService {

    @Autowired
    private ResourceInformationDao resourceInformationDao;

    @Autowired
    private TransferService transferService;

    Logger logger = LoggerFactory.getLogger(PlayServiceImpl.class);

    @Override
    public List<List<Object>> getDanmaku(int fileId) throws Exception {
//        List<DanmakuV0> data = new ArrayList<>();
        List<List<Object>> data = new ArrayList<>();
        String url1 = "";
        ResourceEntity resourceEntity = null;
        try {
            resourceEntity = resourceInformationDao.findResourceInformationByFileId(fileId);
            url1 = "https://api.acplay.net/api/v2/comment/" + resourceEntity.getEpisodeId() + "?withRelated=true";
        } catch (Exception e) {
            logger.error(e.toString());
        }
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet get = new HttpGet(url1);
            HttpResponse response = client.execute(get);
            HttpEntity entity1 = response.getEntity();

            if (entity1 != null) {
                List<JSONObject> dataList = JSON.parseArray(JSONObject
                        .parseObject(EntityUtils.toString(entity1))
                        .getJSONArray("comments").toJSONString(), JSONObject.class);
                dataList.forEach(k -> {
                    // 过滤弹幕
                    if (!checkDanmuku(k.getString("m"))) {
                        return;
                    }
                    List<Object> danmaku = new ArrayList<>();
                    String[] temp = k.getString("p").split(",");
                    danmaku.add(Double.parseDouble(temp[0]));
                    danmaku.add(0);
                    danmaku.add(Integer.parseInt(temp[2]));
                    danmaku.add(temp[3]);
                    danmaku.add(k.getString("m"));
                    data.add(danmaku);
//                    DanmakuV0 danmakuV0 = DanmakuV0.builder()
//                                .time(Double.parseDouble(temp[0]))
//                                .mode(Integer.parseInt(temp[1]))
//                                .color(Integer.parseInt(temp[2]))
//                                .message(k.getString("m"))
//                                .build();
//                    data.add(danmakuV0);
//                    logger.info("add info to Danmaku, info: {}", danmakuV0.toString());
                });
            }
        } catch (Exception e) {
            logger.info("error info : {}", e.toString());
        }
        logger.info("get danmaku , info: {}", data);
        return data;
    }

    @Override
    public Boolean postDanmaku(DanmakuPostV0 comment) throws Exception {
        try {
            logger.info("put danmaku, danmaku: {}", comment);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return true;
    }

    @Override
    public List<RegExpV0> getRegex() {
        List<RegExpV0> data = new ArrayList<>();
        //todo
        return data;
    }

    @Override
    public Boolean updatePos(int fileId, double videoTime) {
        if (resourceInformationDao.updatePlayPosition(fileId, videoTime) == 1)
            return true;
        else
            return false;
    }

    @Override
    public boolean updateRecentPlayTime(int fileId) {
        return resourceInformationDao.updateRecentPlayTime(fileId) == 1;
    }

    @Override
    public Map<String, Object> findPlayInformation(int fileId) {
        ResourceEntity resourceEntity = resourceInformationDao.findResourceInformationByFileId(fileId);
        logger.info("find resource information by fileId, resourceEntity: {}", resourceEntity);
        if (resourceEntity == null) {
            throw new FileIdException("未找到fileId对应文件");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("fileUrl", "/docker/resource/" + resourceEntity.getFileUuid() + "/index." + resourceEntity.getTransferFormat());
        data.put("fileName", resourceEntity.getFileName());
        data.put("ResourceId", resourceEntity.getResourceId());
        data.put("ResourceName", resourceEntity.getResourceName());
        data.put("episode", resourceEntity.getEpisodeTitle());
        data.put("subtitleUrl", resourceEntity.getSubtitlePath());
        data.put("videoTime", resourceEntity.getRecentPlayPosition());
        data.put("format", resourceEntity.getTransferFormat());
        data.put("fontSize", RuntimeVariable.fontSize);
        data.put("fontBottom", RuntimeVariable.fontBottom);
        data.put("fontColor", RuntimeVariable.fontColor);
        return data;
    }

    @Override
    public List<MatchEpisodeRespVO> getMatchEpisodes(Integer fileId) throws IOException {
        // 获取文件 id 对应的资源文件信息
        ResourceEntity resourceEntity = resourceInformationDao.findResourceInformationByFileId(fileId);
        if (resourceEntity == null) {
            return new ArrayList<>();
        }
        // 已经是精确匹配，返回匹配的弹幕文件信息
        if (resourceEntity.getExactMatch() == 1) {
            MatchEpisodeRespVO matchEpisodeRespVO = MatchEpisodeRespVO.builder()
                    .episodeId(String.valueOf(resourceEntity.getEpisodeId()))
                    .episode(resourceEntity.getEpisodeTitle())
                    .resourceId(String.valueOf(resourceEntity.getResourceId()))
                    .resourceName(resourceEntity.getResourceName())
                    .resourceType(resourceEntity.getType()).build();
            return Collections.singletonList(matchEpisodeRespVO);
        }
        // 非精确匹配，返回所有可能的匹配弹幕文件信息
        List<ResourceMatchV0> resourceMatchV0s = transferService.matchResourceInformation(resourceEntity.getFileName(),
                resourceEntity.getFileHash(), resourceEntity.getFileSize(), resourceEntity.getVideoDuration());
        return resourceMatchV0s.stream().map(resourceMatchV0 -> MatchEpisodeRespVO.builder()
                .resourceId(String.valueOf(resourceMatchV0.getResourceId()))
                .resourceName(resourceMatchV0.getResourceName())
                .episodeId(String.valueOf(resourceMatchV0.getEpisodeId()))
                .episode(resourceMatchV0.getEpisodeTitle())
                .resourceType(resourceMatchV0.getType())
                .build()).collect(Collectors.toList());
    }

    @Override
    public void putMatchEpisode(MatchEpisodePutReqVO matchEpisodePutReqVO) {
        ResourceEntity resourceEntity = resourceInformationDao.findResourceInformationByFileId(Integer.parseInt(matchEpisodePutReqVO.getFileId()));
        if (resourceEntity == null) {
            return;
        }
        if (Objects.equals(String.valueOf(resourceEntity.getEpisodeId()), matchEpisodePutReqVO.getEpisodeId())) {
            return;
        }
        // 更新弹幕库关联信息
        resourceInformationDao.updateResourceInformation(Integer.parseInt(matchEpisodePutReqVO.getFileId()),
                resourceEntity.getExactMatch(),
                Integer.parseInt(matchEpisodePutReqVO.getResourceId()),
                matchEpisodePutReqVO.getResourceName(),
                matchEpisodePutReqVO.getEpisode(),
                matchEpisodePutReqVO.getResourceType(),
                Integer.parseInt(matchEpisodePutReqVO.getEpisodeId()));
    }

    public Boolean checkDanmuku(String comment) {
        // 方法返回值为 true 表示无需过滤此条弹幕
        if (!RuntimeVariable.defaultStatus) {
            return true;
        }
        String rule = RuntimeVariable.regex;
        if (rule == null) {
            return true;
        }
        StringTokenizer tokenizer = new StringTokenizer(rule, "\n");
        List<Pattern> rules = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            String tempRule = tokenizer.nextToken();
            try {
                Pattern pattern = Pattern.compile(tempRule);
                rules.add(pattern);
            } catch (PatternSyntaxException e) {
                logger.warn("正则语法错误 rule = {} ", tempRule);
            }
        }
        // 若匹配某一屏蔽规则，返回 false;
        for (Pattern pattern : rules) {
            Matcher matcher = pattern.matcher(comment);
            if (matcher.find()) {
                return false;
            }
        }
        return true;
    }

}
