package com.devteam.mikufunbackend.util;

import com.devteam.mikufunbackend.handle.ShellException;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Jackisome
 * @date 2021/10/5
 */
@Log4j2
public class ShellUtil {
    /**
     * 真正运行shell命令
     *
     * @param baseShellDir 运行命令所在目录（先切换到该目录后再运行命令）
     * @param cmd          命令数组
     * @param logFilePath  日志输出文件路径, 为空则直接输出到当前应用日志中，否则写入该文件
     * @return 进程退出码, 0: 成功, 其他:失败
     * @throws IOException 执行异常时抛出
     */
    public static int runShellCommandSync(String baseShellDir, String[] cmd,
                                          String logFilePath)
            throws IOException {
        long startTime = System.currentTimeMillis();
        int exitCode;
        ensureFilePathExists(logFilePath);
        String redirectLogInfoAndErrCmd = " >> " + logFilePath + " 2>&1 ";
        cmd = mergeTwoArr(cmd, redirectLogInfoAndErrCmd.split("\\s+"));
        log.info("【cli】receive new Command. baseDir: {}, cmd: {}, logFile:{}",
                baseShellDir, String.join(" ", cmd), logFilePath);
        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.directory(new File("./"));
        pb.redirectErrorStream(true);
        pb.redirectOutput(new File(logFilePath));
        Process p = pb.start();
        try {
            p.waitFor(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            log.error("进程被中断", e);
        } finally {
            exitCode = p.exitValue();
            log.info("【cli】process costTime:{}ms, exitCode:{}",
                    System.currentTimeMillis() - startTime, exitCode);
        }
        if (exitCode != 0) {
            throw new ShellException("shell execute error, ");
        }
        return exitCode;
    }

    /**
     * 使用 Runtime.exec() 运行shell
     */
    public static int runShellWithRuntime(String baseShellDir,
                                          String[] cmd) throws IOException {
        long startTime = System.currentTimeMillis();
        Process p = Runtime.getRuntime().exec(cmd, null, new File(baseShellDir));
        int exitCode;
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            log.error("进程被中断", e);
        } catch (Throwable e) {
            log.error("其他异常", e);
        } finally {
            exitCode = p.exitValue();
            log.info("【cli】process costTime:{}ms, exitCode:{}",
                    System.currentTimeMillis() - startTime, exitCode);
        }
        if (exitCode != 0) {
            throw new ShellException("shell execute error, ");
        }
        return exitCode;
    }

    /**
     * 确保文件夹存在
     *
     * @param filePath 文件路径
     */
    public static void ensureFilePathExists(String filePath) {
        File path = new File(filePath);
        if (path.exists()) {
            return;
        }
        File p = path.getParentFile();
        if (p.mkdirs()) {
            log.info("为文件创建目录: {} 成功", p.getPath());
            return;
        }
        log.warn("创建目录:{} 失败", p.getPath());
    }

    /**
     * 合并两个数组数据
     *
     * @param arrFirst  左边数组
     * @param arrAppend 要添加的数组
     * @return 合并后的数组
     */
    public static String[] mergeTwoArr(String[] arrFirst, String[] arrAppend) {
        String[] merged = new String[arrFirst.length + arrAppend.length];
        System.arraycopy(arrFirst, 0,
                merged, 0, arrFirst.length);
        System.arraycopy(arrAppend, 0,
                merged, arrFirst.length, arrAppend.length);
        return merged;
    }

}