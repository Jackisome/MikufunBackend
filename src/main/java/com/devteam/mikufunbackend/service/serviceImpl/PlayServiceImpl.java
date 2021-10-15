package com.devteam.mikufunbackend.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.devteam.mikufunbackend.dao.ResourceInformationDao;
import com.devteam.mikufunbackend.entity.*;
import com.devteam.mikufunbackend.service.serviceInterface.PlayService;
import com.devteam.mikufunbackend.util.HttpClientUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlayServiceImpl implements PlayService {

    @Autowired
    private ResourceInformationDao resourceInformationDao;

    Logger logger = LoggerFactory.getLogger(PlayServiceImpl.class);

    @Override
    public ResourceEntity getFileAddr(int fileId){
        ResourceEntity resourceEntity = null;
        try {
            resourceEntity = resourceInformationDao.findResourceInformationByFileId(fileId);
        } catch (Exception e){
            logger.error(e.toString());
        }
        logger.info("get fileAddress, info: {}", resourceEntity);
        return resourceEntity;
    }

    @Override
    public List<DanmakuV0> getDanmaku(int fileId) throws Exception{
        List<DanmakuV0> data = new ArrayList<>();
        String url1 = "";
        ResourceEntity resourceEntity = null;
        try {
            resourceEntity = resourceInformationDao.findResourceInformationByFileId(fileId);
            url1="https://api.acplay.net/api/v2/comment/"+resourceEntity.getEpisodeId()+"?withRelated=true";
        }catch(Exception e){
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
                        .getJSONArray("comments").toJSONString(),JSONObject.class);
                dataList.forEach(k->{
                    String[] temp = k.getString("p").split(",");
                    DanmakuV0 danmakuV0 = DanmakuV0.builder()
                                .time(Double.parseDouble(temp[0]))
                                .mode(Integer.parseInt(temp[1]))
                                .color(Integer.parseInt(temp[2]))
                                .message(k.getString("m"))
                                .build();
                    data.add(danmakuV0);
                    logger.info("add info to Danmaku, info: {}", danmakuV0.toString());
                });
            }
        } catch (Exception e) {
            logger.info("error info : {}",e.toString());
        }
        logger.info("get danmaku , info: {}", data);
        return data;
    }

    @Override
    public Boolean postDanmaku(DanmakuPostV0 comment) throws Exception{
        try{
            //todo
        }catch (Exception e){
            logger.error(e.toString());
        }
        return true;
    }

    @Override
    public List<RegExpV0> getRegex(){
        List<RegExpV0> data = new ArrayList<>();
        //todo
        return data;
    }

    @Override
    public Boolean updatePos(int fileId,int videoTime){
        if(resourceInformationDao.updatePlayPosition(fileId,videoTime)==1)
            return true;
        else
            return false;
    }
}
