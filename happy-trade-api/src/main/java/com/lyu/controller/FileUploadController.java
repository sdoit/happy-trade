package com.lyu.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.extra.ssh.Sftp;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.common.Constant;
import com.lyu.entity.UserResource;
import com.lyu.exception.UpdateFileException;
import com.lyu.service.UserResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @author LEE
 * @time 2023/1/28 20:28
 */
@Slf4j
@RestController()
@RequestMapping("/api/upload")
public class FileUploadController {
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
    private UserResourceService userResourceService;


    @Resource
    private DateTimeFormatter dateTimeFormatterUpload;

    @PostMapping("/image")
    public CommonResult<Object> updateImageUseSftp(MultipartFile file) throws UpdateFileException {
        long uid = StpUtil.getLoginIdAsLong();
        if (file == null) {
            return CommonResult.Result(CodeAndMessage.UNEXPECTED_ERROR, null);
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
        String filePath = path + "/" + Constant.NGINX_STATIC_SERVER_IMAGE_ADDR + "/" + uid + "/" + formatDate;
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
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }

    @PostMapping("/imageBatch")
    public CommonResult<Object> updateImageUseSftpBatch(MultipartFile[] files) throws UpdateFileException {
        long uid = StpUtil.getLoginIdAsLong();
        if (files == null || files.length == 0) {
            return CommonResult.Result(CodeAndMessage.UNEXPECTED_ERROR, null);
        }
        String suffix = "";
        LocalDateTime now = LocalDateTime.now();
        String formatDate = now.format(dateTimeFormatterUpload);
        String filePath = path + "/" + Constant.NGINX_STATIC_SERVER_IMAGE_ADDR + "/" + uid + "/" + formatDate;
        String[] fileNames = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file.getOriginalFilename() != null) {
                String[] splitFileName = file.getOriginalFilename().split("\\.");
                if (splitFileName.length > 1) {
                    suffix = splitFileName[splitFileName.length - 1];
                }
            }
            fileNames[i] = RandomUtil.randomString(20) + "." + suffix;
        }
        try (Sftp sftp = JschUtil.createSftp(host, port, username, password)) {
            if (!sftp.cd(filePath)) {
                sftp.mkDirs(filePath);
            }
            ChannelSftp channelSftp = sftp.getClient();
            ArrayList<UserResource> userResources = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                channelSftp.put(files[i].getInputStream(), filePath + "/" + fileNames[i], ChannelSftp.OVERWRITE);
                UserResource userResource = new UserResource();
                userResource.setDate(now);
                userResource.setFileName(fileNames[i]);
                userResource.setUid(uid);
                userResource.setType(Constant.FILE_TYPE_IMAGE);
                userResources.add(userResource);
            }
            //保存文件信息到数据库
            userResourceService.saveBatch(userResources);
        } catch (IOException |
                 SftpException e) {
            throw new UpdateFileException(CodeAndMessage.UNEXPECTED_ERROR.getCode(), CodeAndMessage.UNEXPECTED_ERROR.getMessage());
        }
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }
}
