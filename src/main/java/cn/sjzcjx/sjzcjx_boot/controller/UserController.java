package cn.sjzcjx.sjzcjx_boot.controller;

import cn.sjzcjx.sjzcjx_boot.config.AppException;
import cn.sjzcjx.sjzcjx_boot.config.Result;
import cn.sjzcjx.sjzcjx_boot.entity.User;
import cn.sjzcjx.sjzcjx_boot.service.impl.UserServiceImpl;
import cn.sjzcjx.sjzcjx_boot.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
@Api(tags = "用户")
public class UserController {

    @Resource
    UserServiceImpl userService;


    @PostMapping("/login")
    @ApiOperation("登录")
    public Result login(@RequestBody User user) {
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

        String token = JwtUtil.generateJwt(selectedUser);

        user.setPassword("");

        HashMap<String, Object> data = new HashMap<>();
        data.put("userInfo", selectedUser);
        data.put("token", token);
        return Result.OKWithData(data);
    }

    @GetMapping("/list")
    @ApiOperation("获取用户列表")
    public Result getList(
            @RequestParam("username") String username,
            @RequestParam("userId") String userId,
            @RequestParam("currentPage") String currentPage,
            @RequestParam("pageSize") String pageSize

    ) {
        List<User> list = userService.lambdaQuery()
                .like(!userId.isEmpty(), User::getUserId, userId)
                .like(!username.isEmpty(), User::getUsername, username).list();
        return Result.OKWithData(null);
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
