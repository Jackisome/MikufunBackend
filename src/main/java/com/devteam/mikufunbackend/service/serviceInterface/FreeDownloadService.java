package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.entity.FileV0;

import java.io.IOException;
import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/23
 */
public interface FreeDownloadService {
    boolean createFreeDownloadFile(String link) throws IOException;

    List<FileV0> getAllFreeDownloadFile();
}
