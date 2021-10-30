package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.entity.FileV0;
import com.devteam.mikufunbackend.handle.FileUploadException;
import com.devteam.mikufunbackend.handle.ParameterErrorException;
import com.devteam.mikufunbackend.service.serviceInterface.Aria2Service;
import com.devteam.mikufunbackend.service.serviceInterface.FreeDownloadService;
import com.devteam.mikufunbackend.service.serviceInterface.LocalServerService;
import com.devteam.mikufunbackend.util.ParamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/23
 */
@Service
public class FreeDownloadServiceImpl implements FreeDownloadService {

    Logger logger = LoggerFactory.getLogger(FreeDownloadService.class);

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

    @Override
    public boolean saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileUploadException("上传文件为空");
        }
        String fileName = file.getOriginalFilename();
        String filePath = freeDownloadPath + fileName;
        File savedFile = new File(filePath);
        try {
            file.transferTo(savedFile);
            logger.info("upload file finish, fileName: {}", fileName);
        } catch (IOException e) {
            throw new FileUploadException("上传文件保存出现异常");
        }
        return true;
    }

    @Override
    public int saveFiles(MultipartFile[] files) {
        int transferFileCount = 0;
        int totalFileCount = files.length;
        for (MultipartFile file : files) {
            if (saveFile(file)) {
                transferFileCount++;
            }
        }
        return totalFileCount;
    }
}
