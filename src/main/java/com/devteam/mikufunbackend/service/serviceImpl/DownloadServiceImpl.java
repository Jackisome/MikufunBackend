package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.entity.Aria2Entity;
import com.devteam.mikufunbackend.service.serviceInterface.DownLoadService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.devteam.mikufunbackend.entity.Aria2Entity.tellActive;

/**
 * @author Jackisome
 * @date 2021/9/27
 */
@Service
public class DownloadServiceImpl implements DownLoadService {

    @Value("${aria2.download.dir}")
    private String dir;

    @Override
    public boolean download(String link) throws Exception {
        String gid = UUID.randomUUID().toString();
        String downloadReturn = Aria2Entity.addUrl(link, dir, gid);
        if (StringUtils.isEmptyOrWhitespaceOnly(downloadReturn)) {
            throw new Exception();
        }
        return true;
    }
}
