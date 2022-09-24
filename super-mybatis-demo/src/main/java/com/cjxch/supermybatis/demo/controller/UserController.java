package com.cjxch.supermybatis.demo.controller;

import com.cjxch.supermybatis.demo.entity.User;
import com.cjxch.supermybatis.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/test")
    public List<User> test(){
        return userInfoService.allList();
    }
}
