package com.healthcare.enrollee.serviceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcare.enrollee.entity.DependentEntity;
import com.healthcare.enrollee.entity.EnrolleeEntity;
import com.healthcare.enrollee.exception.EnrolleeServiceException;
import com.healthcare.enrollee.logging.LogManager;
import com.healthcare.enrollee.logging.LoggingLevel;
import com.healthcare.enrollee.model.Dependent;
import com.healthcare.enrollee.repository.DependentRepository;
import com.healthcare.enrollee.repository.EnrolleeRepository;
import com.healthcare.enrollee.service.DependentService;
import com.healthcare.enrollee.util.ApplicationConstants;
import com.healthcare.enrollee.util.DateUtils;
import com.healthcare.enrollee.vo.AddDependentRequest;
import com.healthcare.enrollee.vo.AddDependentResponse;
import com.healthcare.enrollee.vo.BaseResponse;
import com.healthcare.enrollee.vo.GetDependentResponse;
import com.healthcare.enrollee.vo.UpdateDependentRequest;
import com.healthcare.enrollee.vo.UpdateDependentResponse;

@Service
public class DependentServiceImpl implements DependentService {

	@Autowired
	private DependentRepository dependentRepository;

	@Autowired
	private EnrolleeRepository enrolleeRepository;

	@Override
	public AddDependentResponse addDependent(AddDependentRequest request) {
		LogManager.writeLog(DependentServiceImpl.class, "addDependent() : Start", LoggingLevel.INFO);
		AddDependentResponse response = null;
		EnrolleeEntity enrolleeEntity = null;
		DependentEntity dependentEntity = null;
		Optional<EnrolleeEntity> optional = null;
		try {
			response = new AddDependentResponse();
			optional = enrolleeRepository.findById(request.getEnrolleeId());
			enrolleeEntity = (optional != null && optional.isPresent()) ? optional.get() : null;
			if (enrolleeEntity != null) {
				dependentEntity = new DependentEntity();
				dependentEntity.setName(request.getName());
				dependentEntity.setDateOfBirth(DateUtils.convertStringToDate(request.getDateOfBirth(), ApplicationConstants.DATE_FORMAT_YYYY_MM_DD));
				dependentEntity.setEnrolleeId(request.getEnrolleeId());
				LogManager.writeLog(DependentServiceImpl.class, "Dependent going to add into DB : " + dependentEntity, LoggingLevel.INFO);
				dependentEntity = dependentRepository.save(dependentEntity);
				LogManager.writeLog(DependentServiceImpl.class, "Dependent added successfully with id : " + dependentEntity.getId(), LoggingLevel.INFO);
				response.setDependentId(dependentEntity.getId());
				response.setMessage("Dependent added successfully");
				response.setResponseCode(ApplicationConstants.RESPONSE_CODE_SUCCESS_0000);
			} else {
				response.setMessage("Invalid enrollee id");
				response.setResponseCode(ApplicationConstants.RESPONSE_CODE_INVALID_INPUT);
				LogManager.writeLog(EnrolleeServiceImpl.class, "Invalid enrollee id", LoggingLevel.INFO);
			}
		} catch (Exception e) {
			LogManager.writeLog(EnrolleeServiceImpl.class, "Exception occured while adding dependent in DB", LoggingLevel.ERROR);
			throw new EnrolleeServiceException("Exception occured while adding dependent in DB");
		}
		LogManager.writeLog(DependentServiceImpl.class, "addDependent() : End", LoggingLevel.INFO);
		return response;
	}

	@Override
	public UpdateDependentResponse updateDependent(UpdateDependentRequest request) {
		LogManager.writeLog(DependentServiceImpl.class, "updateDependent() : Start", LoggingLevel.INFO);
		DependentEntity entity = null;
		UpdateDependentResponse response = null;
		Optional<DependentEntity> optional = null;
		try {
			response = new UpdateDependentResponse();
			optional = dependentRepository.findById(request.getId());
			entity = (optional != null && optional.isPresent()) ? optional.get() : null;
			LogManager.writeLog(DependentServiceImpl.class, "Dependent from DB : " + entity, LoggingLevel.INFO);
			if (entity != null) {
				entity.setName(request.getName());
				entity.setDateOfBirth(DateUtils.convertStringToDate(request.getDateOfBirth(), ApplicationConstants.DATE_FORMAT_YYYY_MM_DD));
				entity.setUpdatedDate(LocalDateTime.now());
				LogManager.writeLog(DependentServiceImpl.class, "Dependent info going to update is: " + entity,	LoggingLevel.INFO);
				dependentRepository.save(entity);
				LogManager.writeLog(DependentServiceImpl.class, "Dependent info updated successfully", LoggingLevel.INFO);
				response.setMessage("Dependent info updated successfully");
				response.setResponseCode(ApplicationConstants.RESPONSE_CODE_SUCCESS_0000);
			} else {
				response.setMessage("Invalid dependent id");
				response.setResponseCode(ApplicationConstants.RESPONSE_CODE_INVALID_INPUT);
				LogManager.writeLog(EnrolleeServiceImpl.class, "Invalid dependent id", LoggingLevel.INFO);
			}
		} catch (Exception e) {
			LogManager.writeLog(EnrolleeServiceImpl.class, "Exception occured while updating dependent", LoggingLevel.ERROR);
			throw new EnrolleeServiceException("Exception occured while updating dependent");
		}
		LogManager.writeLog(DependentServiceImpl.class, "updateDependent() : End", LoggingLevel.INFO);
		return response;
	}

	@Override
	public BaseResponse deleteDependent(Long id) {
		LogManager.writeLog(DependentServiceImpl.class, "deleteDependent() : Start", LoggingLevel.INFO);
		BaseResponse response = null;
		DependentEntity entity = null;
		Optional<DependentEntity> optional = null;
		try {
			response = new BaseResponse();
			optional = dependentRepository.findById(id);
			entity = (optional != null && optional.isPresent()) ? optional.get() : null;
			LogManager.writeLog(DependentServiceImpl.class, "Dependent from DB : " + entity, LoggingLevel.INFO);
			if (entity != null) {
				dependentRepository.deleteById(id);
				LogManager.writeLog(DependentServiceImpl.class, "Dependent deleted successfully", LoggingLevel.INFO);
				response.setMessage("Dependent removed successfully");
				response.setResponseCode(ApplicationConstants.RESPONSE_CODE_SUCCESS_0000);
			} else {
				response.setMessage("Invalid dependent id");
				response.setResponseCode(ApplicationConstants.RESPONSE_CODE_INVALID_INPUT);
				LogManager.writeLog(EnrolleeServiceImpl.class, "Invalid dependent id", LoggingLevel.INFO);
			}
		} catch (Exception e) {
			LogManager.writeLog(EnrolleeServiceImpl.class, "Exception occured while deleting dependent", LoggingLevel.ERROR);
			throw new EnrolleeServiceException("Exception occured while deleting dependent");
		}
		LogManager.writeLog(DependentServiceImpl.class, "deleteDependent() : End", LoggingLevel.INFO);
		return response;
	}

	@Override
	public GetDependentResponse getDependent(Long id) {
		LogManager.writeLog(DependentServiceImpl.class, "Start", LoggingLevel.INFO);
		Dependent dependent = null;
		DependentEntity entity = null;
		GetDependentResponse response = null;
		Optional<DependentEntity> otional = null;
		try {
			response = new GetDependentResponse();
			otional = dependentRepository.findById(id);
			entity = (otional != null && otional.isPresent()) ? otional.get() : null;
			if (entity != null) {
				dependent = new Dependent();
				BeanUtils.copyProperties(entity, dependent);
				response.setDependent(dependent);
				response.setMessage(ApplicationConstants.RESPONSE_MSG_SUCCESS);
				response.setResponseCode(ApplicationConstants.RESPONSE_CODE_SUCCESS_0000);
			} else {
				response.setMessage(ApplicationConstants.RESPONSE_MSG_NO_RECORD_FOUND);
				response.setResponseCode(ApplicationConstants.RESPONSE_CODE_FAILURE_0001);
			}
		} catch (Exception e) {
			LogManager.writeLog(EnrolleeServiceImpl.class, "Exception occured while getting dependent", LoggingLevel.ERROR);
			throw new EnrolleeServiceException("Exception occured while getting dependent");
		}
		return response;
	}
}
