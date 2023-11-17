package cn.sjzcjx.sjzcjx_boot.controller.publics;

import cn.sjzcjx.sjzcjx_boot.config.AppException;
import cn.sjzcjx.sjzcjx_boot.config.Result;
import cn.sjzcjx.sjzcjx_boot.entity.Log;
import cn.sjzcjx.sjzcjx_boot.entity.User;
import cn.sjzcjx.sjzcjx_boot.service.impl.LogServiceImpl;
import cn.sjzcjx.sjzcjx_boot.service.impl.UserServiceImpl;
import cn.sjzcjx.sjzcjx_boot.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/11/17 09:44
 **/

@RestController
@RequestMapping("/puser")
@Api(tags = "public-用户")
@Slf4j
public class PUserController {
    @Resource
    UserServiceImpl userService;

    @Resource
    LogServiceImpl logService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public Result login(@RequestBody User user, ServletRequest request) {
        User selectedUser = userService
                .lambdaQuery()
                .eq(User::getUserId, user.getUserId())
                .isNull(User::getDeletedAt)
                .one();

        if (selectedUser == null) {
            throw new AppException("账户不存在或密码错误");
        }

        if (!Objects.equals(selectedUser.getPassword(), user.getPassword())) {
            throw new AppException("账户不存在或密码错误");
        }

        //保存一个日志
        PLogUtil pLogUtil = new PLogUtil(logService, new Log(null, "login", 0, user.getUserId(), request.getRemoteAddr(), LocalDateTime.now()));
        pLogUtil.run();

        String token = JwtUtil.generateJwt(selectedUser);
        user.setPassword("");

        HashMap<String, Object> data = new HashMap<>();
        data.put("userInfo", selectedUser);
        data.put("token", token);
        return Result.OKWithData(data);
    }

}
