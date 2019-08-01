package com.he.water.validate;

import com.he.water.entity.Result;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 捕获异常
 *
 * @author hzh
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 声明要捕获的异常
     */
    @ExceptionHandler(XException.class)
    @ResponseBody
    public Result defaultExceptionHandler(XException e) {
        System.out.println(e.getMsg());
        Result result = new Result();
        return result.error(e.getRet(), e.getMsg());

    }

    /**
     * 入参校验异常
     *
     * @param e
     * @return Result
     * @throws Exception
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result bindExceptionErrorHandler(MethodArgumentNotValidException e) {
        Result r = new Result();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuilder sb = new StringBuilder();
        if (!CollectionUtils.isEmpty(fieldErrors)) {
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage() + ",");
            }
        }
        String msg = sb.deleteCharAt(sb.lastIndexOf(",")).toString();
        System.out.println(msg);
        return r.error(2001, msg);
    }
}