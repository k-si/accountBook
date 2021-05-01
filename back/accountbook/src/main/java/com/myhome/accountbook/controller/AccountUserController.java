package com.myhome.accountbook.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myhome.accountbook.common.R;
import com.myhome.accountbook.entity.AccountUser;
import com.myhome.accountbook.entity.vo.UserQueryVo;
import com.myhome.accountbook.service.AccountUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 账单使用人 前端控制器
 * </p>
 *
 * @author orange
 * @since 2021-04-16
 */
@Api(description = "使用者管理")
@RestController
@RequestMapping("/accountbook/account-user")
@CrossOrigin
public class AccountUserController {

    @Autowired
    private AccountUserService userService;

    // 不带条件的分页查询
    @ApiOperation(value = "不带条件的分页查询")
    @GetMapping("/find/{page}/{limit}")
    public R find(@PathVariable long page, @PathVariable long limit) {
        Page<AccountUser> userPage = new Page<>(page, limit);
        userService.page(userPage, null);
        return R.ok()
                .data("total", userPage.getTotal())
                .data("items", userPage.getRecords());
    }

    // 带条件的分页查询
    @ApiOperation(value = "带条件的分页查询")
    @PostMapping("/findByCondition/{page}/{limit}")
    public R findByCondition(@PathVariable long page, @PathVariable long limit, @RequestBody UserQueryVo queryVo) {
        Page<AccountUser> userPage = new Page<>(page, limit);
        userService.userQuery(userPage, queryVo);
        return R.ok()
                .data("total", userPage.getTotal())
                .data("items", userPage.getRecords());
    }

    // 根据id查找使用人
    @ApiOperation(value = "根据id查找使用人")
    @GetMapping("/find/{id}")
    public R findById(@PathVariable String id) {
        AccountUser user = userService.getById(id);
        if (user == null)
            return R.error().message("未找到该使用者");
        return R.ok().data("item", user);
    }

    // 根据id修改使用人
    @ApiOperation(value = "根据id修改使用人")
    @PostMapping("/update/{id}")
    public R updateById(@PathVariable String id, @RequestBody AccountUser user) {
        user.setId(id);
        boolean isUpdated = userService.updateById(user);
        if (isUpdated) return R.ok();
        else return R.error().message("修改失败");
    }

    // 添加使用人
    @ApiOperation(value = "添加使用人")
    @PostMapping("/add")
    public R add(@RequestBody AccountUser user) {
        boolean isSaved = userService.save(user);
        if (isSaved) return R.ok();
        else return R.error().message("添加失败");
    }

    // 根据id逻辑删除使用人
    @ApiOperation(value = "根据id删除使用人")
    @DeleteMapping("/delete/{id}")
    public R deleteById(@PathVariable String id) {
        boolean isDeleted = userService.removeById(id);
        if (isDeleted) return R.ok();
        else return R.error().message("删除失败");
    }

    // 查询所有使用人
    @ApiOperation(value = "查询所有使用人")
    @GetMapping("/find")
    public R find() {
        List<AccountUser> users = userService.list(null);
        return R.ok().data("items", users);
    }

}
