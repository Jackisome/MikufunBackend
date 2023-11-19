package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.entity.FileVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/23
 */
public interface FreeDownloadService {
    boolean createFreeDownloadFile(String link) throws IOException;

    List<FileVO> getAllFreeDownloadFile();

    boolean saveFile(MultipartFile file);

    int saveFiles(MultipartFile[] files);
}
