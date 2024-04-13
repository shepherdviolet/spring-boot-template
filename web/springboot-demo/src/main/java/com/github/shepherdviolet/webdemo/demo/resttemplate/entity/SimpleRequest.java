package com.github.shepherdviolet.webdemo.demo.resttemplate.entity;

import java.math.BigDecimal;

public class SimpleRequest {

    private String name;
    private BigDecimal amount;

    public String getName() {
        return name;
    }

    public SimpleRequest setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public SimpleRequest setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public String toString() {
        return "SimpleRequest{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
