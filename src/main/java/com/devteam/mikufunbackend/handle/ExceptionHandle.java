package com.devteam.mikufunbackend.handle;

import com.devteam.mikufunbackend.constant.ResponseEnum;
import com.devteam.mikufunbackend.util.Response;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Jackisome
 * @date 2021/9/26
 * 统一异常处理
 */
@ControllerAdvice
public class ExceptionHandle {
    public static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response exceptionHandle(Exception e) {
        // todo: 需细化
        if (e instanceof Exception) {
            return ResultUtil.fail(ResponseEnum.UNKNOWN_ERROR);
        } else {
            logger.error("unknown error: {}", e);
            return ResultUtil.fail(ResponseEnum.UNKNOWN_ERROR);
        }
    }

    @ExceptionHandler(value = Aria2Exception.class)
    @ResponseBody
    public Response aria2ExceptionHandle(Aria2Exception e) {
        logger.error(e.getMessage());
        return ResultUtil.fail(ResponseEnum.ARIA2_ERROR);
    }

    @ExceptionHandler(value = FileIdException.class)
    @ResponseBody
    public Response fileIdExceptionHandle(FileIdException e) {
        logger.error(e.getMessage());
        return ResultUtil.fail(ResponseEnum.FILEID_ERROR);
    }

    @ExceptionHandler(value = ShellException.class)
    @ResponseBody
    public Response shellExceptionHandle(ShellException e) {
        logger.error(e.getMessage());
        return ResultUtil.fail(ResponseEnum.SHELL_ERROR);
    }

    @ExceptionHandler(value = FavoriteException.class)
    @ResponseBody
    public Response favoriteExceptionHandle(FavoriteException e) {
        logger.error(e.getMessage());
        return ResultUtil.fail(ResponseEnum.FAVORITE_ERROR);
    }

    @ExceptionHandler(value = PasswordErrorException.class)
    @ResponseBody
    public Response passwordErrorExceptionHandle(PasswordErrorException e) {
        logger.error(e.getMessage());
        return ResultUtil.fail(ResponseEnum.LOGIN_ERROR);
    }

    @ExceptionHandler(value = FileErrorException.class)
    @ResponseBody
    public Response fileErrorExceptionHandle(FileErrorException e) {
        logger.error(e.getMessage());
        return ResultUtil.fail(ResponseEnum.FILE_ERROR);
    }

    @ExceptionHandler(value = DownloadedException.class)
    @ResponseBody
    public Response downloadedExceptionHandle(DownloadedException e) {
        logger.error(e.getMessage());
        return ResultUtil.fail(ResponseEnum.DOWNLOADED_EXCEPTION);
    }

    @ExceptionHandler(value = OrganizeErrorException.class)
    @ResponseBody
    public Response organizeErrorExceptionHandle(OrganizeErrorException e) {
        logger.error(e.getMessage());
        return ResultUtil.fail(ResponseEnum.ORGANIZE_ERROR);
    }

    @ExceptionHandler(value = TokenException.class)
    @ResponseBody
    public Response tokenExceptionHandle(TokenException e) {
        logger.error(e.getMessage());
        return ResultUtil.fail(ResponseEnum.TOKEN_ERROR);
    }
}
