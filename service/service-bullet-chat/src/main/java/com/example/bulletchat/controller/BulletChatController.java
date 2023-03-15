package com.example.bulletchat.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.example.bulletchat.entity.BulletChat;
import com.example.bulletchat.service.BulletChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 弹幕 前端控制器
 * </p>
 *
 * @author xiaozhiwei
 * @since 2023-03-13
 */
@RestController
@RequestMapping("/edubc/bullet-chat")
public class BulletChatController {
    @Autowired
    BulletChatService bulletChatService;

    @GetMapping("list")
    public R<List<BulletChat>> getBulletChatList(String videoId) {
        return R.ok(bulletChatService.getBulletsByVideoId(videoId));
    }
}

