package cn.sjzcjx.sjzcjx_boot.mapper;

import cn.sjzcjx.sjzcjx_boot.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author hasd
 * @since 2023-11-16
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
