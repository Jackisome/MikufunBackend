package com.devteam.mikufunbackend.constant;

/**
 * @author Jackisome
 * @date 2021/9/27
 */
public class Aria2Constant {
    public final static String METHOD_TELL_ACTIVE = "aria2.tellActive";
    public final static String METHOD_ADD_URI = "aria2.addUri";
    public final static String METHOD_GET_GLOBAL_STAT = "aria2.getGlobalStat";
    public final static String METHOD_TELL_STOPPED = "aria2.tellStopped";
    public final static String METHOD_TELL_WAITING = "aria2.tellWaiting";
    public final static String METHOD_REMOVE_DOWNLOAD_RESULT = "aria2.removeDownloadResult";
    public final static String[] PARAM_ARRAY_OF_FILED =
            new String[]{"totalLength", "completedLength", "files", "status", "errorCode", "gid"};
}
