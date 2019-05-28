package se.BTH.ITProjectManagement.validator;

import se.BTH.ITProjectManagement.Annotations.PositiveNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PositiveNumberValidator implements ConstraintValidator<PositiveNumber, Integer> {

    @Override
    public void initialize(PositiveNumber constraintAnnotation) {

    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return ((value != null) && (value> -1));
    }
}
