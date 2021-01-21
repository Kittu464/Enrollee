package com.healthcare.enrollee.validator;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.healthcare.enrollee.constraint.DateFormatConstraint;
import com.healthcare.enrollee.util.ApplicationConstants;
import com.healthcare.enrollee.util.DateUtils;

public class DateFormatValidator implements ConstraintValidator<DateFormatConstraint, String> {

	private String dateFormat;

	@Override
	public void initialize(DateFormatConstraint constraintAnnotation) {
		dateFormat = constraintAnnotation.dateFormat();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isBlank(value)) {
			return true;
		} else {
			if (ApplicationConstants.DATE_FORMAT_YYYY_MM_DD.equals(dateFormat)) {
				LocalDate date = DateUtils.convertStringToDate(value, dateFormat);
				if (date != null) {
					return true;
				}
			}
		}
		return false;
	}
}
