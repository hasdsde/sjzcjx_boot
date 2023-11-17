package cn.sjzcjx.sjzcjx_boot.controller.publics;

import cn.sjzcjx.sjzcjx_boot.config.Result;
import cn.sjzcjx.sjzcjx_boot.entity.Sort;
import cn.sjzcjx.sjzcjx_boot.entity.dto.SortDto;
import cn.sjzcjx.sjzcjx_boot.mapper.SortMapper;
import cn.sjzcjx.sjzcjx_boot.service.impl.SortServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/11/17 09:49
 **/

@RestController
@RequestMapping("/psort")
@Api(tags = "公共-分类")
@Slf4j
public class PSortController {

    @Resource
    public SortMapper sortMapper;

    @Resource
    public SortServiceImpl sortService;


    @GetMapping("/list")
    @ApiOperation("获取全部列表附带父级子级")
    public Result SelectWithArray() {
        List<Sort> parentList = sortMapper.selectList(new QueryWrapper<Sort>().isNull("deleted_at").isNull("parent"));
        List<Sort> childList = sortMapper.selectList(new QueryWrapper<Sort>().isNull("deleted_at").isNotNull("parent"));
        ArrayList<SortDto> sortDtoList = new ArrayList<>();

        parentList.forEach(parent -> {
            SortDto sortDto = new SortDto();
            sortDto.setSortName(parent.getSortName());
            sortDto.setId(parent.getId());
            sortDto.setName(parent.getName());

            childList.forEach(child -> {
                if (Objects.equals(child.getParent(), sortDto.getId())) {
                    sortDto.addChildren(child);
                }
            });
            sortDtoList.add(sortDto);
        });

        return Result.OKWithData(sortDtoList);
    }
}
