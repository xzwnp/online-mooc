package com.example.bulletchat;

import com.example.commonutils.JwtEntity;
import com.example.commonutils.JwtUtil;

import java.util.UUID;

/**
 * com.example.bulletchat
 *
 * @author xiaozhiwei
 * 2023/3/23
 * 19:58
 */
public class TokenGenerator {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            JwtEntity jwtEntity = new JwtEntity();
            jwtEntity.setUserId(UUID.randomUUID().toString().replace("-", "").substring(0, 8));
            jwtEntity.setNickname("用户-" + jwtEntity.getUserId());
            System.out.println(JwtUtil.createJwtToken(jwtEntity));
        }

    }
}
