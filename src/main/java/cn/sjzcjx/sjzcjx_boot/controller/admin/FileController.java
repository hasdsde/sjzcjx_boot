package cn.sjzcjx.sjzcjx_boot.controller.admin;

import cn.hutool.crypto.SecureUtil;
import cn.sjzcjx.sjzcjx_boot.config.AppException;
import cn.sjzcjx.sjzcjx_boot.config.JwtInterceptor;
import cn.sjzcjx.sjzcjx_boot.config.Result;
import cn.sjzcjx.sjzcjx_boot.controller.publics.PLogUtil;
import cn.sjzcjx.sjzcjx_boot.entity.Log;
import cn.sjzcjx.sjzcjx_boot.mapper.FileMapper;
import cn.sjzcjx.sjzcjx_boot.service.impl.FileServiceImpl;
import cn.sjzcjx.sjzcjx_boot.service.impl.LogServiceImpl;
import cn.sjzcjx.sjzcjx_boot.utils.FileUtil;
import cn.sjzcjx.sjzcjx_boot.utils.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/11/17 17:10
 **/

@RestController
@RequestMapping("/file")
@Api(tags = "admin-文件")
public class FileController {

    @Resource
    FileMapper fileMapper;
    @Resource
    FileServiceImpl fileService;

    @javax.annotation.Resource
    LogServiceImpl logService;

    @Value("${constom.filePath}")
    public String fileUploadPath;
    @Value("${constom.fileUrl}")
    public String fileUploadUrl;

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public Result Upload(@RequestParam MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null) {
            throw new AppException("文件名为空");
        }

        //获取名字
        Map<String, Object> splitName = FileUtil.SplitFileName(originalFilename);
        String name = (String) splitName.get("name");
        String type = (String) splitName.get("type");
        String size = getFixSize(file.getSize()); //默认MB

        File fileUploadPath = new File(this.fileUploadPath);
        if (!fileUploadPath.exists()) {
            fileUploadPath.mkdirs();
        }

        String day = new SimpleDateFormat("yy-MM-dd").format(new Date());
        String newFileName = name + "-" + day + "." + type;
        // 文件路径  UUID + 点 + 文件类型
        File uploadFile = new File(fileUploadPath + "/" + newFileName);

        String md5;
        String url;
        file.transferTo(uploadFile);
        md5 = SecureUtil.md5(uploadFile);
        cn.sjzcjx.sjzcjx_boot.entity.File dbFiles = getFileByMd5(md5);

        if (dbFiles != null) {
            url = dbFiles.getUrl();
        } else {
            url = fileUploadUrl + newFileName;

            // 给数据库添加记录
            cn.sjzcjx.sjzcjx_boot.entity.File file1 = new cn.sjzcjx.sjzcjx_boot.entity.File();
            file1.setSize(size);
            file1.setType(type);
            file1.setMd5(md5);
            file1.setUrl(url);
            file1.setName(newFileName);
            file1.setCreatedAt(LocalDateTime.now());
            fileService.save(file1);
        }
        return Result.OKWithData(url);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除")
    public Result Delete(@RequestParam("id") int id, ServletRequest servletRequest) {
        cn.sjzcjx.sjzcjx_boot.entity.File file = new cn.sjzcjx.sjzcjx_boot.entity.File();
        file.setId(id);
        file.setDeletedAt(LocalDateTime.now());
        fileService.updateById(file);

        //记录删除的用户
        PLogUtil pLogUtil = new PLogUtil(logService, new Log(null, "deleteFile", id, JwtUtil.getTokenInfo(JwtInterceptor.GlobalUserToken, "user_name").toString(), servletRequest.getRemoteAddr(), LocalDateTime.now()));
        pLogUtil.run();

        return Result.OK();
    }


    /**
     * @param md5 string
     * @return cn.sjzcjx.sjzcjx_boot.entity.File
     * @Description 查看数据库是否存在相同MD5文件
     * @author hasdsd
     * @Date 2023/11/17
     */
    public cn.sjzcjx.sjzcjx_boot.entity.File getFileByMd5(String md5) {
        return fileService.getOne(new QueryWrapper<cn.sjzcjx.sjzcjx_boot.entity.File>()
                .eq("md5", md5)
                .last("order by id desc limit 1")
        );
    }

    /**
     * @param size Long
     * @return java.lang.String
     * @Description 找到适合文件的大小单位
     * @author hasdsd
     * @Date 2023/11/17
     */
    public static String getFixSize(Long size) {
        String flag = "B";
        if (size > 1000) {
            size = size / 1024L;
            flag = "KB";
            if (size > 1000) {
                size = size / 1024L;
                flag = "MB";
                if (size > 1000) {
                    size = size / 1024L;
                    flag = "GB";
                }
            }
        }
        return size + flag;
    }
}
