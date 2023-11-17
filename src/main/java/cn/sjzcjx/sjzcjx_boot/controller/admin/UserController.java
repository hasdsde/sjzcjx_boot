package cn.sjzcjx.sjzcjx_boot.controller.admin;

import cn.sjzcjx.sjzcjx_boot.config.AppException;
import cn.sjzcjx.sjzcjx_boot.config.JwtInterceptor;
import cn.sjzcjx.sjzcjx_boot.config.Result;
import cn.sjzcjx.sjzcjx_boot.entity.User;
import cn.sjzcjx.sjzcjx_boot.mapper.UserMapper;
import cn.sjzcjx.sjzcjx_boot.service.impl.UserServiceImpl;
import cn.sjzcjx.sjzcjx_boot.utils.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author hasd
 * @since 2023-11-16
 */
@RestController
@RequestMapping("/user")
@Api(tags = "admin-用户")
@Slf4j
public class UserController {

    @Resource
    UserServiceImpl userService;
    @Resource
    UserMapper userMapper;

    @GetMapping("/list")
    @ApiOperation("获取用户列表")
    public Result getList(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam("currentPage") int currentPage,
            @RequestParam("pageSize") int pageSize
    ) {

        if (!Objects.equals(JwtUtil.getTokenInfo(JwtInterceptor.GlobalUserToken, "role"), "admin")) {
            throw new AppException("permission error");
        }

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .like(userId == null, User::getUserId, userId)
                .like(username == null, User::getUsername, username);

        Page<User> page = userMapper.selectPage(new Page<>(currentPage, pageSize), queryWrapper);

        return Result.OKWithPage(page);
    }

    @PostMapping("/create")
    @ApiOperation("创建用户")
    public Result Create(@RequestBody User user) {

        if (!Objects.equals(JwtUtil.getTokenInfo(JwtInterceptor.GlobalUserToken, "role"), "admin")) {
            throw new AppException("permission error");
        }

        user.setCreatedAt(LocalDateTime.now());
        userService.save(user);
        return Result.OK();
    }

    @PutMapping("/update")
    @ApiOperation("根据ID修改用户")
    public Result Update(@RequestBody User user) {

        if (!Objects.equals(JwtUtil.getTokenInfo(JwtInterceptor.GlobalUserToken, "role"), "admin")) {
            throw new AppException("permission error");
        }

        user.setUpdatedAt(LocalDateTime.now());
        userService.updateById(user);
        return Result.OKWithMessage("操作成功");
    }

    @DeleteMapping("/delete")
    @ApiOperation("根据ID删除用户")
    public Result Delete(@RequestParam("id") Integer id) {

        if (!Objects.equals(JwtUtil.getTokenInfo(JwtInterceptor.GlobalUserToken, "role"), "admin")) {
            throw new AppException("permission error");
        }

        User user = new User();
        user.setId(id);
        user.setDeletedAt(LocalDateTime.now());
        userService.updateById(user);
        return Result.OKWithMessage("操作成功");
    }
}
