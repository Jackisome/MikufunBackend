package com.devteam.mikufunbackend.config;

import com.devteam.mikufunbackend.service.serviceInterface.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

/**
 * @author Jackisome
 * @date 2021/10/3
 */
@Configuration
@EnableScheduling
public class ScheduledTask {
    @Autowired
    private TransferService transferService;

    @Scheduled(fixedDelay = 60000)
    public void transfer() throws IOException, InterruptedException {
        transferService.transfer();
    }

    @Scheduled(fixedDelay = 60000)
    public void cleanSourceFiles() throws IOException, InterruptedException {
        transferService.cleanSourceFiles();
    }
}
