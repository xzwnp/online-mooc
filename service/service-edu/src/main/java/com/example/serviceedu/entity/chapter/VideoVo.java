package com.example.serviceedu.entity.chapter;

import lombok.Data;

@Data
public class VideoVo {

    private String id;

    private String title;
    //该视频在阿里云视频点播上的id号
    private String videoSourceId;
    private boolean free;
}
