package com.example.servicebase.exceptionhandler;

import com.example.commonutils.ExceptionUtil;
import com.example.commonutils.R;
import com.example.servicebase.exception.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * com.example.commonutils.exceptionhandler
 *
 * @author xzwnp
 * 2022/1/27
 * 14:14
 * Steps：
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //加上这个注解表示用于处理exception异常类
    @ExceptionHandler(Exception.class)
    @ResponseBody //指定返回json数据
    public R error(Exception e) {
        return R.error().message("执行了全局的异常处理");
    }

    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e) {
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
