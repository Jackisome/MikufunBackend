package com.devteam.mikufunbackend.entity;

import com.alibaba.fastjson.JSONObject;
import com.devteam.mikufunbackend.constant.Aria2Constant;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentHelper;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Jackisome
 * @date 2021/9/27
 */

@Data
//开启setter方法的链式调用
@Accessors(chain = true)
//无参构造方法
@NoArgsConstructor
@Slf4j
public class Aria2Entity {
    /**
     * id随机生成，也可以手动设置
     */
    private String id = UUID.randomUUID().toString();
    private String jsonrpc = "2.0";
    private String method;
    private String url;
    private List<Object> params = new ArrayList<>();

    /**
     * 添加下载参数
     * @return
     */
    public Aria2Entity addParam(Object obj) {
        params.add(obj);
        return this;
    }
}
