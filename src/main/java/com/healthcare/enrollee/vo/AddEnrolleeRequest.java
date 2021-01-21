package com.healthcare.enrollee.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.healthcare.enrollee.constraint.DateFormatConstraint;
import com.healthcare.enrollee.util.ApplicationConstants;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AddEnrolleeRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String name;

	@NotNull
	@DateFormatConstraint(dateFormat = ApplicationConstants.DATE_FORMAT_YYYY_MM_DD, message = "DOB format should be "+ApplicationConstants.DATE_FORMAT_YYYY_MM_DD)
	private String dateOfBirth;

	@Pattern(regexp = "^[0-9]{10}$", message = "phone number must be 10 digit")
	private String phoneNumber;
	
	private String createdBy;

	private boolean activationStatus;
}
