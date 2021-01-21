package com.healthcare.enrollee.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.healthcare.enrollee.util.ApplicationConstants;
import com.healthcare.enrollee.validator.DateFormatValidator;

@Documented
@Constraint(validatedBy = DateFormatValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateFormatConstraint {

	String message() default "";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String dateFormat() default ApplicationConstants.DATE_FORMAT_YYYY_MM_DD;
}
