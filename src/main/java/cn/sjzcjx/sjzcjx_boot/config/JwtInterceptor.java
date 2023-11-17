package cn.sjzcjx.sjzcjx_boot.config;

import cn.sjzcjx.sjzcjx_boot.service.impl.UserServiceImpl;
import cn.sjzcjx.sjzcjx_boot.utils.JwtUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/11/17 22:27
 **/

@Configuration
public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    UserServiceImpl userService;

    public static String GlobalUserToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        if (Objects.equals(request.getMethod(), "OPTIONS")) {
            return true;
        }

        if (token == null) {
            throw new AppException(401, "token is null");
        }

        GlobalUserToken = token;

        return JwtUtil.checkJwt(userService, token);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
