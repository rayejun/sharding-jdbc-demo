package io.github.rayejun.shardingjdbc.demo.controller;

import io.github.rayejun.shardingjdbc.demo.dao.mysql.UserMapper;
import io.github.rayejun.shardingjdbc.demo.model.mysql.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
public class TestController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping("user/insert")
    public String userInsert(String username, String password, String schoolId) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(username);
        user.setPassword(password);
        user.setSchoolId(schoolId);
        user.setCreateTime(new Date());
        userMapper.insertSelective(user);
        return "success";
    }
}
