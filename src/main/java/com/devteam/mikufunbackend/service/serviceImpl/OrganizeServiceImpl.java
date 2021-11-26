package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.constant.RuntimeVariable;
import com.devteam.mikufunbackend.constant.TransferTypeEnum;
import com.devteam.mikufunbackend.entity.OrganizeV0;
import com.devteam.mikufunbackend.handle.FileUploadException;
import com.devteam.mikufunbackend.handle.OrganizeErrorException;
import com.devteam.mikufunbackend.service.serviceInterface.OrganizeService;
import com.devteam.mikufunbackend.util.ParamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    @Value("${userImage.path}")
    private String userImagePath;

    Logger logger = LoggerFactory.getLogger(OrganizeService.class);

    @Override
    public OrganizeV0 getProperty() {
        OrganizeV0 organizeV0 = OrganizeV0.builder()
                .userImageUrl(this.userImagePath + RuntimeVariable.userImageName)
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
        logger.info("get property, organizeV0: {}", organizeV0);
        return organizeV0;
    }

    @Override
    public void setProperty(String userPassword, List<String> visitorPasswords, String transferType, String fontSize,
                            String fontColor, String fontBottom, boolean defaultStatus, String regex, String subscribeEmail,
                            boolean danmakuTranslate, String danmakuBottom, String animeSearchApi) {
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

        if (!ParamUtil.isNotEmpty(subscribeEmail) || ParamUtil.isLegalEmail(subscribeEmail)) {
            if ((ParamUtil.isNotEmpty(subscribeEmail) && !subscribeEmail.equals(RuntimeVariable.subscribeEmail)) ||
                    (!ParamUtil.isNotEmpty(subscribeEmail) && ParamUtil.isNotEmpty(RuntimeVariable.subscribeEmail))) {
                RuntimeVariable.subscribeEmail = subscribeEmail;
                logger.info("set subscribeEmail, subscribeEmail: {}", RuntimeVariable.subscribeEmail);
            }
        } else {
            throw new OrganizeErrorException("邮箱格式有误");
        }

        if (danmakuTranslate != RuntimeVariable.danmakuTranslate) {
            RuntimeVariable.danmakuTranslate = danmakuTranslate;
            logger.info("set danmakuTranslate, danmakuTranslate: {}", RuntimeVariable.danmakuTranslate);
        }

        if (ParamUtil.isNotEmpty(danmakuBottom)) {
            if (!danmakuBottom.equals(RuntimeVariable.danmakuBottom)) {
                RuntimeVariable.danmakuBottom = danmakuBottom;
                logger.info("set danmakuBottom, danmakuBottom: {}", RuntimeVariable.danmakuBottom);
            }
        } else {
            throw new OrganizeErrorException("弹幕距播放器底部距离为空");
        }

        if (ParamUtil.isNotEmpty(animeSearchApi)) {
            if (!animeSearchApi.equals(RuntimeVariable.animeSearchApi)) {
                RuntimeVariable.animeSearchApi = animeSearchApi;
                logger.info("set animeSearchApi, animeSearchApi: {}", RuntimeVariable.animeSearchApi);
            }
        } else {
            throw new OrganizeErrorException("资源搜索API为空");
        }
    }

    @Override
    public void setUserImage(MultipartFile image) {
        if (image.isEmpty()) {
            throw new FileUploadException("上传用户图像文件为空");
        }
        String fileName = image.getOriginalFilename();
        String filePath = userImagePath + fileName;
        File savedFile = new File(filePath);
        try {
            image.transferTo(savedFile);
            RuntimeVariable.userImageName = fileName;
            logger.info("upload user image finish, image name: {}", fileName);
        } catch (IOException e) {
            throw new FileUploadException("上传用户图片保存出现异常");
        }
    }
}
