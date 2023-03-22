package com.example.serviceedu.log;

import com.example.commonutils.SnowFlakeUtil;
import icu.ynu.log.annotation.LogRecordFunction;
import icu.ynu.log.annotation.LogRecordFunctionBean;

/**
 * com.example.serviceedu.log
 *
 * @author xiaozhiwei
 * 2023/3/22
 * 9:04
 */
@LogRecordFunctionBean
public class BizIdGenerator {
    private static SnowFlakeUtil snowFlakeGenerator = new SnowFlakeUtil(1, 1);

    @LogRecordFunction("getBizId")
    public static String generateBizId() {
        return String.valueOf(snowFlakeGenerator.getNextId());
    }


}
