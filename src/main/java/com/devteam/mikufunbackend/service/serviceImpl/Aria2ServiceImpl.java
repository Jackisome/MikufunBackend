package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.constant.Aria2Constant;
import com.devteam.mikufunbackend.entity.Aria2RequestV0;
import com.devteam.mikufunbackend.entity.Aria2ResponseV0;
import com.devteam.mikufunbackend.entity.Aria2StatusV0;
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
        System.out.println(entityString);
        return "1234567890";
    }

    @Override
    public boolean removeDownloadingFile(String gid) throws IOException {
        Aria2RequestV0 aria2RequestV0 = new Aria2RequestV0();
        aria2RequestV0.setMethod(Aria2Constant.METHOD_REMOVE_DOWNLOAD_RESULT)
                .addParam(gid);
        logger.info("begin remove downloading file, gid: {}", gid);
        CloseableHttpResponse response = HttpClientUtil.sendPostAsJson(aria2RpcUrl, aria2RequestV0);
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
        logger.info("get file status finish, type: {}", type);
        Aria2ResponseV0 aria2ResponseV0 = (Aria2ResponseV0) HttpClientUtil.convertJsonToObject(response, Aria2ResponseV0.class);
        return aria2ResponseV0.getResult();
    }

    @Override
    public Map<String, String> getGlobalStatus() {
        return null;
    }
}
