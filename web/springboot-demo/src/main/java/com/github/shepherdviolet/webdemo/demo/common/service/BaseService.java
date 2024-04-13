package com.github.shepherdviolet.webdemo.demo.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 基础服务
 */
public abstract class BaseService {

    //注入处理逻辑
    @Autowired
    @Qualifier("renderLogic")
    private Logic coreLogic;

    //处理后逻辑待定
    private Logic afterLogic;

    /**
     * 处理前逻辑待实现
     */
    public abstract String beforeHandle(String input);

    public String handle(String input){
        input = beforeHandle(input);
        input = coreLogic.handle(input);
        if (afterLogic != null) {
            input = afterLogic.handle(input);
        }
        return input;
    }

    /**
     * 设置处理后逻辑
     */
    protected void setAfterLogic(Logic afterLogic) {
        this.afterLogic = afterLogic;
    }

}
