package cn.sjzcjx.sjzcjx_boot.controller.publics;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.sjzcjx.sjzcjx_boot.config.Result;
import cn.sjzcjx.sjzcjx_boot.mapper.FileMapper;
import cn.sjzcjx.sjzcjx_boot.service.impl.FileServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * <p>
 * 位于本地的文件 前端控制器
 * </p>
 *
 * @author hasd
 * @since 2023-11-16
 */
@RestController
@RequestMapping("/file")
@Api(tags = "文件")
public class PFileController {
    @Resource
    FileMapper fileMapper;
    @Resource
    FileServiceImpl fileService;

    @Value("${constom.filePath}")
    public String fileUploadPath;

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public Result Upload(@RequestParam MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();

        String fileExtension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String type = fileExtension;
        long size = file.getSize() / 1024 / 1024; //默认MB

        File fileUploadPath = new File(this.fileUploadPath);
        if (!fileUploadPath.exists()) {
            fileUploadPath.mkdirs();
        }

        // 定义文件唯一标识位
        String uuid = IdUtil.fastSimpleUUID();
        //UUID，这样方便后续下载
        String FileUUID = uuid + StrUtil.DOT + type;

        // 文件路径  UUID + 点 + 文件类型
        File uploadFile = new File(fileUploadPath + "/" + FileUUID);

        // 下面一大片操作目的是：遇到重复文件时，数据库增加数据，服务器储存不变
        String md5;
        String url;
        file.transferTo(uploadFile);
        md5 = SecureUtil.md5(uploadFile);
        cn.sjzcjx.sjzcjx_boot.entity.File dbFiles = getFileByMd5(md5);
        if (dbFiles != null) {
            url = dbFiles.getUrl();
            uploadFile.delete();
        } else {
            url = "http://127.0.0.1:8080/file/" + FileUUID;
        }
        // 给数据库添加记录
        cn.sjzcjx.sjzcjx_boot.entity.File file1 = new cn.sjzcjx.sjzcjx_boot.entity.File();
        file1.setSize(String.valueOf(size));
        file1.setType(type);
        file1.setMd5(md5);
        file1.setUrl(url);
        file1.setName(uuid);
        file1.setUpdatedAt(LocalDateTime.now());
        fileService.save(file1);
        return Result.OK();
    }

    public cn.sjzcjx.sjzcjx_boot.entity.File getFileByMd5(String md5) {
        return fileService.getOne(new QueryWrapper<cn.sjzcjx.sjzcjx_boot.entity.File>().eq("md5", md5));
    }
}
