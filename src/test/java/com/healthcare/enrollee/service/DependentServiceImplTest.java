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

import com.healthcare.enrollee.entity.DependentEntity;
import com.healthcare.enrollee.entity.EnrolleeEntity;
import com.healthcare.enrollee.repository.DependentRepository;
import com.healthcare.enrollee.repository.EnrolleeRepository;
import com.healthcare.enrollee.serviceImpl.DependentServiceImpl;
import com.healthcare.enrollee.util.ApplicationConstants;
import com.healthcare.enrollee.util.DateUtils;
import com.healthcare.enrollee.vo.AddDependentRequest;
import com.healthcare.enrollee.vo.AddDependentResponse;
import com.healthcare.enrollee.vo.BaseResponse;
import com.healthcare.enrollee.vo.GetDependentResponse;
import com.healthcare.enrollee.vo.UpdateDependentRequest;
import com.healthcare.enrollee.vo.UpdateDependentResponse;

@RunWith(SpringRunner.class)
public class DependentServiceImplTest {

	@Mock
	private DependentRepository dependentRepository;

	@InjectMocks
	private DependentServiceImpl dependentServiceImpl;

	@Mock
	private EnrolleeRepository repository;

	@Test
	public void addDependentTest() {
		AddDependentRequest request = new AddDependentRequest();
		request.setDateOfBirth("2020-12-31");
		request.setName("test");
		request.setEnrolleeId(1l);

		EnrolleeEntity entity = new EnrolleeEntity();
		entity.setId(1l);
		entity.setName(request.getName());
		entity.setDateOfBirth(
				DateUtils.convertStringToDate(request.getDateOfBirth(), ApplicationConstants.DATE_FORMAT_YYYY_MM_DD));
		entity.setIsActive(ApplicationConstants.FLAG_YES);
		Optional<EnrolleeEntity> optional = Optional.of(entity);
		Mockito.when(repository.findById(Mockito.any())).thenReturn(optional);

		DependentEntity dependentEntity = new DependentEntity();
		dependentEntity.setId(1l);
		dependentEntity.setName(request.getName());
		dependentEntity.setDateOfBirth(
				DateUtils.convertStringToDate(request.getDateOfBirth(), ApplicationConstants.DATE_FORMAT_YYYY_MM_DD));
		dependentEntity.setEnrolleeId(request.getEnrolleeId());
		Mockito.when(dependentRepository.save(Mockito.any())).thenReturn(dependentEntity);
		AddDependentResponse response = dependentServiceImpl.addDependent(request);
		assertNotNull(response);
		assertEquals(response.getDependentId(), entity.getId());
	}

	@Test
	public void updateDependentTest() {
		UpdateDependentRequest request = new UpdateDependentRequest();
		request.setId(1l);
		request.setDateOfBirth("2020-12-31");
		request.setName("test");

		DependentEntity dependentEntity = new DependentEntity();
		dependentEntity.setId(1l);
		dependentEntity.setName(request.getName());
		dependentEntity.setDateOfBirth(
				DateUtils.convertStringToDate(request.getDateOfBirth(), ApplicationConstants.DATE_FORMAT_YYYY_MM_DD));
		Optional<DependentEntity> optional = Optional.of(dependentEntity);
		Mockito.when(dependentRepository.findById(Mockito.any())).thenReturn(optional);

		Mockito.when(dependentRepository.save(Mockito.any())).thenReturn(dependentEntity);
		UpdateDependentResponse response = dependentServiceImpl.updateDependent(request);
		assertNotNull(response);
		assertEquals("Dependent info updated successfully", response.getMessage());
	}

	@Test
	public void deleteDependentTest() {
		DependentEntity dependentEntity = new DependentEntity();
		dependentEntity.setId(1l);
		dependentEntity.setName("test");
		dependentEntity.setDateOfBirth(
				DateUtils.convertStringToDate("2020-12-31", ApplicationConstants.DATE_FORMAT_YYYY_MM_DD));
		Optional<DependentEntity> optional = Optional.of(dependentEntity);
		Mockito.when(dependentRepository.findById(Mockito.any())).thenReturn(optional);

		doNothing().when(dependentRepository).deleteById(Mockito.any());
		BaseResponse response = dependentServiceImpl.deleteDependent(1l);
		assertNotNull(response);
		assertEquals("Dependent removed successfully", response.getMessage());
	}
	@Test
	public void getDependentTest() {
		DependentEntity dependentEntity = new DependentEntity();
		dependentEntity.setId(1l);
		dependentEntity.setName("test");
		dependentEntity.setDateOfBirth(
				DateUtils.convertStringToDate("2020-12-31", ApplicationConstants.DATE_FORMAT_YYYY_MM_DD));
		Optional<DependentEntity> optional = Optional.of(dependentEntity);
		Mockito.when(dependentRepository.findById(Mockito.any())).thenReturn(optional);

		GetDependentResponse response = dependentServiceImpl.getDependent(1l);
		assertNotNull(response);
		assertEquals(response.getDependent().getName(), dependentEntity.getName());
	}
}
