package com.healthcare.enrollee.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.healthcare.enrollee.entity.EnrolleeEntity;
import com.healthcare.enrollee.repository.DependentRepository;
import com.healthcare.enrollee.repository.EnrolleeRepository;
import com.healthcare.enrollee.serviceImpl.EnrolleeServiceImpl;
import com.healthcare.enrollee.util.ApplicationConstants;
import com.healthcare.enrollee.util.DateUtils;
import com.healthcare.enrollee.vo.AddEnrolleeRequest;
import com.healthcare.enrollee.vo.AddEnrolleeResponse;
import com.healthcare.enrollee.vo.BaseResponse;
import com.healthcare.enrollee.vo.UpdateEnrolleeRequest;
import com.healthcare.enrollee.vo.UpdateEnrolleeResponse;

@RunWith(SpringRunner.class)
public class EnrolleeServiceImplTest {

	@Mock
	private DependentRepository dependentRepository;

	@InjectMocks
	private EnrolleeServiceImpl enrolleeServiceImpl;

	@Mock
	private EnrolleeRepository repository;

	@Test
	public void addEnrolleeTest() {
		AddEnrolleeRequest request = new AddEnrolleeRequest();
		request.setActivationStatus(true);
		request.setDateOfBirth("2020-12-31");
		request.setName("test");
		request.setPhoneNumber("1234567890");
		request.setCreatedBy("test");

		EnrolleeEntity entity = new EnrolleeEntity();
		entity.setId(1l);
		entity.setName(request.getName());
		entity.setDateOfBirth(
				DateUtils.convertStringToDate(request.getDateOfBirth(), ApplicationConstants.DATE_FORMAT_YYYY_MM_DD));
		entity.setPhoneNumber(request.getPhoneNumber());
		entity.setIsActive(ApplicationConstants.FLAG_YES);
		entity.setCreatedBy(request.getCreatedBy());
		Mockito.when(repository.save(Mockito.any())).thenReturn(entity);
		AddEnrolleeResponse response = enrolleeServiceImpl.addEnrollee(request);
		assertNotNull(response);
		assertEquals(response.getEnrolleeId(), entity.getId());
	}

	@Test(expected = Exception.class)
	public void addEnrolleeTestException() {
		AddEnrolleeRequest request = new AddEnrolleeRequest();
		Mockito.when(repository.save(Mockito.any())).thenReturn(request);
		enrolleeServiceImpl.addEnrollee(request);
	}

	@Test
	public void updateEnrolleeTest() {
		UpdateEnrolleeRequest request = new UpdateEnrolleeRequest();
		request.setId(1l);
		request.setActivationStatus(true);
		request.setDateOfBirth("2020-12-31");
		request.setName("test");
		request.setPhoneNumber("1234567890");

		EnrolleeEntity entity = new EnrolleeEntity();
		entity.setId(1l);
		entity.setName(request.getName());
		entity.setDateOfBirth(
				DateUtils.convertStringToDate(request.getDateOfBirth(), ApplicationConstants.DATE_FORMAT_YYYY_MM_DD));
		entity.setPhoneNumber(request.getPhoneNumber());
		entity.setIsActive(ApplicationConstants.FLAG_YES);
		entity.setUpdatedBy("test");
		
		Optional<EnrolleeEntity> optional = Optional.of(entity);
		Mockito.when(repository.findById(Mockito.any())).thenReturn(optional);
		Mockito.when(repository.save(Mockito.any())).thenReturn(entity);
		UpdateEnrolleeResponse response = enrolleeServiceImpl.updateEnrollee(request);
		assertNotNull(response);
		assertEquals("Enrollee info updated successfully", response.getMessage());
	}
	
	@Test
	public void updateEnrolleeTestNotFound() {
		UpdateEnrolleeRequest request = new UpdateEnrolleeRequest();
		request.setId(1l);
		request.setActivationStatus(true);
		request.setDateOfBirth("2020-12-31");
		request.setName("test");
		request.setPhoneNumber("1234567890");
		Mockito.when(repository.findById(Mockito.any())).thenReturn(null);
		UpdateEnrolleeResponse response = enrolleeServiceImpl.updateEnrollee(request);
		assertNotNull(response);
		assertEquals("Invalid enrollee id", response.getMessage());
	}
	
	@Test
	public void deleteEnrolleeTest() {
		EnrolleeEntity entity = new EnrolleeEntity();
		entity.setId(1l);
		entity.setName("test");
		entity.setDateOfBirth(
				DateUtils.convertStringToDate("2020-12-31", ApplicationConstants.DATE_FORMAT_YYYY_MM_DD));
		entity.setPhoneNumber("1234567890");
		entity.setIsActive(ApplicationConstants.FLAG_YES);
		entity.setUpdatedBy("test");
		
		Optional<EnrolleeEntity> optional = Optional.of(entity);
		Mockito.when(repository.findById(Mockito.any())).thenReturn(optional);
		doNothing().when(repository).deleteById(Mockito.anyLong());
		BaseResponse response = enrolleeServiceImpl.deleteEnrollee(1l);
		assertNotNull(response);
		assertEquals("Enrollee removed successfully", response.getMessage());
	}
	
	@Test
	public void deleteEnrolleeTestNotFound() {
		Mockito.when(repository.findById(Mockito.any())).thenReturn(null);
		BaseResponse response = enrolleeServiceImpl.deleteEnrollee(1l);
		assertNotNull(response);
		assertEquals("Invalid enrollee id", response.getMessage());
	}
	
	@Test(expected = Exception.class)
	public void deleteEnrolleeTestException() {
		Mockito.when(repository.findById(Mockito.any())).thenThrow(NullPointerException.class);
		enrolleeServiceImpl.deleteEnrollee(1l);
	}
	
	@Test
	public void getEnrolleeDetailsTest() {
		EnrolleeEntity entity = new EnrolleeEntity();
		entity.setId(1l);
		entity.setName("test");
		entity.setDateOfBirth(
				DateUtils.convertStringToDate("2020-12-31", ApplicationConstants.DATE_FORMAT_YYYY_MM_DD));
		entity.setPhoneNumber("1234567890");
		entity.setIsActive(ApplicationConstants.FLAG_YES);
		entity.setUpdatedBy("test");
		
		Optional<EnrolleeEntity> optional = Optional.of(entity);
		Mockito.when(repository.findById(Mockito.any())).thenReturn(optional);
		BaseResponse response = enrolleeServiceImpl.getEnrolleeDetails(1l);
		assertNotNull(response);
		assertEquals("SUCCESS", response.getMessage());
	}
	
	@Test
	public void getEnrolleeDetailsTestNotFound() {
		Mockito.when(repository.findById(Mockito.any())).thenReturn(null);
		BaseResponse response = enrolleeServiceImpl.getEnrolleeDetails(1l);
		assertNotNull(response);
		assertEquals("No record found", response.getMessage());
	}
	
	@Test(expected = Exception.class)
	public void getEnrolleeDetailsTestException() {
		Mockito.when(repository.findById(Mockito.any())).thenThrow(NullPointerException.class);
		enrolleeServiceImpl.getEnrolleeDetails(1l);
	}
	
	
}
