package com.healthcare.enrollee.vo;

import com.healthcare.enrollee.model.Enrollee;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class GetEnrolleeResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;

	private Enrollee enrollee;
	
}
