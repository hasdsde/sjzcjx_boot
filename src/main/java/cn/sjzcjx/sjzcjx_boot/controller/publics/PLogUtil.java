package cn.sjzcjx.sjzcjx_boot.controller.publics;

import cn.sjzcjx.sjzcjx_boot.entity.Log;
import cn.sjzcjx.sjzcjx_boot.service.impl.LogServiceImpl;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Resource;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/11/17 14:23
 **/


@Setter
@Getter
public class PLogUtil implements Runnable {

    @Resource
    public LogServiceImpl logService;

    public Log log;

    /**
     * @param logService 注入的service
     * @param log        需要新建的对象
     * @Description 多线程存储
     * @author hasdsd
     * @Date 2023/11/17
     */
    public PLogUtil(LogServiceImpl logService, Log log) {
        this.logService = logService;
        this.log = log;
    }

    @Override
    public void run() {
        logService.save(log);
    }

}
