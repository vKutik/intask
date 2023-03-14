package com.project.intask.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = MathPassword.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchPassword {

    String message() default "Password does not match";

    String filed();

    String filedMatch();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
