package com.example.servicebase.log;

import com.example.commonutils.JwtEntity;
import com.example.servicebase.util.UserUtil;
import icu.ynu.log.entity.Operator;
import icu.ynu.log.operator.IOperatorGetService;
import org.springframework.stereotype.Component;

/**
 * com.example.serviceedu.log
 *
 * @author xiaozhiwei
 * 2023/3/22
 * 8:38
 */
@Component
public class OperatorGetService implements IOperatorGetService {

    @Override
    public Operator getOperator() {
        JwtEntity user = UserUtil.getUser();
        return new Operator(user.getUserId(), user.getNickname());
    }
}
