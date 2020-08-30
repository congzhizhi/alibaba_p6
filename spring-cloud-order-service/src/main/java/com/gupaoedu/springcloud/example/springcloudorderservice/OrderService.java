package com.gupaoedu.springcloud.example.springcloudorderservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderService {

    @Value("${server.port}")
    private int port;

    @GetMapping("/orders")
    public String getAllOrder(){
        System.out.println("port:"+port);
        return "Return All Order";
    }
}
