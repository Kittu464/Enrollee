package com.healthcare.enrollee.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.healthcare.enrollee.constraint.DateFormatConstraint;
import com.healthcare.enrollee.util.ApplicationConstants;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddDependentRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long enrolleeId;

	@NotBlank
	private String name;

	@NotNull
	@DateFormatConstraint(dateFormat = ApplicationConstants.DATE_FORMAT_YYYY_MM_DD, message = "DOB format should be "+ApplicationConstants.DATE_FORMAT_YYYY_MM_DD)
	private String dateOfBirth;

}
