package com.example.servicebase.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.example.servicebase.exception
 *
 * @author xzwnp
 * 2022/1/27
 * 15:09
 * Steps：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalException extends RuntimeException{
    protected int code;
    protected String msg;

}
