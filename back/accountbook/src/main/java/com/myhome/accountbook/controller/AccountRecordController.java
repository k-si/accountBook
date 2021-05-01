package com.myhome.accountbook.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myhome.accountbook.common.R;
import com.myhome.accountbook.common.RecordStatusCode;
import com.myhome.accountbook.entity.AccountLabel;
import com.myhome.accountbook.entity.AccountRecord;
import com.myhome.accountbook.entity.AccountUser;
import com.myhome.accountbook.entity.vo.AccountRecordVo;
import com.myhome.accountbook.entity.vo.RecordQueryVo;
import com.myhome.accountbook.service.AccountLabelService;
import com.myhome.accountbook.service.AccountRecordService;
import com.myhome.accountbook.service.AccountUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 账单 前端控制器
 * </p>
 *
 * @author orange
 * @since 2021-04-16
 */
@Api(description = "流水记录管理")
@RestController
@RequestMapping("/accountbook/account-record")
@CrossOrigin
public class AccountRecordController {

    @Autowired
    private AccountRecordService recordService;

    @Autowired
    private AccountLabelService labelService;

    @Autowired
    private AccountUserService userService;

    // 带条件的分页查询
    @ApiOperation(value = "带条件的分页查询")
    @PostMapping("/findByCondition/{page}/{limit}")
    public R findByCondition(@PathVariable long page, @PathVariable long limit, @RequestBody RecordQueryVo queryVo) {
        Page<AccountRecord> accountPage = new Page<>(page, limit);
        AccountRecordVo[] records = recordService.recordQuery(accountPage, queryVo);
        return R.ok()
                .data("total", records.length)
                .data("items", records);
    }

    // 根据id查找记录
    @ApiOperation(value = "根据流水id查找记录")
    @GetMapping("find/{id}")
    public R findById(@PathVariable String id) {
        AccountRecord record = recordService.getById(id);

        if (record == null)
            return R.error().message("未找到该流水记录");

        if (RecordStatusCode.EXPEND.equals(record.getStatus())) record.setStatus("expend"); // 使前端回显流水状态时正确显示label
        else record.setStatus("income");

        return R.ok().data("item", record);
    }

    // 根据id修改记录
    @ApiOperation(value = "根据id修改记录")
    @PostMapping("/update/{id}")
    public R updateById(@PathVariable String id, @RequestBody AccountRecord record) {
        record.setId(id);

        // record表中存在labelId和label，虽然存在冗余，但是可以通过labelId查找label
        // 如果只有labelId，那么必须进行连表查询，性能和逻辑更差
        // 缺点是需要在后端再次对label进行赋值
        AccountLabel label = labelService.getById(record.getLabelId());
        record.setLabel(label.getName());

        // 根据流水金额变化更改用户余额
        userService.updateBalance(record.getUserId(), record.getStatus(), record.getChange());

        boolean isUpdated = recordService.updateById(record);
        if (isUpdated) return R.ok();
        else return R.error().message("修改失败");
    }
    
    // 添加流水记录
    @ApiOperation(value = "添加流水记录")
    @PostMapping("/add")
    public R add(@RequestBody AccountRecord record) {
        // record表中存在labelId和label，虽然存在冗余，但是可以通过labelId查找label
        // 如果只有labelId，那么必须进行连表查询，性能和逻辑更差
        // 缺点是需要在后端再次对label进行赋值
        AccountLabel label = labelService.getById(record.getLabelId());
        record.setLabel(label.getName());

        // 根据流水金额变化更改用户余额
        userService.updateBalance(record.getUserId(), record.getStatus(), record.getChange());

        // gmt_create字段当作事件发生时间
        // 由于gmt_create字段是自动添加的，所以还需再次修改自定义的添加日期
        boolean isSaved = recordService.save(record);
        recordService.updateEventTime(record.getId(), record.getGmtCreate());

        if (isSaved) return R.ok();
        else return R.error().message("添加失败");
    }

    // 根据id逻辑删除流水记录
    @ApiOperation(value = "根据id删除流水记录")
    @DeleteMapping("/delete/{id}")
    public R deleteById(@PathVariable String id) {

        // 根据流水金额变化更改用户余额，由于是删除，所以要逆向增减
        AccountRecord record = recordService.getById(id);
        String status = record.getStatus();
        if (RecordStatusCode.EXPEND.equals(status)) status = RecordStatusCode.INCOME;
        else status = RecordStatusCode.EXPEND;
        userService.updateBalance(record.getUserId(), status, record.getChange());

        boolean isDeleted = recordService.removeById(id);

        if (isDeleted) return R.ok();
        else return R.error().message("删除失败");
    }

    @ApiOperation(value = "获取用户组中的资产总额")
    @GetMapping("/getTotalAssets")
    public R getTotalAssets() {
        List<AccountUser> users = userService.list(null);
        BigDecimal assets = new BigDecimal(0);
        for (int i = 0; i < users.size(); ++i) {
            System.out.println(users.get(i).getBalance());
            assets = assets.add(new BigDecimal(String.valueOf(users.get(i).getBalance())));
        }
        return R.ok().data("totalAssets", assets);
    }
}
