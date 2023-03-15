package com.example.bulletchat.websocket;

import com.example.bulletchat.websocket.WebSocketService;
import io.netty.util.internal.ObjectUtil;
import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * com.example.bulletchat.util
 *
 * @author xiaozhiwei
 * 2023/3/13
 * 13:38
 */
public class WebSocketMap extends ConcurrentHashMap<String, ConcurrentHashMap<String, WebSocketService>> {
    /**
     * 第一层key为视频id,第二层key为userId
     */
    private static final ConcurrentHashMap<String, ConcurrentHashMap<String, WebSocketService>> map = new ConcurrentHashMap<>();

    /**
     * 先拿到视频id对应的map,再去设置
     */
    public static void put(WebSocketService webSocketService) {
        String videoId = webSocketService.getVideoId();
        ConcurrentHashMap<String, WebSocketService> mapByVideoId = map.get(videoId);
        if (ObjectUtils.isEmpty(mapByVideoId)) {
            mapByVideoId = new ConcurrentHashMap<>();
            map.put(videoId, mapByVideoId);
        }
        mapByVideoId.put(webSocketService.getUserId(), webSocketService);
    }

    public static ConcurrentHashMap<String, WebSocketService> getAllByVideoId(String videoId) {
        return map.get(videoId);
    }

    public static ConcurrentHashMap<String, WebSocketService> getAllByVideoId(WebSocketService webSocketService) {
        String videoId = webSocketService.getVideoId();
        return map.get(videoId);
    }

    public static void remove(WebSocketService webSocketService) {
        String videoId = webSocketService.getVideoId();
        ConcurrentHashMap<String, WebSocketService> mapByVideoId = map.getOrDefault(videoId, new ConcurrentHashMap<>());
        mapByVideoId.remove(webSocketService.getUserId());
        if (mapByVideoId.isEmpty()) {
            //如果空了,它自己也可以滚蛋了
            map.remove(videoId);
        }
    }

    /**
     * 根据视频id和用户id判断是否存在
     */
    public static boolean contains(WebSocketService webSocketService) {
        String videoId = webSocketService.getVideoId();
        return map.containsKey(videoId) && map.get(videoId).containsKey(webSocketService.getUserId());
    }

}
