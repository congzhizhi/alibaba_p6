package com;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class BeanService {

    private String name="congzhizhi";
    private int age=32;

    public BeanService(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("BeanService 带参数");
    }

    public BeanService() {
        System.out.println("BeanService 不带参数");
    }
}
