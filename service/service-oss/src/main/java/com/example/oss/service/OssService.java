package com.example.oss.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * com.example.oss.service
 *
 * @author xzwnp
 * 2022/1/28
 * 19:07
 * Steps：
 */
public interface OssService {
    String uploadFile(MultipartFile file);
}
