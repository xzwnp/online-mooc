package com.example.serviceedu;

import com.example.commonutils.JwtEntity;
import com.example.commonutils.JwtUtil;
import org.junit.jupiter.api.Test;

/**
 * com.example.serviceedu
 *
 * @author xiaozhiwei
 * 2023/3/13
 * 21:29
 */
public class JwtTest {
    @Test
    public void test() {
        JwtEntity jwtEntity = new JwtEntity();
        for (int i = 0; i < 10; i++) {
            jwtEntity.setUserId("12345" + i);
            jwtEntity.setNickname("用户" + i);
            System.out.println(JwtUtil.createJwtToken(jwtEntity).replace("Bearer ", ""));
        }
    }
}
