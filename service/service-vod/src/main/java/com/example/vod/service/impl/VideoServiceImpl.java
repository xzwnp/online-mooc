package com.example.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.utils.StringUtils;
import com.aliyuncs.vod.model.v20170321.*;
import com.example.servicebase.exception.GlobalException;
import com.example.vod.service.VideoService;
import com.example.vod.utils.AliyunVodSDKUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * com.example.vod.service.impl
 *
 * @author xzwnp
 * 2022/3/10
 * 19:03
 * Steps：
 */
@Service
@Log4j2
public class VideoServiceImpl implements VideoService {
    @Override
    public boolean testUploadVideo(String title, String fileName) {
        String accessKeyId = AliyunVodSDKUtils.ACCESS_KEY_ID;
        String accessKeySecret = AliyunVodSDKUtils.ACCESS_KEY_SECRET;
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        return true;
    }

    @Override
    public GetPlayInfoResponse getVideoPlayInfo(String videoId) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(videoId);
        DefaultAcsClient client = AliyunVodSDKUtils.getClient();
        return client.getAcsResponse(request);
    }

    @Override
    public String getVideoPlayAuth(String videoId) {
        DefaultAcsClient client = null;
        try {
            client = AliyunVodSDKUtils.getClient();
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            return response.getPlayAuth();
        } catch (ClientException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String uploadVideoPlay(MultipartFile file) {

        //1.音视频上传-本地文件上传
        try {
            //视频标题(必选)
            String title = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            //本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
            //文件名必须包含扩展名
            //本地文件上传
            UploadStreamRequest request = new UploadStreamRequest(AliyunVodSDKUtils.ACCESS_KEY_ID, AliyunVodSDKUtils.ACCESS_KEY_SECRET, title, title, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                log.warn(errorMessage);
                if (StringUtils.isEmpty(videoId)) {
                    throw new GlobalException(20001, errorMessage);
                }
            }
            return videoId;
        } catch (IOException e) {
            throw new GlobalException(20001, "vod 服务上传失败,无法读取文件流");
        }
    }

    @Override
    public void removeVideo(String videoId) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.getClient();
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);

            DeleteVideoResponse response = client.getAcsResponse(request);

            System.out.print("RequestId = " + response.getRequestId() + "\n");

        } catch (ClientException e) {
            throw new GlobalException(20001, "视频删除失败");
        }
    }
}
