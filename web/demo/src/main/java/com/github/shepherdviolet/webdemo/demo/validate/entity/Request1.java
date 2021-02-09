package com.github.shepherdviolet.webdemo.demo.validate.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Request1 {

    @NotBlank(message = "field-blank")
    @Size(max = 20, message = "field-too-long")
    private String name;

    @NotBlank(message = "field-blank")
    @Pattern(regexp = "^\\d{6}$", message = "field-invalid")
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
