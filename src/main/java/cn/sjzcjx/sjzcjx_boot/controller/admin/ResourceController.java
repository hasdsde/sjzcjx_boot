package cn.sjzcjx.sjzcjx_boot.controller.admin;

import cn.sjzcjx.sjzcjx_boot.config.Result;
import cn.sjzcjx.sjzcjx_boot.entity.Resource;
import cn.sjzcjx.sjzcjx_boot.mapper.ResourceMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/11/17 22:01
 **/

@RestController
@RequestMapping("/resource")
@Api(tags = "admin-资源")
public class ResourceController {

    @Autowired
    public ResourceMapper resourceMapper;

    @PostMapping("/save")
    @ApiOperation("新增资源")
    public Result Save(@RequestBody Resource resource) {
        resource.setCreatedAt(LocalDateTime.now());
        resourceMapper.insert(resource);
        return Result.OK();
    }
}
