package com.example.bulletchat.service;

import com.example.bulletchat.entity.BulletChat;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 弹幕 服务类
 * </p>
 *
 * @author xiaozhiwei
 * @since 2023-03-13
 */
public interface BulletChatService extends IService<BulletChat> {
    /**
     * 新增弹幕
     */
    void saveBullet(BulletChat bulletChat);

    /**
     * 推送弹幕
     */
    void pushBullet(BulletChat bulletChat);

    /**
     * 获取弹幕
     *
     * @return
     */
    List<BulletChat> getBulletsByVideoId(String videoId);
}
