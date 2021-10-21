package com.devteam.mikufunbackend.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.devteam.mikufunbackend.constant.Aria2Constant;
import com.devteam.mikufunbackend.entity.Aria2RequestV0;
import com.devteam.mikufunbackend.entity.Aria2ResponseV0;
import com.devteam.mikufunbackend.entity.Aria2StatusV0;
import com.devteam.mikufunbackend.handle.Aria2Exception;
import com.devteam.mikufunbackend.service.serviceInterface.Aria2Service;
import com.devteam.mikufunbackend.util.HttpClientUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
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

    private final Logger logger = LoggerFactory.getLogger(Aria2Service.class);

    @Value("${aria2.url}")
    private String aria2RpcUrl;

    @Override
    public String addUrl(String link) throws IOException, Aria2Exception {
        Aria2RequestV0 aria2RequestV0 = new Aria2RequestV0();
        aria2RequestV0.setMethod(Aria2Constant.METHOD_ADD_URI)
                .addParam(new String[]{link});
        logger.info("begin download, link: {}", link);
        CloseableHttpResponse response = HttpClientUtil.sendPostAsJson(aria2RpcUrl, aria2RequestV0);
        if (!HttpClientUtil.validateResponse(response)) {
            logger.error("Aria2下载未正常进行，下载链接: {}", link);
            throw new Aria2Exception("下载未正常进行");
        }
        logger.info("send to aria2 for downloading finished, link: {}", link);
        String entityString = EntityUtils.toString(response.getEntity());
        Map<String, String> entityMap = JSON.parseObject(entityString, new TypeReference<Map<String, String>>(){});
        return entityMap.get("result");
    }

    @Override
    public boolean pauseAllDownloadingFile() throws IOException {
        Aria2RequestV0 aria2RequestV0 = new Aria2RequestV0();
        aria2RequestV0.setMethod(Aria2Constant.METHOD_PAUSE_ALL);
        logger.info("begin pause all downloading file");
        CloseableHttpResponse response = HttpClientUtil.sendPostAsJson(aria2RpcUrl, aria2RequestV0);
        String entityString = EntityUtils.toString(response.getEntity());
        System.out.println(entityString);
        if (!HttpClientUtil.validateResponse(response)) {
            logger.error("Aria2未正常暂停所有下载");
            throw new Aria2Exception("暂停所有下载未正常进行");
        }
        logger.info("send to aria2 for pause all finished");
        return true;
    }

    @Override
    public boolean unpauseAllDownloadingFile() throws IOException {
        Aria2RequestV0 aria2RequestV0 = new Aria2RequestV0();
        aria2RequestV0.setMethod(Aria2Constant.METHOD_UNPAUSE_ALL);
        logger.info("begin unpause all downloading file");
        CloseableHttpResponse response = HttpClientUtil.sendPostAsJson(aria2RpcUrl, aria2RequestV0);
        String entityString = EntityUtils.toString(response.getEntity());
        System.out.println(entityString);
        if (!HttpClientUtil.validateResponse(response)) {
            logger.error("Aria2未正常恢复所有下载");
            throw new Aria2Exception("恢复所有下载未正常进行");
        }
        logger.info("send to aria2 for unpause all finished");
        return true;
    }

    @Override
    public boolean transferDownloadStatus(String gid, String method) throws IOException {
        Aria2RequestV0 aria2RequestV0 = new Aria2RequestV0();
        aria2RequestV0.setMethod(method)
                .addParam(gid);
        logger.info("begin transfer status of downloading file, gid: {}, method: {}", gid, method);
        CloseableHttpResponse response = HttpClientUtil.sendPostAsJson(aria2RpcUrl, aria2RequestV0);
        String entityString = EntityUtils.toString(response.getEntity());
        System.out.println(entityString);
        if (!HttpClientUtil.validateResponse(response)) {
            logger.error("Aria2状态切换未正常进行, gid: {}, method: {}", gid, method);
            throw new Aria2Exception("下载状态未正常切换");
        }
        logger.info("send to aria2 for transfer file status finished, gid: {}, method: {}", gid, method);
        return true;
    }

    @Override
    public Aria2StatusV0 tellDownloadingFileStatus(String gid) {
        return null;
    }

    @Override
    public List<Aria2StatusV0> getFileStatus(String type) throws IOException {
        Aria2RequestV0 aria2RequestV0 = new Aria2RequestV0();
        aria2RequestV0.setMethod(type);
        if (!Aria2Constant.METHOD_TELL_ACTIVE.equals(type)) {
            aria2RequestV0.addParam(0)
                    .addParam(1000);
        }
        aria2RequestV0.addParam(Aria2Constant.PARAM_ARRAY_OF_FILED);
        logger.info("begin get file status, type: {}", type);
        CloseableHttpResponse response = HttpClientUtil.sendPostAsJson(aria2RpcUrl, aria2RequestV0);
        if (!HttpClientUtil.validateResponse(response)) {
            logger.error("Aria2查看文件信息未完成");
            throw new Aria2Exception("查看文件信息未完成");
        }
        Aria2ResponseV0 aria2ResponseV0 = (Aria2ResponseV0) HttpClientUtil.convertJsonToObject(response, Aria2ResponseV0.class);
        logger.info("get file status finish, type: {}, content: {}", type, aria2ResponseV0.toString());
        return aria2ResponseV0.getResult();
    }

    @Override
    public Map<String, String> getGlobalStatus() {
        return null;
    }
}
