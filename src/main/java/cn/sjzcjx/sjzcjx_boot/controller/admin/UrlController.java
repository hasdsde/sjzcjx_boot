package cn.sjzcjx.sjzcjx_boot.controller.admin;

import cn.sjzcjx.sjzcjx_boot.config.Result;
import cn.sjzcjx.sjzcjx_boot.entity.Url;
import cn.sjzcjx.sjzcjx_boot.mapper.UrlMapper;
import cn.sjzcjx.sjzcjx_boot.service.impl.UrlServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/11/17 14:07
 **/

@RestController
@RequestMapping("/url")
@Api(tags = "admin-URL")
public class UrlController {
    @Resource
    public UrlServiceImpl urlService;

    @Resource
    public UrlMapper urlMapper;

    @GetMapping("/list")
    @ApiOperation("获取")
    public Result getUrl(
            @RequestParam(value = "currentPage") int currentPage,
            @RequestParam(value = "pageSize") int pageSize
    ) {
        Page<Url> selectedPage = urlMapper.selectPage(new Page<>(currentPage, pageSize), new QueryWrapper<Url>());
        return Result.OKWithPage(selectedPage);
    }

    @PostMapping("/create")
    @ApiOperation("创建")
    public Result CreateUrl(@RequestBody Url url) {
        url.setCreatedAt(LocalDateTime.now());
        urlService.save(url);
        return Result.OK();
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除")
    public Result Delete(@RequestParam("id") int id) {
        Url url = new Url();
        url.setId(id);
        url.setDeletedAt(LocalDateTime.now());
        urlService.updateById(url);
        return Result.OK();
    }


}
