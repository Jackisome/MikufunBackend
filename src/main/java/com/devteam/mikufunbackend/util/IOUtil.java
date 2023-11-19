package com.devteam.mikufunbackend.util;

import com.devteam.mikufunbackend.constant.RuntimeVariable;
import com.devteam.mikufunbackend.entity.OrganizeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.ArrayList;

public class IOUtil {

    @Value("userImage.path")
    private static String userImagePath;

    static Logger logger = LoggerFactory.getLogger(IOUtil.class);

    /**
     * 更新配置文件
     */
    public static void writeConfig() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/docker/config.obj"));
            OrganizeVO organizeVO = OrganizeVO.builder()
                    .userImageUrl(userImagePath + RuntimeVariable.userImageName)
                    .userPassword(RuntimeVariable.password)
                    .visitorPasswords(new ArrayList<>(RuntimeVariable.visitorPasswords))
                    .transferType(RuntimeVariable.transferType.getFormat())
                    .fontSize(RuntimeVariable.fontSize)
                    .fontColor(RuntimeVariable.fontColor)
                    .fontBottom(RuntimeVariable.fontBottom)
                    .defaultStatus(RuntimeVariable.defaultStatus)
                    .regex(RuntimeVariable.regex)
                    .subscribeEmail(RuntimeVariable.subscribeEmail)
                    .danmakuTranslate(RuntimeVariable.danmakuTranslate)
                    .danmakuBottom(RuntimeVariable.danmakuBottom)
                    .animeSearchApi(RuntimeVariable.animeSearchApi)
                    .build();
            oos.writeObject(organizeVO);
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    /**
     * 读取配置文件
     * @return
     */
    public static OrganizeVO readConfig() {
        try {
            //如果文件还没有写入数据，写入系统当前配置值
            File file = new File("/docker/config.obj");
            if(file.createNewFile() || file.length() == 0){
                writeConfig();
            }
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/docker/config.obj"));
            OrganizeVO organizeVO = (OrganizeVO) ois.readObject();
            return organizeVO;
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }
}
