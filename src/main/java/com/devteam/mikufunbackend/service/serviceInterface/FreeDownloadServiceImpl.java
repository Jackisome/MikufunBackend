package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.entity.FileV0;
import com.devteam.mikufunbackend.handle.ParameterErrorException;
import com.devteam.mikufunbackend.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/23
 */
@Component
public class FreeDownloadServiceImpl implements FreeDownloadService {

    @Autowired
    private LocalServerService localServerService;

    @Autowired
    private Aria2Service aria2Service;

    @Value("${freedownload.path}")
    private String freeDownloadPath;

    @Override
    public boolean createFreeDownloadFile(String link) throws IOException {
        if (ParamUtil.isNotEmpty(link)) {
            return aria2Service.addUrl(link, freeDownloadPath);
        } else {
            throw new ParameterErrorException("下载链接为空");
        }
    }

    @Override
    public List<FileV0> getAllFreeDownloadFile() {
        return localServerService.getFileInformation(freeDownloadPath);
    }
}
