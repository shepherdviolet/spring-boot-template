package com.github.shepherdviolet.webdemo.demo.validate.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Request1 {

    @NotBlank(message = "field_blank")
    @Size(max = 20, message = "field_too_long")
    private String name;

    @NotBlank(message = "field_blank")
    @Pattern(regexp = "^\\d{6}$", message = "field_invalid")
    private String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Request1{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
