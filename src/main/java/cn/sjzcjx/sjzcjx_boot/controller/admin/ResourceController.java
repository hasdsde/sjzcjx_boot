package cn.sjzcjx.sjzcjx_boot.controller.admin;

import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import cn.sjzcjx.sjzcjx_boot.config.Result;
import cn.sjzcjx.sjzcjx_boot.controller.publics.PLogUtil;
import cn.sjzcjx.sjzcjx_boot.entity.Log;
import cn.sjzcjx.sjzcjx_boot.entity.Resource;
import cn.sjzcjx.sjzcjx_boot.mapper.ResourceMapper;
import cn.sjzcjx.sjzcjx_boot.service.impl.LogServiceImpl;
import cn.sjzcjx.sjzcjx_boot.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
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


    @javax.annotation.Resource
    LogServiceImpl logService;

    @Autowired
    public ResourceMapper resourceMapper;

    @PostMapping("/save")
    @ApiOperation("新增")
    public Result Save(@RequestBody Resource resource, HttpServerRequest request, HttpServerResponse response, ServletRequest servletRequest) {
        resource.setCreatedAt(LocalDateTime.now());
        int id = resourceMapper.insert(resource);

        //保存一个日志
        PLogUtil pLogUtil = new PLogUtil(logService, new Log(null, "create_resource", id, JwtUtil.getTokenInfo(request, "userName").toString(), servletRequest.getRemoteAddr(), LocalDateTime.now()));
        pLogUtil.run();

        return Result.OK();
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除")
    public Result Delete(@RequestParam("id") int id) {
        Resource resource = new Resource();
        resource.setId(id);
        resource.setDeletedAt(LocalDateTime.now());
        resourceMapper.updateById(resource);
        return Result.OK();
    }

    @PutMapping("/update")
    @ApiOperation("更新")
    public Result Update(@RequestBody Resource resource) {
        resource.setUpdatedAt(LocalDateTime.now());
        resourceMapper.updateById(resource);
        return Result.OK();
    }
}
