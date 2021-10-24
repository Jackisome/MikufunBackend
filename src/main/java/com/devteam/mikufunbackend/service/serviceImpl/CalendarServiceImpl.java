package com.devteam.mikufunbackend.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.devteam.mikufunbackend.constant.AnimeTypeEnum;
import com.devteam.mikufunbackend.entity.CalendarInfoV0;
import com.devteam.mikufunbackend.entity.DownloadStatusV0;
import com.devteam.mikufunbackend.service.serviceInterface.CalendarService;
import com.devteam.mikufunbackend.util.HttpClientUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Wooyuwen
 * @date 2021/10/04
 */
@Service
public class CalendarServiceImpl implements CalendarService {

    Logger logger = LoggerFactory.getLogger(CalendarServiceImpl.class);

    @Override
    public List<CalendarInfoV0> calendar(AnimeTypeEnum type, int week) {

        List<CalendarInfoV0> data = new ArrayList<>();

        String url1="https://api.acplay.net/api/v2/homepage?filterAdultContent=true";
        String url2="https://api.bgm.tv/calendar";
        logger.info("pos1");
        try {
            CloseableHttpClient client1 = HttpClients.createDefault(),client2 = HttpClients.createDefault();
            HttpGet get1 = new HttpGet(url1),get2 = new HttpGet(url2);
            HttpResponse response1 = client1.execute(get1),response2 = client2.execute(get2);
            HttpEntity entity1 = response1.getEntity();
            HttpEntity entity2 = response2.getEntity();

            if (entity1 != null && entity2 != null) {
                parseJsonGet(data,EntityUtils.toString(entity1),EntityUtils.toString(entity2),week);
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }

        logger.info("get calendar infomation, info: {}", data);
        return data;
    }

    public void parseJsonGet(List<CalendarInfoV0> data,String jsonString1,String jsonString2,int week){
        try {
            //dandanPlay数据
            List<JSONObject> dataList1 = JSON.parseArray((JSONObject.parseObject(jsonString1))
                    .getJSONArray("shinBangumiList").toJSONString(), JSONObject.class);
            int week1 = week == 7 ? 0 : week;
            //Bangumi数据
            List<JSONObject> dataList2 = JSON.parseArray(jsonString2, JSONObject.class);

            //数据采用dandanplay,bangumi若有对应番剧则采用bangumi上映日期airDate
            dataList1.forEach(k -> {
                if (k.getInteger("airDay") == week1) {
                    CalendarInfoV0 calendarInfoV0 = CalendarInfoV0.builder()
                            .resourceId(String.valueOf(k.getInteger("animeId")))
                            .resourceName(k.getString("animeTitle"))
                            .airDate("未知")
                            .rating(k.getDouble("rating"))
                            .imageUrl(k.getString("imageUrl"))
                            .build();
                    dataList2.forEach(i -> {
                        if (i.getJSONObject("weekday").getInteger("id") == week) {
                            List<JSONObject> items = JSON.parseArray(i.getJSONArray("items").toJSONString(), JSONObject.class);
                            items.forEach(j -> {
                                if (j.getString("name_cn").equals(k.getString("animeTitle"))) {
                                    calendarInfoV0.setAirDate(j.getString("air_date"));
                                }
                            });
                        }
                    });
                    data.add(calendarInfoV0);
                }
            });
        }catch (Exception e){
            logger.error(e.toString());
        }
    }
}
