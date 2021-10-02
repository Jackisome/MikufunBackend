package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.handle.Aria2Exception;
import org.dom4j.DocumentException;

import java.io.IOException;

/**
 * @author Jackisome
 * @date 2021/9/27
 */
public interface DownLoadService {
    /**
     * 下载指定链接的数据到本地
     * @param link 下载链接
     * @return 下载请求是否完成创建
     * @throws Exception
     */
    boolean download(String link) throws DocumentException, IOException, Aria2Exception;

    boolean remove(String gid) throws IOException;
}
