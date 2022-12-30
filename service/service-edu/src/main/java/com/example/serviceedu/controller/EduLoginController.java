package com.example.serviceedu.controller;

import com.example.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * com.example.serviceedu.controller
 *
 * @author xzwnp
 * 2022/1/28
 * 11:47
 * Steps：
 */
@RestController
@RequestMapping("/eduservice/user")
@Api(tags = "临时接口 不用管")
public class EduLoginController {
    //login
    @PostMapping("login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    //info
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
