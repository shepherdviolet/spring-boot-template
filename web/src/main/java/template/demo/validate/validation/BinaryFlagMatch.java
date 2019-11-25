package template.demo.validate.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义检查注解: 判断指定二进制位是否为1
 */
@Documented
@Constraint(validatedBy = BinaryFlagMatchValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface BinaryFlagMatch {

    String message() default "field-binary-flag-not-match";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    int value() default 0x00000000;

}
