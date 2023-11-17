package cn.sjzcjx.sjzcjx_boot.controller.publics;

import cn.sjzcjx.sjzcjx_boot.config.AppException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.applet.AppletIOException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

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
@Api(tags = "public-文件")
public class PFileController {

    
    @Value("${constom.filePath}")
    public String fileUploadPath;
    @Value("${constom.fileUrl}")
    public String fileUploadUrl;

    @GetMapping("/{fileName}")
    @ApiOperation("下载文件")
    public void Download(
            @PathVariable("fileName") String fileName,
            HttpServletResponse response
    ) throws IOException {

        if (fileName.isEmpty()) {
            throw new AppException("文件名为空");
        }

        File file = new File(fileUploadPath + fileName);

        if (file.exists()) {
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "utf-8"));

            byte[] buffer = new byte[1024];

            FileInputStream fis;
            BufferedInputStream bis;

            OutputStream os;
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                throw new AppletIOException("文件不存在");
            }
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
