package com.myhome.accountbook.controller;

import com.myhome.accountbook.common.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class LoginController {

    @PostMapping("/login")
    public R login() {
        return R.ok()
                .data("token", "admin");
    }

    @GetMapping("/info")
    public R getInfo() {
        return R.ok()
                .data("roles", "[admin]")
                .data("name", "admin")
                .data("avatar", "https://ksir-oss.oss-cn-beijing.aliyuncs.com/myhome/avatar/admin.jpg");
    }

}
