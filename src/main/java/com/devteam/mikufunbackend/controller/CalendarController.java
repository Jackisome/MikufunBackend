package com.devteam.mikufunbackend.controller;

import com.devteam.mikufunbackend.constant.AnimeTypeEnum;
import com.devteam.mikufunbackend.service.serviceImpl.CalendarServiceImpl;
import com.devteam.mikufunbackend.service.serviceInterface.CalendarService;
import com.devteam.mikufunbackend.util.Response;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Jackisome
 * @date 2021/9/26
 */
@RestController
@RequestMapping("/api/v1/calendar")
public class CalendarController {
    @Autowired
    CalendarService calendarService;

    @GetMapping("/type/{type}/week/{week}")
    public Response calendar(@PathVariable("type") AnimeTypeEnum type, @PathVariable("week") int week) throws Exception{
        Map<String,Object> data=ResultUtil.getData();
        data.put("resources",calendarService.calendar(type,week));
        return ResultUtil.success(data);
    }
}
