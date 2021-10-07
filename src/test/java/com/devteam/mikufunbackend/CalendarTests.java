package com.devteam.mikufunbackend;

import com.devteam.mikufunbackend.constant.AnimeTypeEnum;
import com.devteam.mikufunbackend.controller.CalendarController;
import com.devteam.mikufunbackend.controller.DownloadController;
import com.devteam.mikufunbackend.util.HttpClientUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wooyuwen
 * @date 2021-10-06
 */
public class CalendarTests {

    @Test
    public void testCalendarGet() {
        try {
            CalendarController testc = new CalendarController();
            Assert.assertNotNull(testc.calendar(AnimeTypeEnum.anime,1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
