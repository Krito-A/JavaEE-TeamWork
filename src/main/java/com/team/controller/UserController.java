package com.team.controller;

import com.team.domain.User;
import com.team.dto.Result;
import com.team.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081",allowCredentials = "true")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/insert")
    public Result insert(@RequestParam String userName, @RequestParam String email) {
        return userService.insert(userName, email);
    }

    @GetMapping("/list")
    public Result list() {
        List<User> userList = userService.list();
        return Result.ok(userList);
    }


}
