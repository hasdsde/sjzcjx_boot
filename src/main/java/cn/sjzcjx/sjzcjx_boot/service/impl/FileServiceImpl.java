package cn.sjzcjx.sjzcjx_boot.service.impl;

import cn.sjzcjx.sjzcjx_boot.entity.File;
import cn.sjzcjx.sjzcjx_boot.mapper.FileMapper;
import cn.sjzcjx.sjzcjx_boot.service.IFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 位于本地的文件 服务实现类
 * </p>
 *
 * @author hasd
 * @since 2023-11-16
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

}
