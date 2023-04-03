package com.example.servicebase.exceptionhandler;

import com.example.commonutils.R;
import com.example.servicebase.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public R error(GlobalException e) {
        log.error("错误信息:" + e.getMsg(), e);
        return R.error().code(e.getCode()).message(e.getMsg());
    }


    @ExceptionHandler(CannotCreateTransactionException.class)
    @ResponseBody //指定返回json数据
    public R error(CannotCreateTransactionException e) {
        log.error("数据库连接超时:", e);
        return R.error().message("数据库连接超时!");
    }


    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody //指定返回json数据
    public R error(AuthenticationException e) {
        return R.error().code(30001).message("token过期或无效,请重新登录!");
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody //指定返回json数据
    public R error(AuthorizationException e) {
        log.error("无权限:", e);
        return R.error().code(30001).message("您没有权限!");
    }

    //加上这个注解表示用于处理exception异常类
    @ExceptionHandler(Exception.class)
    @ResponseBody //指定返回json数据
    public R error(Exception e) {
        log.error("服务器出错:", e);
        return R.error().message("服务器出错,请联系管理员");
    }
}
