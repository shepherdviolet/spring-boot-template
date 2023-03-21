package com.github.shepherdviolet.webdemo.demo.resttemplate.entity;

import java.math.BigDecimal;
import java.util.List;

public class SimpleResponse {

    private String name;
    private BigDecimal amount;
    private List<String> aliases;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    @Override
    public String toString() {
        return "SimpleResponse{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", aliases=" + aliases +
                '}';
    }
}
