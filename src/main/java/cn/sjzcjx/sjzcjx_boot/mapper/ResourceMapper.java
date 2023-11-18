package cn.sjzcjx.sjzcjx_boot.mapper;

import cn.sjzcjx.sjzcjx_boot.entity.Resource;
import cn.sjzcjx.sjzcjx_boot.entity.dto.ResourceDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author hasd
 * @since 2023-11-16
 */
@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {

    List<ResourceDto> selectOneWithUrl(@Param("name") String name);
}
