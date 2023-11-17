package cn.sjzcjx.sjzcjx_boot.controller.admin;

import cn.sjzcjx.sjzcjx_boot.config.Result;
import cn.sjzcjx.sjzcjx_boot.entity.User;
import cn.sjzcjx.sjzcjx_boot.mapper.UserMapper;
import cn.sjzcjx.sjzcjx_boot.service.impl.UserServiceImpl;
import cn.sjzcjx.sjzcjx_boot.utils.SqlUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .like(SqlUtil.condition(userId), User::getUserId, userId)
                .like(SqlUtil.condition(username), User::getUsername, username);

        Page<User> page = userMapper.selectPage(new Page<>(currentPage, pageSize), queryWrapper);

        return Result.OKWithPage(page);
    }

    @PostMapping("/create")
    @ApiOperation("创建用户")
    public Result Create(@RequestBody User user) {
        user.setCreatedAt(LocalDateTime.now());
        userService.save(user);
        return Result.OK();
    }

    @PutMapping("/update")
    @ApiOperation("根据ID修改用户")
    public Result Update(@RequestBody User user) {
        user.setUpdatedAt(LocalDateTime.now());
        userService.updateById(user);
        return Result.OKWithMessage("操作成功");
    }

    @DeleteMapping("/delete")
    @ApiOperation("根据ID删除用户")
    public Result Delete(@RequestParam("id") Integer id) {
        User user = new User();
        user.setId(id);
        user.setDeletedAt(LocalDateTime.now());
        userService.updateById(user);
        return Result.OKWithMessage("操作成功");
    }
}
