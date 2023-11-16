package cn.sjzcjx.sjzcjx_boot.service.impl;

import cn.sjzcjx.sjzcjx_boot.entity.Log;
import cn.sjzcjx.sjzcjx_boot.mapper.LogMapper;
import cn.sjzcjx.sjzcjx_boot.service.ILogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hasd
 * @since 2023-11-16
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

}
