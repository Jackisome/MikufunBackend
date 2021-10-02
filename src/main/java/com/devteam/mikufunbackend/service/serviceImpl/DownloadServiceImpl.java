package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.entity.Aria2Entity;
import com.devteam.mikufunbackend.handle.Aria2Exception;
import com.devteam.mikufunbackend.service.serviceInterface.Aria2Service;
import com.devteam.mikufunbackend.service.serviceInterface.DownLoadService;
import com.mysql.cj.util.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

import static com.devteam.mikufunbackend.entity.Aria2Entity.tellActive;

/**
 * @author Jackisome
 * @date 2021/9/27
 */
@Service
public class DownloadServiceImpl implements DownLoadService {

    Logger logger = LoggerFactory.getLogger(DownloadServiceImpl.class);

    @Autowired
    private Aria2Service aria2Service;

    @Override
    public boolean download(String link) throws DocumentException, IOException, Aria2Exception {
        String gid = aria2Service.addUrl(link);
        System.out.println(gid);
        return true;
    }

    @Override
    public boolean remove(String gid) throws IOException {
        return aria2Service.removeDownloadingFile(gid);
    }
}
