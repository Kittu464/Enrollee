package com.healthcare.enrollee.service;

import com.healthcare.enrollee.vo.AddDependentRequest;
import com.healthcare.enrollee.vo.AddDependentResponse;
import com.healthcare.enrollee.vo.BaseResponse;
import com.healthcare.enrollee.vo.GetDependentResponse;
import com.healthcare.enrollee.vo.UpdateDependentRequest;
import com.healthcare.enrollee.vo.UpdateDependentResponse;

public interface DependentService {

	public GetDependentResponse getDependent(Long id);
	
	public AddDependentResponse addDependent(AddDependentRequest request);

	public UpdateDependentResponse updateDependent(UpdateDependentRequest request);

	public BaseResponse deleteDependent(Long id);
}
