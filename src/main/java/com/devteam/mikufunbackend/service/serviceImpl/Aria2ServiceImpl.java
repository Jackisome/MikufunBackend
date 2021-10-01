package com.devteam.mikufunbackend.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.devteam.mikufunbackend.constant.Aria2Constant;
import com.devteam.mikufunbackend.entity.Aria2Entity;
import com.devteam.mikufunbackend.entity.Aria2OptionEntity;
import com.devteam.mikufunbackend.entity.DownloadStatusEntity;
import com.devteam.mikufunbackend.handle.Aria2Exception;
import com.devteam.mikufunbackend.service.serviceInterface.Aria2Service;
import com.devteam.mikufunbackend.util.HttpClientUtil;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Jackisome
 * @date 2021/10/1
 */
@Service
public class Aria2ServiceImpl implements Aria2Service {

    private Logger logger = LoggerFactory.getLogger(Aria2Service.class);

    @Value("${aria2.url}")
    private String aria2RpcUrl;

    @Override
    public String addUrl(String link) throws IOException, DocumentException {
        Aria2Entity aria2Entity = new Aria2Entity();
        aria2Entity.setMethod(Aria2Constant.METHOD_ADD_URI)
                .addParam(new String[]{link});
        CloseableHttpResponse response = HttpClientUtil.sendPostAsJson(aria2RpcUrl, aria2Entity);
        if (!HttpClientUtil.validateResponse(response)) {
            logger.error("Aria2下载未正常进行，下载链接: {}", link);
            throw new Aria2Exception("下载未正常进行");
        }
        String entityString = EntityUtils.toString(response.getEntity());
        org.dom4j.Document document = DocumentHelper.parseText(entityString);
        org.dom4j.Element rootElement = document.getRootElement();
        String gid = rootElement.elementText("result");
        return gid;
    }

    @Override
    public boolean removeDownloadingFile(String gid) throws IOException {
        Aria2Entity aria2Entity = new Aria2Entity();
        aria2Entity.setMethod(Aria2Constant.METHOD_REMOVE_DOWNLOAD_RESULT)
                .addParam(gid);
        CloseableHttpResponse response = HttpClientUtil.sendPostAsJson(aria2RpcUrl, aria2Entity);
        if (!HttpClientUtil.validateResponse(response)) {
            logger.error("Aria2下载未正常移除, gid: {}", gid);
            throw new Aria2Exception("移除下载未正常进行");
        }
        return true;
    }

    @Override
    public boolean pauseDownloadingFile(String gid) {
        return false;
    }

    @Override
    public boolean pauseAllDownloadingFile() {
        return false;
    }

    @Override
    public boolean unpauseDownloadingFile(String gid) {
        return false;
    }

    @Override
    public boolean unpauseAllDownloadingFile() {
        return false;
    }

    @Override
    public DownloadStatusEntity tellDownloadingFileStatus(String gid, String keys) {
        return null;
    }

    @Override
    public List<DownloadStatusEntity> getActiveFileStatus() {
        return null;
    }

    @Override
    public List<DownloadStatusEntity> getWaitingFileStatus() {
        return null;
    }

    @Override
    public List<DownloadStatusEntity> getStoppedFilesStatus() {
        return null;
    }

    @Override
    public Map<String, String> getGlobalStatus() {
        return null;
    }
}
