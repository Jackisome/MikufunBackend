package com.devteam.mikufunbackend.util;

import com.alibaba.fastjson.JSONObject;
import com.devteam.mikufunbackend.handle.Aria2Exception;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/1
 */
public class HttpClientUtil {
    /**
     * 使用GET方式发送请求
     * @param urlPath 调用地址
     * @param nameValuePairs 参数内容
     * @return 响应体
     * @throws URISyntaxException
     * @throws IOException
     */
    public static CloseableHttpResponse sendGet(String urlPath, List<NameValuePair> nameValuePairs) throws URISyntaxException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URIBuilder uri = new URIBuilder();
        uri.setPath(urlPath);
        uri.setParameters(nameValuePairs);
        HttpGet httpGet = new HttpGet(uri.build());
        try {
            return httpClient.execute(httpGet);
        } catch (IOException e) {
            throw e;
        } finally {
            httpClient.close();
        }
    }

    /**
     * 使用POST方式发送请求
     * @param urlPath 调用地址
     * @param nameValuePairs body的内容
     * @return 响应体
     * @throws URISyntaxException
     * @throws IOException
     */
    public static CloseableHttpResponse sendPost(String urlPath, List<NameValuePair> nameValuePairs) throws URISyntaxException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URIBuilder uri = new URIBuilder();
        uri.setPath(urlPath);
        uri.setParameters(nameValuePairs);
        HttpPost httpPost = new HttpPost(uri.build());
        try {
            return httpClient.execute(httpPost);
        } catch (IOException e) {
            throw e;
        } finally {
            httpClient.close();
        }
    }

    /**
     * 使用POST方式发送请求，转换成JSON格式之后，将entity作为body的内容
     * @param urlPath 调用地址
     * @param entity 作为body的对象
     * @return 响应体
     * @throws IOException
     */
    public static CloseableHttpResponse sendPostAsJson(String urlPath, Object entity) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(urlPath);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
        httpPost.setEntity(new StringEntity(JSONObject.toJSONString(entity), StandardCharsets.UTF_8));
        try {
            return httpClient.execute(httpPost);
        } catch (IOException e) {
            throw e;
        } finally {
            httpClient.close();
        }
    }

    /**
     * 检测HTTP调用是否有正常响应
     * @param response 响应体
     * @return 是否正常响应
     */
    public static boolean validateResponse(CloseableHttpResponse response) {
        return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
    }
}
