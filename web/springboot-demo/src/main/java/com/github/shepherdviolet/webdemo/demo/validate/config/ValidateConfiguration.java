package com.github.shepherdviolet.webdemo.demo.validate.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.*;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 *
 */
@Configuration
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.validate.controller",
})
public class ValidateConfiguration {

    /**
     * 手动调用的validator
     */
    @Bean
    public Validator validator() {
        return Validation.byProvider(HibernateValidator.class)
                .configure()
                .addProperty("hibernate.validator.fail_fast", "true")
                .buildValidatorFactory()
                .getValidator();
    }

}
