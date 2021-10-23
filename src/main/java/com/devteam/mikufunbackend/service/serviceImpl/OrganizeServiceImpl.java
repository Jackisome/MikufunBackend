package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.constant.RuntimeVariable;
import com.devteam.mikufunbackend.constant.TransferTypeEnum;
import com.devteam.mikufunbackend.entity.OrganizeV0;
import com.devteam.mikufunbackend.handle.OrganizeErrorException;
import com.devteam.mikufunbackend.service.serviceInterface.OrganizeService;
import com.devteam.mikufunbackend.util.ParamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/23
 */
@Service
public class OrganizeServiceImpl implements OrganizeService {

    Logger logger = LoggerFactory.getLogger(OrganizeService.class);

    @Override
    public OrganizeV0 getProperty() {
        OrganizeV0 organizeV0 = OrganizeV0.builder()
                .userPassword(RuntimeVariable.password)
                .visitorPasswords(new ArrayList<>(RuntimeVariable.visitorPasswords))
                .transferType(RuntimeVariable.transferType.getFormat())
                .fontSize(RuntimeVariable.fontSize)
                .fontColor(RuntimeVariable.fontColor)
                .fontBottom(RuntimeVariable.fontBottom)
                .defaultStatus(RuntimeVariable.defaultStatus)
                .regex(RuntimeVariable.regex)
                .build();
        logger.info("get property, organizeV0: {}", organizeV0);
        return organizeV0;
    }

    @Override
    public void setProperty(String userPassword, List<String> visitorPasswords, String transferType, String fontSize, String fontColor, String fontBottom, boolean defaultStatus, String regex) {
        if (ParamUtil.isNotEmpty(userPassword)) {
            if (!userPassword.equals(RuntimeVariable.password)) {
                RuntimeVariable.password = userPassword;
                RuntimeVariable.token = null;
                logger.info("set user password, password: {}", RuntimeVariable.password);
            }
        } else {
            throw new OrganizeErrorException("用户密码为空");
        }

        if (ParamUtil.isNotEmpty(visitorPasswords)) {
            if (visitorPasswords.size() > 3) {
                throw new OrganizeErrorException("访客密码数量大于3");
            } else {
                if (!RuntimeVariable.visitorPasswords.equals(new HashSet<>(visitorPasswords))) {
                    RuntimeVariable.visitorPasswords = new HashSet<>(visitorPasswords);
                    RuntimeVariable.visitorToken.forEach((visitorPassword, token) -> {
                        if (!visitorPasswords.contains(visitorPassword)) {
                            RuntimeVariable.visitorToken.remove(visitorPassword);
                        }
                    });
                    logger.info("set visitor passwords, visitorPasswords: {}", Arrays.toString(visitorPasswords.toArray()));
                }
            }
        } else {
            RuntimeVariable.visitorPasswords.clear();
            RuntimeVariable.visitorToken.clear();
        }

        if (ParamUtil.isNotEmpty(transferType)) {
            TransferTypeEnum transferTypeEnum = TransferTypeEnum.fromString(transferType);
            if (transferTypeEnum != null) {
                if (!transferTypeEnum.equals(RuntimeVariable.transferType)) {
                    RuntimeVariable.transferType = transferTypeEnum;
                    logger.info("set transferType, transferType: {}", RuntimeVariable.transferType.getFormat());
                }
            } else {
                throw new OrganizeErrorException(transferType + "转码方式不存在");
            }
        } else {
            throw new OrganizeErrorException("转码方式为空");
        }

        if (ParamUtil.isNotEmpty(fontSize)) {
            if (!fontSize.equals(RuntimeVariable.fontSize)) {
                RuntimeVariable.fontSize = fontSize;
                logger.info("set fontSize, fontSize: {}", RuntimeVariable.fontSize);
            }
        } else {
            throw new OrganizeErrorException("字幕大小为空");
        }

        if (ParamUtil.isNotEmpty(fontColor)) {
            if (!fontColor.equals(RuntimeVariable.fontColor)) {
                RuntimeVariable.fontColor = fontColor;
                logger.info("set fontColor, fontColor: {}", RuntimeVariable.fontColor);
            }
        } else {
            throw new OrganizeErrorException("字幕颜色为空");
        }

        if (ParamUtil.isNotEmpty(fontBottom)) {
            if (!fontBottom.equals(RuntimeVariable.fontBottom)) {
                RuntimeVariable.fontBottom = fontBottom;
                logger.info("set fontBottom, fontBottom: {}", RuntimeVariable.fontBottom);
            }
        } else {
            throw new OrganizeErrorException("字幕距播放器底部距离为空");
        }

        if (defaultStatus != RuntimeVariable.defaultStatus) {
            RuntimeVariable.defaultStatus = defaultStatus;
            logger.info("set defaultStatus, defaultStatus: {}", RuntimeVariable.defaultStatus);
        }

        if (!(regex == null && RuntimeVariable.regex == null)
                && !(RuntimeVariable.regex != null && RuntimeVariable.regex.equals(regex))) {
            RuntimeVariable.regex = regex;
            logger.info("set regex, regex: {}", RuntimeVariable.regex);
        }
    }
}
