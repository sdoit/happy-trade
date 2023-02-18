package com.lyu.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author LEE
 * @time 2023/2/15 16:11
 */
public interface FileUploadService {
    /**
     * 上传文件到远程静态服务器
     *
     * @param file 要上传的文件
     * @param type 文件类型 image/video
     * @return 文件访问url
     */
    String uploadFile(MultipartFile file, String type);
}
