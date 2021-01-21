package com.healthcare.enrollee.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
import com.healthcare.enrollee.model.Dependent;
import com.healthcare.enrollee.service.DependentService;
import com.healthcare.enrollee.vo.AddDependentRequest;
import com.healthcare.enrollee.vo.AddDependentResponse;
import com.healthcare.enrollee.vo.GetDependentResponse;
import com.healthcare.enrollee.vo.UpdateDependentRequest;
import com.healthcare.enrollee.vo.UpdateDependentResponse;

@RunWith(SpringRunner.class)
@WebMvcTest(value = DependentController.class)
public class DependentControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DependentService dependentService;
	private GetDependentResponse response;
	
	@Before
	public void setup() {
		response = new GetDependentResponse();
		response.setMessage("success");
		response.setResponseCode("200");
		
		Dependent dep = new Dependent();
		dep.setName("testName");
		dep.setCreatedBy("testCreate");
		dep.setCreatedDate(LocalDateTime.now());
		dep.setUpdatedBy("testUpdate");
		dep.setUpdatedDate(LocalDateTime.now());
		dep.setDateOfBirth(LocalDate.now());
		dep.setId(1l);
		dep.setEnrolleeId(1l);
		
		response.setDependent(dep);
    }
	
	@Test
	public void getDependentTest() throws Exception {

		Mockito.when(dependentService.getDependent(Mockito.anyLong())).thenReturn(response);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/dependent/3").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(result.getResponse().getStatus(), (int) Integer.valueOf(response.getResponseCode()));
	}
	
	@Test
	public void getDependentInternalServerError() throws Exception {
		response.setResponseCode("500");
		Mockito.when(dependentService.getDependent(Mockito.anyLong())).thenThrow(NullPointerException.class);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/dependent/3").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(result.getResponse().getStatus(), (int) Integer.valueOf(response.getResponseCode()));
	}
	
	@Test
	public void addDependentTest() throws Exception {
		AddDependentRequest adddep = new AddDependentRequest();
		adddep.setName("test");
		adddep.setDateOfBirth("2020-01-18");
		adddep.setEnrolleeId(1l);

		ObjectMapper mapper = new ObjectMapper();
		String req = mapper.writeValueAsString(adddep);

		AddDependentResponse res = new AddDependentResponse();
		res.setDependentId(1l);
		res.setResponseCode("200");
		res.setMessage("success");
		
		Mockito.when(dependentService.addDependent(Mockito.any())).thenReturn(res);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/dependent/add").accept(MediaType.APPLICATION_JSON)
				.content(req).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(result.getResponse().getStatus(), (int) Integer.valueOf(response.getResponseCode()));

	}
	
	@Test
	public void updateDependentTest() throws Exception {
		UpdateDependentRequest updateDep = new UpdateDependentRequest();
		updateDep.setId(1l);
		updateDep.setName("test");
		updateDep.setDateOfBirth("2020-11-11");


		ObjectMapper mapper = new ObjectMapper();
		String req = mapper.writeValueAsString(updateDep);

		UpdateDependentResponse res = new UpdateDependentResponse();
		res.setResponseCode("200");
		res.setMessage("success");
		Mockito.when(dependentService.updateDependent(Mockito.any())).thenReturn(res);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/dependent/update").accept(MediaType.APPLICATION_JSON)
				.content(req).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(result.getResponse().getStatus(), (int) Integer.valueOf(response.getResponseCode()));
	}
	
	@Test
	public void deleteDependentTest() throws Exception {

		UpdateDependentResponse res = new UpdateDependentResponse();
		res.setResponseCode("200");
		res.setMessage("success");
		Mockito.when(dependentService.deleteDependent(Mockito.any())).thenReturn(res);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/dependent/1").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(result.getResponse().getStatus(), (int) Integer.valueOf(response.getResponseCode()));
	}

}
