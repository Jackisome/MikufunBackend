package com.devteam.mikufunbackend.handle;

import com.devteam.mikufunbackend.constant.ResponseEnum;
import com.devteam.mikufunbackend.util.Response;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

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

    @ExceptionHandler(value = FileUploadException.class)
    @ResponseBody
    public Response fileUploadExceptionHandle(FileUploadException e) {
        logger.error(e.getMessage());
        return ResultUtil.fail(ResponseEnum.FILE_UPLOAD_ERROR);
    }

    @ExceptionHandler(value = ParameterErrorException.class)
    @ResponseBody
    public Response parameterErrorException(ParameterErrorException e) {
        logger.error(e.getMessage());
        return ResultUtil.fail(ResponseEnum.PARAMETER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public Response methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
        logger.error(e.getMessage());
        return ResultUtil.fail(HttpStatus.BAD_REQUEST.value(), String.format("请求参数类型错误: %s", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Response methodArgumentNotValidExceptionExceptionHandler(MethodArgumentNotValidException e) {
        logger.error(e.getMessage());
        FieldError fieldError = e.getBindingResult().getFieldError();
        assert fieldError != null;
        return ResultUtil.fail(HttpStatus.BAD_REQUEST.value(), String.format("请求参数不合法: %s",
                fieldError.getDefaultMessage()));
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public Response constraintViolationExceptionHandler(ConstraintViolationException e) {
        logger.error(e.getMessage());
        ConstraintViolation<?> constraintViolation = e.getConstraintViolations().iterator().next();
        return ResultUtil.fail(HttpStatus.BAD_REQUEST.value(), String.format("请求参数不正确:%s", constraintViolation.getMessage()));
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public Response missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        logger.error(e.getMessage());
        return ResultUtil.fail(HttpStatus.BAD_REQUEST.value(), String.format("请求参数缺失:%s", e.getParameterName()));
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Response bindExceptionHandler(BindException e) {
        logger.error(e.getMessage());
        FieldError fieldError = e.getFieldError();
        assert fieldError != null;
        return ResultUtil.fail(HttpStatus.BAD_REQUEST.value(), String.format("请求参数不正确:%s", fieldError.getDefaultMessage()));
    }

}
