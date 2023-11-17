package cn.sjzcjx.sjzcjx_boot.controller.admin;

import cn.sjzcjx.sjzcjx_boot.config.Result;
import cn.sjzcjx.sjzcjx_boot.entity.Sort;
import cn.sjzcjx.sjzcjx_boot.mapper.SortMapper;
import cn.sjzcjx.sjzcjx_boot.service.impl.SortServiceImpl;
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
 * @since : 2023/11/17 13:21
 **/

@RestController
@RequestMapping("/sort")
@Api(tags = "admin-分类")
public class SortController {

    @Resource
    public SortMapper sortMapper;

    @Resource
    public SortServiceImpl sortService;


    @GetMapping("/list")
    @ApiOperation("列表")
    public Result GetList(
            @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize
    ) {
        Page<Sort> page = sortMapper.selectPage(new Page<>(currentPage, pageSize), new QueryWrapper<Sort>());
        return Result.OKWithPage(page);
    }

    @PostMapping("/create")
    @ApiOperation("创建")
    public Result Create(@RequestBody Sort sort) {
        sort.setCreatedAt(LocalDateTime.now());
        sortService.save(sort);
        return Result.OK();
    }

    @PutMapping("/update")
    @ApiOperation("更新")
    public Result Update(@RequestBody Sort sort) {
        sort.setUpdatedAt(LocalDateTime.now());
        sortService.updateById(sort);
        return Result.OK();
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除")
    public Result Delete(@RequestParam("id") Integer id) {
        Sort sort = new Sort();
        sort.setId(id);
        sort.setDeletedAt(LocalDateTime.now());
        sortMapper.updateById(sort);
        return Result.OK();
    }

}
