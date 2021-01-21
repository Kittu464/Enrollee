package com.healthcare.enrollee.service;

import com.healthcare.enrollee.vo.AddEnrolleeRequest;
import com.healthcare.enrollee.vo.AddEnrolleeResponse;
import com.healthcare.enrollee.vo.BaseResponse;
import com.healthcare.enrollee.vo.GetEnrolleeResponse;
import com.healthcare.enrollee.vo.UpdateEnrolleeRequest;
import com.healthcare.enrollee.vo.UpdateEnrolleeResponse;

public interface EnrolleeService {

	public AddEnrolleeResponse addEnrollee(AddEnrolleeRequest request);

	public UpdateEnrolleeResponse updateEnrollee(UpdateEnrolleeRequest request);

	public BaseResponse deleteEnrollee(Long id);

	public GetEnrolleeResponse getEnrolleeDetails(Long id);
}
