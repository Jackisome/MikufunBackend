package com.devteam.mikufunbackend.config;

import com.devteam.mikufunbackend.service.serviceInterface.AutoDownloadService;
import com.devteam.mikufunbackend.service.serviceInterface.TransferService;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.text.ParseException;

/**
 * @author Jackisome
 * @date 2021/10/3
 */
@Configuration
@EnableScheduling
public class ScheduledTask {
    @Autowired
    private TransferService transferService;

    @Autowired
    private AutoDownloadService autoDownloadService;

    @Scheduled(fixedDelay = 60000)
    public void transfer() throws IOException, InterruptedException {
        transferService.transfer();
    }

    @Scheduled(fixedDelay = 60000)
    public void cleanSourceFiles() throws IOException, InterruptedException {
        transferService.cleanSourceFiles();
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void findDownloadableResource() throws DocumentException, ParseException, IOException, InterruptedException {
        autoDownloadService.findDownloadableResource();
    }
}
