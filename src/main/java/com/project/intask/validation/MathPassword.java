package com.project.intask.validation;

import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class MathPassword implements ConstraintValidator<MatchPassword, Object> {

    private String password;
    private String confirmPassword;

    @Override
    public void initialize(MatchPassword constraintAnnotation) {
        this.password = constraintAnnotation.filed();
        this.confirmPassword = constraintAnnotation.filedMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

        Object fieldValue = new BeanWrapperImpl(value)
                    .getPropertyValue(password);
        Object fieldMatchValue = new BeanWrapperImpl(value)
                    .getPropertyValue(confirmPassword);

        if (fieldValue != null && fieldMatchValue != null) {
            return Objects.equals(fieldValue, fieldMatchValue);
        }
        return false;
    }
}
