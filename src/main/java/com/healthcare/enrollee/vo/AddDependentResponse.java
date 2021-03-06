package com.healthcare.enrollee.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class AddDependentResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;

	private Long dependentId;

}
