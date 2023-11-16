package cn.sjzcjx.sjzcjx_boot.mapper;

import cn.sjzcjx.sjzcjx_boot.entity.File;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 位于本地的文件 Mapper 接口
 * </p>
 *
 * @author hasd
 * @since 2023-11-16
 */
@Mapper
public interface FileMapper extends BaseMapper<File> {

}
