package cn.sjzcjx.sjzcjx_boot.utils;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.sjzcjx.sjzcjx_boot.config.AppException;
import cn.sjzcjx.sjzcjx_boot.entity.User;
import cn.sjzcjx.sjzcjx_boot.service.impl.UserServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/11/16 16:05
 **/


public class JwtUtil {

    @Resource
    public static UserServiceImpl userService;

    /**
     * @param user
     * @return java.lang.String
     * @Description 生成Token
     * @author hasdsd
     * @Date 2023/11/16
     */
    public static String generateJwt(User user) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userName", user.getUsername());
        map.put("user_id", user.getUserId());
        map.put("role", user.getRole());
        map.put("expire_time", System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30);//半个月
        return JWTUtil.createToken(map, user.getPassword().getBytes());
    }

    public static Boolean checkJwt(String token) {

        JWT jwt = JWTUtil.parseToken(token);
        String userId = jwt.getPayload("user_id").toString();

        String expireTime = jwt.getPayload("expire_time").toString();

        if (Long.parseLong(expireTime) < System.currentTimeMillis()) {
            throw new AppException(401, "登录已过期");
        }

        User user = userService.getOne(new QueryWrapper<User>().eq("user_id", userId));
        return JWTUtil.verify(token, user.getPassword().getBytes());
    }
}
