package com.example.vod.service;

import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * com.example.vod.service
 *
 * @author xzwnp
 * 2022/3/10
 * 19:02
 * Steps：
 */

public interface VideoService {
    boolean testUploadVideo(String title, String fileName);

    /**
     * 获取播放地址
     */
    GetPlayInfoResponse getVideoPlayInfo(String videoId) throws Exception;

    /**
     * 获取播放凭证,有的视频被加密了,即使有了播放地址也无法播放
     */
    String getVideoPlayAuth(String videoId);

    /**
     *
     * @return 视频id,如果失败则为空
     */
    String uploadVideoPlay(MultipartFile file);

    void removeVideo(String videoId);
}
