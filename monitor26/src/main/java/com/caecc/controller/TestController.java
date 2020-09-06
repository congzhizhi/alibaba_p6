package com.caecc.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.caecc.mapper.UserMapper;
import com.caecc.mapper.WorkParamMapper;
import com.caecc.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {


    @Autowired
    UserMapper userMapper;

    @GetMapping("cong")
    public Users test(){
        return userMapper.selectById(1L);
    }

    @GetMapping("all")
    public List<Users> all(){
        return userMapper.selectList(null);
    }
    @GetMapping("add")
    public Long add(){
        Users users = new Users();

        userMapper.insert(users);
        return users.getId();


    }
    @GetMapping("update")
    public void update(){

        Users users = new Users();
        users.setId(5L);
        users.setAge(32);
        userMapper.updateById(users);
    }
    @GetMapping("delete")
    public void delete(){

        Users users = new Users();
        users.setAge(32);
        userMapper.delete(new Wrapper<Users>() {
            @Override
            public Users getEntity() {
                return users;
            }

            @Override
            public MergeSegments getExpression() {
                return null;
            }

            @Override
            public void clear() {

            }

            @Override
            public String getSqlSegment() {
                return null;
            }
        });
    }

}



















