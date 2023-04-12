package com.example.servicebase.exception;

import com.example.commonutils.ResultCode;
import lombok.Data;
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
public class FallBackException extends GlobalException {
    public FallBackException(String msg) {
        super(ResultCode.FALLBACK, msg);
    }

    public FallBackException() {
        super(ResultCode.FALLBACK, "服务调用失败!");
    }
}
