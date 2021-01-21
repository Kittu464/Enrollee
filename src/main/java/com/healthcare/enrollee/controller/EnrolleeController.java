package com.healthcare.enrollee.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.enrollee.logging.LogManager;
import com.healthcare.enrollee.logging.LoggingLevel;
import com.healthcare.enrollee.service.EnrolleeService;
import com.healthcare.enrollee.vo.AddEnrolleeRequest;
import com.healthcare.enrollee.vo.AddEnrolleeResponse;
import com.healthcare.enrollee.vo.BaseResponse;
import com.healthcare.enrollee.vo.GetEnrolleeResponse;
import com.healthcare.enrollee.vo.UpdateEnrolleeRequest;
import com.healthcare.enrollee.vo.UpdateEnrolleeResponse;

@RestController
@RequestMapping("/enrollee")
public class EnrolleeController {

	@Autowired
	private EnrolleeService enrolleeService;

	@PostMapping(value ="/add")
	public ResponseEntity<AddEnrolleeResponse> addEnrollee(@Valid @RequestBody AddEnrolleeRequest request) {
		LogManager.writeLog(EnrolleeController.class, "addEnrollee() : Request : " + request, LoggingLevel.INFO);
		AddEnrolleeResponse response = enrolleeService.addEnrollee(request);
		LogManager.writeLog(EnrolleeController.class, "addEnrollee() : Response : " + response, LoggingLevel.INFO);
		return new ResponseEntity<AddEnrolleeResponse>(response, HttpStatus.OK);
	}

	@PutMapping(value ="/update")
	public ResponseEntity<UpdateEnrolleeResponse> updateEnrollee(@Valid @RequestBody UpdateEnrolleeRequest request) {
		LogManager.writeLog(EnrolleeController.class, "updateEnrollee() : Request : " + request, LoggingLevel.INFO);
		UpdateEnrolleeResponse response = enrolleeService.updateEnrollee(request);
		LogManager.writeLog(EnrolleeController.class, "updateEnrollee() : Response : " + response, LoggingLevel.INFO);
		return new ResponseEntity<UpdateEnrolleeResponse>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<BaseResponse> deleteEnrollee(@PathVariable(name = "id", required = true) Long id) {
		LogManager.writeLog(EnrolleeController.class, "deleteEnrollee() : Request Id : " + id, LoggingLevel.INFO);
		BaseResponse response = enrolleeService.deleteEnrollee(id);
		LogManager.writeLog(EnrolleeController.class, "deleteEnrollee() : Response : " + response, LoggingLevel.INFO);
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<GetEnrolleeResponse> getEnrolleeDetails(@Valid @NotNull @PathVariable(name = "id", required = true) Long id) {
		LogManager.writeLog(DependentController.class, "getEnrolleeDetails() : Request Id : " + id, LoggingLevel.INFO);
		GetEnrolleeResponse response = enrolleeService.getEnrolleeDetails(id);
		LogManager.writeLog(DependentController.class, "getEnrolleeDetails() : Response : " + response, LoggingLevel.INFO);
		return new ResponseEntity<GetEnrolleeResponse>(response, HttpStatus.OK);
	}
	
	
}
