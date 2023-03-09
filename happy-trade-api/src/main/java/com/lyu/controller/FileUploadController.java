package com.lyu.controller;

import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.common.Constant;
import com.lyu.exception.UpdateFileException;
import com.lyu.service.FileUploadService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author LEE
 * @time 2023/1/28 20:28
 */
@Slf4j
@Validated

@ApiOperation("文件上传接口")
@RestController()
@RequestMapping("/api/upload")
public class FileUploadController {
    @Resource
    private FileUploadService fileUploadService;

    @PostMapping("/image")
    public CommonResult<String> uploadImageUseSftp(@NotNull MultipartFile file) throws UpdateFileException {
        return CommonResult.Result(CodeAndMessage.SUCCESS, fileUploadService.uploadFile(file, Constant.NGINX_STATIC_SERVER_IMAGE_ADDR));
    }

//    @PostMapping("/imageBatch")
//    public CommonResult<Object> uploadImageUseSftpBatch(MultipartFile[] files) throws UpdateFileException {
//        long uid = StpUtil.getLoginIdAsLong();
//        if (files == null || files.length == 0) {
//            return CommonResult.Result(CodeAndMessage.UNEXPECTED_ERROR, null);
//        }
//        String suffix = "";
//        LocalDateTime now = LocalDateTime.now();
//        String formatDate = now.format(dateTimeFormatterUpload);
//        String filePath = path + "/" + Constant.NGINX_STATIC_SERVER_IMAGE_ADDR + "/" + uid + "/" + formatDate;
//        String[] fileNames = new String[files.length];
//        for (int i = 0; i < files.length; i++) {
//            MultipartFile file = files[i];
//            if (file.getOriginalFilename() != null) {
//                String[] splitFileName = file.getOriginalFilename().split("\\.");
//                if (splitFileName.length > 1) {
//                    suffix = splitFileName[splitFileName.length - 1];
//                }
//            }
//            fileNames[i] = RandomUtil.randomString(20) + "." + suffix;
//        }
//        try (Sftp sftp = JschUtil.createSftp(host, port, username, password)) {
//            if (!sftp.cd(filePath)) {
//                sftp.mkDirs(filePath);
//            }
//            ChannelSftp channelSftp = sftp.getClient();
//            ArrayList<UserResource> userResources = new ArrayList<>();
//            for (int i = 0; i < files.length; i++) {
//                channelSftp.put(files[i].getInputStream(), filePath + "/" + fileNames[i], ChannelSftp.OVERWRITE);
//                UserResource userResource = new UserResource();
//                userResource.setDate(now);
//                userResource.setFileName(fileNames[i]);
//                userResource.setUid(uid);
//                userResource.setType(Constant.FILE_TYPE_IMAGE);
//                userResources.add(userResource);
//            }
//            //保存文件信息到数据库
//            userResourceService.saveBatch(userResources);
//        } catch (IOException |
//                 SftpException e) {
//            throw new UpdateFileException(CodeAndMessage.UNEXPECTED_ERROR.getCode(), CodeAndMessage.UNEXPECTED_ERROR.getMessage());
//        }
//        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
//    }


    @PostMapping("/video")
    public CommonResult<String> uploadVideoUseSftp(@NotNull MultipartFile file) throws UpdateFileException {
        return CommonResult.Result(CodeAndMessage.SUCCESS, fileUploadService.uploadFile(file, Constant.NGINX_STATIC_SERVER_VIDEO_ADDR));
    }
}
