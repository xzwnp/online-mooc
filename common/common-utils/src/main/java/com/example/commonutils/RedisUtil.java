package com.example.commonutils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * com.example.commonutils.vo
 *
 * @author xiaozhiwei
 * 2023/3/13
 * 15:31
 */
public class RedisUtil {
    public static final String SEPARATOR = "::";

    public static String buildKey(String... args) {
        if (args.length < 1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(args[0]);
        for (int i = 1; i < args.length; i++) {
            sb.append(SEPARATOR).append(args[i]);
        }
        return sb.toString();
    }

}
