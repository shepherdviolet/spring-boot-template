package com.github.shepherdviolet.webdemo.demo.validate.controller;

import com.github.shepherdviolet.webdemo.infra.error.CommonErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.shepherdviolet.webdemo.infra.error.RejectException;
import com.github.shepherdviolet.webdemo.demo.validate.entity.Request1;
import com.github.shepherdviolet.webdemo.demo.validate.entity.Request2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.groups.Default;
import java.util.Set;

/**
 * hibernates-validation 示例
 *
 * @author S.Violet
 */
@RestController
@RequestMapping("/validate")
public class ValidateController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Valid注解
     * POST | GET
     * http://localhost:8000/validate/annotation
     */
    @RequestMapping(value = "/annotation", method = RequestMethod.GET)
    public String validateAnnotation1(@Valid Request1 request) {
        return handleValidateAnnotation(request);
    }
    @RequestMapping(value = "/annotation", method = RequestMethod.POST)
    public String validateAnnotation2(@RequestBody @Valid Request1 request) {
        return handleValidateAnnotation(request);
    }
    private String handleValidateAnnotation(Request1 request) {
        logger.info("validation:" + request);
        //返回类型也可以是Bean
        return "ok";
    }

    /**
     * Valid注解, 自定义检查
     * POST | GET
     * http://localhost:8000/validate/custom
     */
    @RequestMapping(value = "/custom", method = RequestMethod.GET)
    public String validateCustom1(@Valid Request2 request) {
        return handleValidateCustom(request);
    }
    @RequestMapping(value = "/custom", method = RequestMethod.POST)
    public String validateCustom2(@RequestBody @Valid Request2 request) {
        return handleValidateCustom(request);
    }
    private String handleValidateCustom(Request2 request) {
        logger.info("validation:" + request);
        //返回类型也可以是Bean
        return "ok";
    }

    @Autowired
    private Validator validator;

    /**
     * Valid注解, 手动检查
     * POST | GET
     * http://localhost:8000/validate/manually
     */
    @RequestMapping(value = "/manually", method = RequestMethod.GET)
    public String validateManually1(Request1 request, HttpServletRequest rawRequest) {
        return handleValidateManually(request);
    }
    @RequestMapping(value = "/manually", method = RequestMethod.POST)
    public String validateManually2(@RequestBody Request1 request, HttpServletRequest rawRequest) {
        return handleValidateManually(request);
    }
    private String handleValidateManually(@RequestBody Request1 request) {
        Set<ConstraintViolation<Request1>> validationResult = validator.validate(request, Default.class);
        for (ConstraintViolation<Request1> constraintViolation : validationResult) {
            throw RejectException.create(CommonErrors.ILLEGAL_REQUEST_FIELD)
                    .msg(constraintViolation.getMessage())
                    .args(constraintViolation.getPropertyPath())
                    .build();
        }
        logger.info("validation:" + request);
        //返回类型也可以是Bean
        return "ok";
    }

}
