package com.sound.haolei.consumer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sound.haolei.facade.HlUserFacade;


@RestController
public class DemoConsumerController {


    @Reference(version = "1.0.0",
            application = "${dubbo.application.id}")
    private HlUserFacade hlUserFacade;

    @RequestMapping("/sayHello")
    public String sayHello(@RequestParam String name) {
        return "Hello, "+name+" ! Welcome to Haolei innovation business center!";
    }


    @RequestMapping("/unauthenticated")
    public String unauthenticated() {
        return "{error:des}";
    }
}
