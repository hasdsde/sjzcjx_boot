package cn.sjzcjx.sjzcjx_boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/11/16 00:00
 **/

@RestController
@RequestMapping("/test")
@Api(tags = "Test测试")
public class pingController {

    @GetMapping("/ping")
    @ApiOperation(value = "PING测试")
    public String Ping() {
        return "Pong";
    }

}
