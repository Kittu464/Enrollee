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
import com.healthcare.enrollee.service.DependentService;
import com.healthcare.enrollee.vo.AddDependentRequest;
import com.healthcare.enrollee.vo.AddDependentResponse;
import com.healthcare.enrollee.vo.BaseResponse;
import com.healthcare.enrollee.vo.GetDependentResponse;
import com.healthcare.enrollee.vo.UpdateDependentRequest;
import com.healthcare.enrollee.vo.UpdateDependentResponse;

@RestController
@RequestMapping("/dependent")
public class DependentController {

	@Autowired
	private DependentService dependentService;

	@PostMapping(value = "/add")
	public ResponseEntity<AddDependentResponse> addDependent(@Valid @RequestBody AddDependentRequest request) {
		LogManager.writeLog(DependentController.class, "addDependent() : Request : " + request, LoggingLevel.INFO);
		AddDependentResponse response = dependentService.addDependent(request);
		LogManager.writeLog(DependentController.class, "addDependent() : Response : " + response, LoggingLevel.INFO);
		return new ResponseEntity<AddDependentResponse>(response, HttpStatus.OK);
	}

	@PutMapping(value="/update")
	public ResponseEntity<UpdateDependentResponse> updateDependent(@Valid @RequestBody UpdateDependentRequest request) {
		LogManager.writeLog(DependentController.class, "updateDependent() : Request : " + request, LoggingLevel.INFO);
		UpdateDependentResponse response = dependentService.updateDependent(request);
		LogManager.writeLog(DependentController.class, "updateDependent() : Response : " + response, LoggingLevel.INFO);
		return new ResponseEntity<UpdateDependentResponse>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<BaseResponse> deleteDependent(
			@Valid @NotNull @PathVariable(name = "id", required = true) Long id) {
		LogManager.writeLog(DependentController.class, "deleteDependent() : Request Id : " + id, LoggingLevel.INFO);
		BaseResponse response = dependentService.deleteDependent(id);
		LogManager.writeLog(DependentController.class, "deleteDependent() : Response : " + response, LoggingLevel.INFO);
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<GetDependentResponse> getDependent(
			@Valid @NotNull @PathVariable(name = "id", required = true) Long id) {
		LogManager.writeLog(DependentController.class, "getDependent() : Request Id : " + id, LoggingLevel.INFO);
		GetDependentResponse response = dependentService.getDependent(id);
		LogManager.writeLog(DependentController.class, "getDependent() : Response : " + response, LoggingLevel.INFO);
		return new ResponseEntity<GetDependentResponse>(response, HttpStatus.OK);
	}

}
