package com.healthcare.enrollee.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.enrollee.exception.EnrolleeServiceException;
import com.healthcare.enrollee.exception.InvalidInputException;
import com.healthcare.enrollee.model.Dependent;
import com.healthcare.enrollee.model.Enrollee;
import com.healthcare.enrollee.service.EnrolleeService;
import com.healthcare.enrollee.vo.AddEnrolleeRequest;
import com.healthcare.enrollee.vo.AddEnrolleeResponse;
import com.healthcare.enrollee.vo.GetEnrolleeResponse;
import com.healthcare.enrollee.vo.UpdateEnrolleeRequest;
import com.healthcare.enrollee.vo.UpdateEnrolleeResponse;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EnrolleeController.class)
public class EnrolleeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EnrolleeService enrolleeService;
	private GetEnrolleeResponse response;
	private AddEnrolleeRequest addenr;

	@Before
	public void setup() {
		response = new GetEnrolleeResponse();
		response.setMessage("success");
		response.setResponseCode("200");

		Enrollee enr = new Enrollee();
		enr.setActivationStatus(true);
		enr.setName("test");
		enr.setDateOfBirth(LocalDate.now());
		enr.setPhoneNumber("1234567890");
		enr.setUpdatedBy("testUpdate");
		enr.setUpdatedDate(LocalDateTime.now());
		enr.setCreatedBy("testCreate");
		enr.setCreatedDate(LocalDateTime.now());
		enr.setId(1l);
		Dependent dep = new Dependent();
		dep.setName("testName");
		dep.setCreatedBy("testCreate");
		dep.setCreatedDate(LocalDateTime.now());
		dep.setUpdatedBy("testUpdate");
		dep.setUpdatedDate(LocalDateTime.now());
		dep.setDateOfBirth(LocalDate.now());
		dep.setId(1l);
		dep.setEnrolleeId(1l);
		List<Dependent> dependents = new ArrayList<>();
		dependents.add(dep);
		enr.setDependents(dependents);
		response.setEnrollee(enr);
		
		addenr = new AddEnrolleeRequest();
		addenr.setActivationStatus(true);
		addenr.setDateOfBirth("2020-12-31");
		addenr.setName("test");
		addenr.setPhoneNumber("1234567890");
		addenr.setCreatedBy("test");
	}

	@Test
	public void getEnrolleeDetailsTest() throws Exception {

		Mockito.when(enrolleeService.getEnrolleeDetails(Mockito.anyLong())).thenReturn(response);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/enrollee/1").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(result.getResponse().getStatus(), (int) Integer.valueOf(response.getResponseCode()));
	}

	@Test
	public void addEnrolleeTest() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String req = mapper.writeValueAsString(addenr);

		AddEnrolleeResponse res = new AddEnrolleeResponse();
		res.setEnrolleeId(1l);
		res.setResponseCode("200");
		res.setMessage("success");
		Mockito.when(enrolleeService.addEnrollee(Mockito.any())).thenReturn(res);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/enrollee/add").accept(MediaType.APPLICATION_JSON)
				.content(req).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(result.getResponse().getStatus(), (int) Integer.valueOf(response.getResponseCode()));
	}

	@Test
	public void updateEnrolleeTest() throws Exception {
		UpdateEnrolleeRequest updateEnr = new UpdateEnrolleeRequest();
		updateEnr.setId(1l);
		updateEnr.setActivationStatus(true);
		updateEnr.setDateOfBirth("2020-12-31");
		updateEnr.setName("test");
		updateEnr.setPhoneNumber("1234567890");

		ObjectMapper mapper = new ObjectMapper();
		String req = mapper.writeValueAsString(updateEnr);

		UpdateEnrolleeResponse res = new UpdateEnrolleeResponse();
		res.setResponseCode("200");
		res.setMessage("success");
		Mockito.when(enrolleeService.updateEnrollee(Mockito.any())).thenReturn(res);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/enrollee/update").accept(MediaType.APPLICATION_JSON)
				.content(req).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(result.getResponse().getStatus(), (int) Integer.valueOf(response.getResponseCode()));
	}
	
	@Test
	public void deleteEnrolleeTest() throws Exception {
		UpdateEnrolleeResponse res = new UpdateEnrolleeResponse();
		res.setResponseCode("200");
		res.setMessage("success");
		Mockito.when(enrolleeService.deleteEnrollee(Mockito.any())).thenReturn(res);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/enrollee/1").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(result.getResponse().getStatus(), (int) Integer.valueOf(response.getResponseCode()));
	}
	
	
	/// Error test cases
	
	@Test
	public void getEnrolleeDetailsInternalServerError() throws Exception {
		response.setResponseCode("500");
		Mockito.when(enrolleeService.getEnrolleeDetails(Mockito.anyLong())).thenThrow(NullPointerException.class);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/enrollee/1").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(result.getResponse().getStatus(), (int) Integer.valueOf(response.getResponseCode()));
	}
	
	@Test
	public void addEnrolleeTestBadRequest() throws Exception {
		AddEnrolleeRequest addenr = new AddEnrolleeRequest();
		ObjectMapper mapper = new ObjectMapper();
		String req = mapper.writeValueAsString(addenr);
		Mockito.when(enrolleeService.addEnrollee(Mockito.any())).thenThrow(InvalidInputException.class);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/enrollee/add").accept(MediaType.APPLICATION_JSON)
				.content(req).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(result.getResponse().getStatus(), 400);
	}
	
	@Test
	public void addEnrolleeTestInvalidInputException() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String req = mapper.writeValueAsString(addenr);
		Mockito.when(enrolleeService.addEnrollee(Mockito.any())).thenThrow(InvalidInputException.class);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/enrollee/add").accept(MediaType.APPLICATION_JSON)
				.content(req).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(result.getResponse().getStatus(), 400);
	}
	
	@Test
	public void addEnrolleeTestEnrolleeServiceException() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String req = mapper.writeValueAsString(addenr);
		Mockito.when(enrolleeService.addEnrollee(Mockito.any())).thenThrow(EnrolleeServiceException.class);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/enrollee/add").accept(MediaType.APPLICATION_JSON)
				.content(req).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(result.getResponse().getStatus(), 500);
	}

}
