package com.devteam.mikufunbackend.handle;

/**
 * @author Jackisome
 * @date 2021/10/23
 */
public class DownloadedException extends RuntimeException {
    public DownloadedException(String info) {
        super(info);
    }
}
