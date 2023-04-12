package com.example.servicebase.exception;

import com.example.commonutils.ResultCode;
import lombok.Getter;
import lombok.Setter;

/**
 * com.example.servicebase.exception
 *
 * @author xiaozhiwei
 * 2023/4/12
 * 18:13
 */
@Getter
@Setter
public class ServiceBusyException extends GlobalException {
    public ServiceBusyException(String msg) {
        super(ResultCode.BUSY, msg);
    }

    public ServiceBusyException() {
        super(ResultCode.FALLBACK, "服务繁忙!请稍后再试!");
    }
}
