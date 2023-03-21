package com.github.shepherdviolet.webdemo.demo.common.entity;

/**
 * 一个持有其他Bean的Bean
 */
public class FatBean {

    private SimpleBeanImpl simpleBean;

    public FatBean() {
    }

    public FatBean(SimpleBeanImpl simpleBean) {
        this.simpleBean = simpleBean;
    }

    public SimpleBeanImpl getSimpleBean() {
        return simpleBean;
    }

    public void setSimpleBean(SimpleBeanImpl simpleBean) {
        this.simpleBean = simpleBean;
    }

    @Override
    public String toString() {
        return "FatBean{" +
                "simpleBean=" + simpleBean +
                '}';
    }

}
