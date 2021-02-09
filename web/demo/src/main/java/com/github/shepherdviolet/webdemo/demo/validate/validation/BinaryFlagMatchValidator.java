package com.github.shepherdviolet.webdemo.demo.validate.validation;

import sviolet.thistle.util.judge.CheckUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义检查: 判断指定二进制位是否为1
 */
public class BinaryFlagMatchValidator implements ConstraintValidator<BinaryFlagMatch, Integer> {

    private int flag;

    @Override
    public void initialize(BinaryFlagMatch constraintAnnotation) {
        flag = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return CheckUtils.isFlagMatch(value, flag);
    }

}
