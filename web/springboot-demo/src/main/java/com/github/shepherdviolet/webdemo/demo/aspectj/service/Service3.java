package com.github.shepherdviolet.webdemo.demo.aspectj.service;

import org.springframework.stereotype.Service;
import com.github.shepherdviolet.webdemo.demo.aspectj.aspect.Mark;

@Service
@Mark
public class Service3 {

    public void method1(String param1){
    }

}
