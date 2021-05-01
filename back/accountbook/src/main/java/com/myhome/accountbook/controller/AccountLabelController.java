package com.myhome.accountbook.controller;


import com.myhome.accountbook.common.R;
import com.myhome.accountbook.entity.AccountLabel;
import com.myhome.accountbook.service.AccountLabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 流水标签 前端控制器
 * </p>
 *
 * @author orange
 * @since 2021-04-17
 */
@Api(description = "标签管理")
@RestController
@RequestMapping("/accountbook/account-label")
@CrossOrigin
public class AccountLabelController {

    @Autowired
    private AccountLabelService labelService;

    @ApiOperation(value = "获取所有标签")
    @GetMapping("/all")
    public R all() {
        List<AccountLabel> labels = labelService.list(null);
        return R.ok().data("items", labels);
    }

    @ApiOperation(value = "添加标签")
    @PostMapping("/add")
    public R add(@RequestBody AccountLabel label) {
        if (labelService.getByName(label.getName()) != null)
            return R.error().message("标签名不允许重复");
        boolean isSaved = labelService.save(label);
        AccountLabel label1 = labelService.getByName(label.getName());
        if (isSaved) return R.ok().data("item", label1);
        else return R.error().message("添加失败");
    }

    @ApiOperation(value = "删除标签")
    @DeleteMapping("delete/{id}")
    public R deleteById(@PathVariable String id) {
        boolean isDeleted = labelService.removeById(id);
        if (isDeleted) return R.ok();
        else return R.error().message("删除失败");
    }
}
