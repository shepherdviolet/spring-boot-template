package template.demo.aspectj.service;

import org.springframework.stereotype.Service;
import template.demo.aspectj.aspect.Mark;

@Service
@Mark
public class Service3 {

    public void method1(String param1){
    }

}
