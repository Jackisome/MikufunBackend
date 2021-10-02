package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.constant.Aria2Constant;
import com.devteam.mikufunbackend.entity.Aria2Entity;
import com.devteam.mikufunbackend.entity.DownloadStatusV0;
import com.devteam.mikufunbackend.handle.Aria2Exception;
import com.devteam.mikufunbackend.service.serviceInterface.Aria2Service;
import com.devteam.mikufunbackend.util.HttpClientUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentException;
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
    public String addUrl(String link) throws IOException, DocumentException, Aria2Exception {
        Aria2Entity aria2Entity = new Aria2Entity();
        aria2Entity.setMethod(Aria2Constant.METHOD_ADD_URI)
                .addParam(new String[]{link});
        logger.info("begin download, link: {}", link);
        CloseableHttpResponse response = HttpClientUtil.sendPostAsJson(aria2RpcUrl, aria2Entity);
        if (!HttpClientUtil.validateResponse(response)) {
            logger.error("Aria2下载未正常进行，下载链接: {}", link);
            throw new Aria2Exception("下载未正常进行");
        }
        logger.info("send to aria2 for downloading finished, link: {}", link);
        String entityString = EntityUtils.toString(response.getEntity());
        System.out.println(entityString);
        return "1234567890";
    }

    @Override
    public boolean removeDownloadingFile(String gid) throws IOException {
        Aria2Entity aria2Entity = new Aria2Entity();
        aria2Entity.setMethod(Aria2Constant.METHOD_REMOVE_DOWNLOAD_RESULT)
                .addParam(gid);
        logger.info("begin remove downloading file, gid: {}", gid);
        CloseableHttpResponse response = HttpClientUtil.sendPostAsJson(aria2RpcUrl, aria2Entity);
        String entityString = EntityUtils.toString(response.getEntity());
        System.out.println(entityString);
        if (!HttpClientUtil.validateResponse(response)) {
            logger.error("Aria2下载未正常移除, gid: {}", gid);
            throw new Aria2Exception("移除下载未正常进行");
        }
        logger.info("send to aria2 for removing finished, gid: {}", gid);
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
    public DownloadStatusV0 tellDownloadingFileStatus(String gid, String keys) {
        return null;
    }

    @Override
    public List<DownloadStatusV0> getActiveFileStatus() throws IOException {
        Aria2Entity aria2Entity = new Aria2Entity();
        aria2Entity.setMethod(Aria2Constant.METHOD_TELL_ACTIVE)
                .addParam(Aria2Constant.PARAM_ARRAY_OF_FILED);
        logger.info("begin get active file status");
        CloseableHttpResponse response = HttpClientUtil.sendPostAsJson(aria2RpcUrl, aria2Entity);
        if (!HttpClientUtil.validateResponse(response)) {
            logger.error("Aria2查看active文件信息未完成");
            throw new Aria2Exception("查看active文件信息未完成");
        }
        return null;
    }

    @Override
    public List<DownloadStatusV0> getWaitingFileStatus() {
        return null;
    }

    @Override
    public List<DownloadStatusV0> getStoppedFilesStatus() {
        return null;
    }

    @Override
    public Map<String, String> getGlobalStatus() {
        return null;
    }
}
