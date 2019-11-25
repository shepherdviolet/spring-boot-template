package com.github.shepherdviolet.webdemo.demo.aspectj.service;

import org.springframework.stereotype.Service;
import com.github.shepherdviolet.webdemo.demo.aspectj.aspect.Mark;

@Service
public class Service1 {

    public Long method1(Integer param1) {
        return null;
    }

    @Mark
    public Float method2(Integer param1) {
        return null;
    }

    public Double method3(@Mark Integer param1, @Mark Float param2) {
        return null;
    }

    public String method4(String param1) {
        throw new RuntimeException("Demo exception");
    }

    @Mark(param = "mark param")
    public String method5(String param1) {
        return null;
    }

}
