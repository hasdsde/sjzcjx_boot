package cn.sjzcjx.sjzcjx_boot.controller.publics;

import cn.sjzcjx.sjzcjx_boot.config.Result;
import cn.sjzcjx.sjzcjx_boot.entity.Resource;
import cn.sjzcjx.sjzcjx_boot.entity.Url;
import cn.sjzcjx.sjzcjx_boot.entity.dto.ResourceDto;
import cn.sjzcjx.sjzcjx_boot.mapper.ResourceMapper;
import cn.sjzcjx.sjzcjx_boot.mapper.UrlMapper;
import cn.sjzcjx.sjzcjx_boot.service.impl.ResourceServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/11/17 17:17
 **/

@RestController
@RequestMapping("/presource")
@Api(tags = "public-资源")
public class PResourceController {
    @Autowired
    ResourceMapper resourceMapper;
    @Autowired
    ResourceServiceImpl resourceService;

    @Autowired
    UrlMapper urlMapper;

    @GetMapping("/list")
    @ApiOperation("获取当前资源列表")
    @Cacheable(cacheNames = "resource-list")
    public Result List(
            @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "sortId", required = false) String sortId
    ) {
        LambdaQueryWrapper<Resource> wrapper = new LambdaQueryWrapper<Resource>()
                .like(name == null, Resource::getName, name)
                .like(name == null, Resource::getComment, name)
                .isNull(Resource::getDeletedAt)
                .eq(sortId == null, Resource::getSortId, sortId);

        Page<Resource> page = resourceMapper.selectPage(new Page<>(currentPage, pageSize), wrapper);
        return Result.OKWithPage(page);
    }

    @GetMapping("/list-all")
    @ApiOperation("模糊查询资源列表+Url")
    public Result GetALl(
            @RequestParam(value = "name", required = false) String name
    ) {
        List<ResourceDto> dtos = resourceMapper.selectOneWithUrl(name);
        return Result.OKWithData(dtos);
    }

    @GetMapping("/detail")
    @ApiOperation("查看url")
    @Cacheable(cacheNames = "resource-detail")
    public Result Detail(@RequestParam("id") int id) {
        List<Url> list = urlMapper.selectList(
                new LambdaQueryWrapper<Url>()
                        .eq(Url::getId, id)
                        .isNull(Url::getDeletedAt));
        return Result.OKWithData(list);
    }


}
