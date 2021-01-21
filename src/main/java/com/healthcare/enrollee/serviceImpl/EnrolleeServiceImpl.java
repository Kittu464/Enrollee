package com.healthcare.enrollee.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import com.healthcare.enrollee.model.Enrollee;
import com.healthcare.enrollee.repository.DependentRepository;
import com.healthcare.enrollee.repository.EnrolleeRepository;
import com.healthcare.enrollee.service.EnrolleeService;
import com.healthcare.enrollee.util.ApplicationConstants;
import com.healthcare.enrollee.util.DateUtils;
import com.healthcare.enrollee.vo.AddEnrolleeRequest;
import com.healthcare.enrollee.vo.AddEnrolleeResponse;
import com.healthcare.enrollee.vo.BaseResponse;
import com.healthcare.enrollee.vo.GetEnrolleeResponse;
import com.healthcare.enrollee.vo.UpdateEnrolleeRequest;
import com.healthcare.enrollee.vo.UpdateEnrolleeResponse;

@Service
public class EnrolleeServiceImpl implements EnrolleeService {

	@Autowired
	private EnrolleeRepository repository;
	
	
	@Autowired
	private DependentRepository dependentRepository;

	@Override
	public AddEnrolleeResponse addEnrollee(AddEnrolleeRequest request) {
		LogManager.writeLog(EnrolleeServiceImpl.class, "addEnrollee() : Start", LoggingLevel.INFO);
		EnrolleeEntity entity = null;
		AddEnrolleeResponse response = null;
		try {
			entity = new EnrolleeEntity();
			//BeanUtils.copyProperties(request, entity);
			entity.setName(request.getName());
			entity.setDateOfBirth(DateUtils.convertStringToDate(request.getDateOfBirth(), ApplicationConstants.DATE_FORMAT_YYYY_MM_DD));
			entity.setPhoneNumber(request.getPhoneNumber());
			entity.setIsActive(request.isActivationStatus() ? ApplicationConstants.FLAG_YES : ApplicationConstants.FLAG_NO);
			entity.setCreatedBy(request.getCreatedBy());
			LogManager.writeLog(EnrolleeServiceImpl.class, "Enrollee going to add into DB is : " + entity, LoggingLevel.INFO);
			entity = repository.save(entity);
			LogManager.writeLog(EnrolleeServiceImpl.class, "Enrollee added successfully with id : " + entity.getId(), LoggingLevel.INFO);
			response = new AddEnrolleeResponse();
			response.setEnrolleeId(entity.getId());
			response.setMessage("Enrollee added successfully");
			response.setResponseCode(ApplicationConstants.RESPONSE_CODE_SUCCESS_0000);
		} catch (Exception e) {
			LogManager.writeLog(EnrolleeServiceImpl.class, "Exception occured while addding enrollee in DB", LoggingLevel.ERROR);
			throw new EnrolleeServiceException("Exception occured while addding enrollee in DB");
		}
		LogManager.writeLog(EnrolleeServiceImpl.class, "addEnrollee() : End", LoggingLevel.INFO);
		return response;
	}

	@Override
	public UpdateEnrolleeResponse updateEnrollee(UpdateEnrolleeRequest request) {
		LogManager.writeLog(EnrolleeServiceImpl.class, "updateEnrollee() : Start", LoggingLevel.INFO);
		EnrolleeEntity entity = null;
		UpdateEnrolleeResponse response = null;
		Optional<EnrolleeEntity> optional = null;
		try {
			response = new UpdateEnrolleeResponse();
			optional = repository.findById(request.getId());
			entity = (optional != null && optional.isPresent()) ? optional.get() : null;
			LogManager.writeLog(EnrolleeServiceImpl.class, "Enrollee from DB : " + entity, LoggingLevel.INFO);
			if (entity != null) {
				entity.setName(request.getName());
				entity.setDateOfBirth(DateUtils.convertStringToDate(request.getDateOfBirth(), ApplicationConstants.DATE_FORMAT_YYYY_MM_DD));
				entity.setPhoneNumber(request.getPhoneNumber());
				entity.setIsActive(request.isActivationStatus() ? ApplicationConstants.FLAG_YES : ApplicationConstants.FLAG_NO);
				entity.setUpdatedDate(LocalDateTime.now());
				LogManager.writeLog(EnrolleeServiceImpl.class, "Enrollee info going to update is: " + entity, LoggingLevel.INFO);
				repository.save(entity);
				LogManager.writeLog(EnrolleeServiceImpl.class, "Enrollee info updated successfully", LoggingLevel.INFO);
				response.setMessage("Enrollee info updated successfully");
				response.setResponseCode(ApplicationConstants.RESPONSE_CODE_SUCCESS_0000);
			} else {
				response.setMessage("Invalid enrollee id");
				response.setResponseCode(ApplicationConstants.RESPONSE_CODE_INVALID_INPUT);
				LogManager.writeLog(EnrolleeServiceImpl.class, "Invalid enrollee id", LoggingLevel.INFO);
			}
		} catch (Exception e) {
			LogManager.writeLog(EnrolleeServiceImpl.class, "Exception occured while updating enrollee",	LoggingLevel.ERROR);
			throw new EnrolleeServiceException("Exception occured while updating enrollee");
		}
		LogManager.writeLog(EnrolleeServiceImpl.class, "updateEnrollee() : End", LoggingLevel.INFO);
		return response;
	}

	@Override
	public BaseResponse deleteEnrollee(Long id) {
		LogManager.writeLog(EnrolleeServiceImpl.class, "deleteEnrollee() : Start", LoggingLevel.INFO);
		BaseResponse response = null;
		EnrolleeEntity entity = null;
		Optional<EnrolleeEntity> optional = null;
		try {
			response = new BaseResponse();
			optional = repository.findById(id);
			entity = (optional != null && optional.isPresent()) ? optional.get() : null;
			LogManager.writeLog(EnrolleeServiceImpl.class, "Enrollee from DB : " + entity, LoggingLevel.INFO);
			if (entity != null) {
				repository.deleteById(id);
				LogManager.writeLog(EnrolleeServiceImpl.class, "Enrollee removed successfully", LoggingLevel.INFO);
				response.setMessage("Enrollee removed successfully");
				response.setResponseCode(ApplicationConstants.RESPONSE_CODE_SUCCESS_0000);
			} else {
				response.setMessage("Invalid enrollee id");
				response.setResponseCode(ApplicationConstants.RESPONSE_CODE_INVALID_INPUT);
				LogManager.writeLog(EnrolleeServiceImpl.class, "Invalid enrollee id", LoggingLevel.INFO);
			}
		} catch (Exception e) {
			LogManager.writeLog(EnrolleeServiceImpl.class, "Exception occured while deleting enrollee",	LoggingLevel.ERROR);
			throw new EnrolleeServiceException("Exception occured while deleting enrollee");
		}
		LogManager.writeLog(EnrolleeServiceImpl.class, "deleteEnrollee() : End", LoggingLevel.INFO);
		return response;
	}

	@Override
	public GetEnrolleeResponse getEnrolleeDetails(Long id) {
		LogManager.writeLog(DependentServiceImpl.class, "Start", LoggingLevel.INFO);
		Enrollee enrollee = null;
		Dependent dependent = null;
		EnrolleeEntity entity = null;
		Boolean activationStatus = null;
		List<Dependent> dependents = null;
		GetEnrolleeResponse response = null;
		Optional<EnrolleeEntity> otional = null;
		List<DependentEntity> dependentEntities = null;
		try {
			response = new GetEnrolleeResponse();
			otional = repository.findById(id);
			entity = (otional != null && otional.isPresent()) ? otional.get() : null;
			if (entity != null) {
				enrollee = new Enrollee();
				BeanUtils.copyProperties(entity, enrollee);
				activationStatus = ApplicationConstants.FLAG_YES.equals(entity.getIsActive())? Boolean.TRUE : Boolean.FALSE;
				enrollee.setActivationStatus(activationStatus);
				dependentEntities = dependentRepository.findByEnrolleeId(enrollee.getId());
				if (dependentEntities != null && !dependentEntities.isEmpty()) {
					dependents = new ArrayList<>();
					for (DependentEntity dependentEntity : dependentEntities) {
						dependent = new Dependent();
						BeanUtils.copyProperties(dependentEntity, dependent);
						dependents.add(dependent);
					}
				} else {
					LogManager.writeLog(EnrolleeServiceImpl.class, "No dependent found for enrollee id : "+id, LoggingLevel.INFO);
				}
				enrollee.setDependents(dependents);
				response.setEnrollee(enrollee);
				response.setMessage(ApplicationConstants.RESPONSE_MSG_SUCCESS);
				response.setResponseCode(ApplicationConstants.RESPONSE_CODE_SUCCESS_0000);
			} else {
				response.setMessage(ApplicationConstants.RESPONSE_MSG_NO_RECORD_FOUND);
				response.setResponseCode(ApplicationConstants.RESPONSE_CODE_FAILURE_0001);
				LogManager.writeLog(EnrolleeServiceImpl.class, "No record found for enrollee id : "+id, LoggingLevel.INFO);
			}
		} catch (Exception e) {
			LogManager.writeLog(EnrolleeServiceImpl.class, "Exception occured while getting enrollee", LoggingLevel.ERROR);
			throw new EnrolleeServiceException("Exception occured while getting enrollee");
		}
		return response;
	}
}
