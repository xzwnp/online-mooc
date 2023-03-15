package com.example.bulletchat.entity;

import lombok.Data;

/**
 * com.example.bulletchat.entity
 *
 * @author xiaozhiwei
 * 2023/3/13
 * 20:10
 */
@Data
public class BulletChatRequest {
    private Integer timestamp;
    private String content;
}
