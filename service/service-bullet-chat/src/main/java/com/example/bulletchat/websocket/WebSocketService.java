package com.example.bulletchat.websocket;

import com.example.bulletchat.config.MyRabbitBeanConfig;
import com.example.bulletchat.entity.BulletChat;
import com.example.bulletchat.entity.BulletChatRequest;
import com.example.bulletchat.util.OnLineCountUtil;
import com.example.commonutils.JsonUtil;
import com.example.commonutils.JwtEntity;
import com.example.commonutils.JwtUtil;
import com.example.commonutils.ResultCode;
import com.example.servicebase.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * com.example.bulletchat.service
 *
 * @author xiaozhiwei
 * 2023/3/12
 * 15:53
 * 说明:websocketService实际上为多例.
 * 由于加了@Component注解,容器启动时创建一个Websocket对象
 * 之后每次请求,都会由Spring new一个Websocket对象,但新new的Websocket对象不受容器管理,因此无法使用自动注入
 * 解决:借助ApplicationContextAware接口,利用容器时启动时创建的第一个WebSocket,设置静态的redisTemplate
 */
@Component
@ServerEndpoint("/edubc/bulletchat/{videoId}/{token}")
@Slf4j
public class WebSocketService implements ApplicationContextAware {
    private Session session;

    private String sessionId;
    private String videoId;
    private String userId;
    private static RedisTemplate<String, Object> redisTemplate;

    private static RabbitTemplate rabbitTemplate;

    @OnOpen
    public void openConnection(Session session, @PathParam("token") String token, @PathParam("videoId") String videoId) {
        JwtEntity userInfo = null;
        try {
            userInfo = JwtUtil.getUserInfo(token);
            this.userId = userInfo.getUserId();
            //游客也可以观看视频,建立连接
        } catch (Exception e) {
            this.userId = "temp::" + session.getId();
        }
        this.videoId = videoId;
        this.sessionId = session.getId();
        this.session = session;
        WebSocketMap.put(this);
        OnLineCountUtil.add(videoId, userId);
        log.info("用户sessionId:{} 连接成功。当前视频在线人数：{}", sessionId, OnLineCountUtil.getVideoOnLineCount(videoId, userId));
        sendMessage("连接建立成功!");
    }

    @OnClose
    public void closeConnection() {
        WebSocketMap.remove(this);
        OnLineCountUtil.remove(videoId, userId);
        log.info("用户sessionId:{} 退出。当前在线人数：{}", sessionId, OnLineCountUtil.getVideoOnLineCount(videoId, userId));
    }

    /**
     * 收到前端发来的弹幕时
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        BulletChatRequest request = JsonUtil.parse(message, BulletChatRequest.class);
        if (!StringUtils.hasLength(request.getContent()) || request.getTimestamp() == null) {
            return;
        }
        if (userId.startsWith("temp")) {
            sendMessage("游客不能发送弹幕!");
            return;
        }

        BulletChat bulletChat = new BulletChat(null, videoId, userId, request.getContent(),
                request.getTimestamp(), 0, new Date());
        //投递到消息队列
        rabbitTemplate.convertAndSend(MyRabbitBeanConfig.BULLET_SAVE_EXCHANGE, MyRabbitBeanConfig.BULLET_SAVE_BINDING, bulletChat);
        sendMessage("弹幕发送成功!");

    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("WebSocket::videoId{},userId:{},content:{}||发送失败!原因:{}", videoId, userId, message, e.getMessage());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        try {
            redisTemplate = (RedisTemplate<String, Object>) applicationContext.getBean("redisTemplate", RedisTemplate.class);
            rabbitTemplate = applicationContext.getBean("rabbitTemplate", RabbitTemplate.class);
        } catch (BeansException e) {
            throw new GlobalException(ResultCode.ERROR, "WebSocketService初始化失败!");
        }
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getVideoId() {
        return videoId;
    }
}
