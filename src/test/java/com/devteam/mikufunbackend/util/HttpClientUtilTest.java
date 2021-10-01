package com.devteam.mikufunbackend.util;

import com.devteam.mikufunbackend.AbstractTestBase;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.Test;
import org.junit.Assert;


/**
 * @author Jackisome
 * @date 2021/10/1
 */
public class HttpClientUtilTest extends AbstractTestBase {
    @Test
    public void testSendGet() {
        String urlPath = "http://www.baidu.com";
        try {
            CloseableHttpResponse response = HttpClientUtil.sendGet(urlPath, null);
            Assert.assertNotNull(response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testSendPost() {
        String urlPath = "http://www.baidu.com";
        try {
            CloseableHttpResponse response = HttpClientUtil.sendPost(urlPath, null);
            Assert.assertNotNull(response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testSendPostAsJson() {
        String urlPath = "http://www.baidu.com";
        try {
            CloseableHttpResponse response = HttpClientUtil.sendPostAsJson(urlPath, null);
            Assert.assertNotNull(response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
