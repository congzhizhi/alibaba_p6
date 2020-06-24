package com.luban.netty.lbrpc.server.service.impl;

import com.luban.netty.lbrpc.server.service.TestService;

import java.util.ArrayList;
import java.util.List;

public class TestServiceImpl implements TestService {

    static ArrayList<String> list = new ArrayList<String>();

    static {
        list.add("张三");
        list.add("李四");
    }

    public List<String> listAll() {
        return list;
    }

    public String listByid(Integer id) {
        return list.get(id);
    }
}
