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
            new String[]{"totalLength", "completedLength", "files", "downloadSpeed", "uploadSpeed", "status", "gid"};
    public final static String METHOD_PAUSE = "aria2.pause";
    public final static String METHOD_PAUSE_ALL = "aria2.pauseAll";
    public final static String METHOD_UNPAUSE = "aria2.unpause";
    public final static String METHOD_UNPAUSE_ALL = "aria2.unpauseAll";

    public enum downloadStatus {
        ACTIVE("active"),
        WAITING("waiting"),
        PAUSED("paused"),
        ERROR("error"),
        COMPLETE("complete"),
        REMOVED("removed");

        private final String description;

        private downloadStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
