package cn.sjzcjx.sjzcjx_boot.controller.publics;

import cn.sjzcjx.sjzcjx_boot.config.Result;
import cn.sjzcjx.sjzcjx_boot.entity.Url;
import cn.sjzcjx.sjzcjx_boot.mapper.UrlMapper;
import cn.sjzcjx.sjzcjx_boot.service.impl.UrlServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/11/17 13:51
 **/

@RestController
@RequestMapping("purl")
@Api(tags = "public-URL")
public class PUrlController {

    @Resource
    public UrlServiceImpl urlService;

    @Resource
    public UrlMapper urlMapper;

    @GetMapping("/list")
    @ApiOperation("根据资源id获取list")
    public Result getList(@RequestParam("id") int id) {
        List<Url> list = urlService.lambdaQuery().eq(Url::getResourceId, id).isNull(Url::getDeletedAt).list();
        return Result.OKWithData(list);
    }
}
