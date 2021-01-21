package com.healthcare.enrollee.vo;

import com.healthcare.enrollee.model.Dependent;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class GetDependentResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;

	private Dependent dependent;
	
}
