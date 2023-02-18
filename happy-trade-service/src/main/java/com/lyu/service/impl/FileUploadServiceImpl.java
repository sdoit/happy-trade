package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.extra.ssh.Sftp;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.Constant;
import com.lyu.entity.UserResource;
import com.lyu.exception.GlobalException;
import com.lyu.exception.UpdateFileException;
import com.lyu.service.FileUploadService;
import com.lyu.service.UserResourceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author LEE
 * @time 2023/2/15 16:13
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Value("${nginx.static-server.ssh.host}")
    private String host;
    @Value("${nginx.static-server.ssh.port}")
    private Integer port;
    @Value("${nginx.static-server.ssh.username}")
    private String username;
    @Value("${nginx.static-server.ssh.password}")
    private String password;
    @Value("${nginx.static-server.ssh.path}")
    private String path;
    @Resource
    private DateTimeFormatter dateTimeFormatterUpload;
    @Resource
    private UserResourceService userResourceService;

    @Override
    public String uploadFile(MultipartFile file, String type) {
        long uid = StpUtil.getLoginIdAsLong();
        if (file == null) {
            throw new GlobalException(CodeAndMessage.UNEXPECTED_ERROR.getCode(), CodeAndMessage.UNEXPECTED_ERROR.getMessage());
        }
        String suffix = "";
        if (file.getOriginalFilename() != null) {
            String[] splitFileName = file.getOriginalFilename().split("\\.");
            if (splitFileName.length > 1) {
                suffix = splitFileName[splitFileName.length - 1];
            }
        }
        LocalDateTime now = LocalDateTime.now();
        String formatDate = now.format(dateTimeFormatterUpload);
        String filePath = path + "/" + type + "/" + uid + "/" + formatDate;
        String fileName = RandomUtil.randomString(20) + "." + suffix;
        try (Sftp sftp = JschUtil.createSftp(host, port, username, password)) {
            if (!sftp.cd(filePath)) {
                sftp.mkDirs(filePath);
            }
            ChannelSftp channelSftp = sftp.getClient();
            channelSftp.put(file.getInputStream(), filePath + "/" + fileName, ChannelSftp.OVERWRITE);
            //保存文件信息到数据库
            UserResource userResource = new UserResource();
            userResource.setDate(now);
            userResource.setFileName(fileName);
            userResource.setUid(uid);
            userResource.setType(Constant.FILE_TYPE_IMAGE);
            userResourceService.saveResource(userResource);
        } catch (IOException | SftpException e) {
            throw new UpdateFileException(CodeAndMessage.UNEXPECTED_ERROR.getCode(), CodeAndMessage.UNEXPECTED_ERROR.getMessage());
        }
        return type + "/" + uid + "/" + formatDate + "/" + fileName;
    }
}
