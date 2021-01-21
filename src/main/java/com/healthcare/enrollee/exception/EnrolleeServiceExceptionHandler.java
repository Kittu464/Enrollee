package com.healthcare.enrollee.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.healthcare.enrollee.logging.LogManager;
import com.healthcare.enrollee.logging.LoggingLevel;
import com.healthcare.enrollee.util.ApplicationConstants;
import com.healthcare.enrollee.vo.ErrorResponse;

@RestControllerAdvice
public class EnrolleeServiceExceptionHandler {

	@ExceptionHandler(InvalidInputException.class)
	public final ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException ex) {
		ErrorResponse response = new ErrorResponse();
		response.setMessage(ex.getMessage());
		response.setResponseCode(ApplicationConstants.RESPONSE_CODE_INVALID_INPUT);
		LogManager.writeLog(EnrolleeServiceExceptionHandler.class,
				"handleInvalidInputException() : Response : " + response, LoggingLevel.ERROR);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EnrolleeServiceException.class)
	public final ResponseEntity<ErrorResponse> handleEnrolleeServiceException(EnrolleeServiceException ex) {
		ErrorResponse response = new ErrorResponse();
		response.setMessage(ex.getMessage());
		response.setResponseCode(ApplicationConstants.RESPONSE_CODE_FAILURE_0001);
		LogManager.writeLog(EnrolleeServiceExceptionHandler.class,
				"handleEnrolleeServiceException() : Response : " + response, LoggingLevel.ERROR);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		ErrorResponse response = new ErrorResponse();
		response.setErrors(errors);
		response.setMessage(ApplicationConstants.RESPONSE_MSG_INVALID_INPUT);
		response.setResponseCode(ApplicationConstants.RESPONSE_CODE_INVALID_INPUT);
		LogManager.writeLog(EnrolleeServiceExceptionHandler.class,
				"handleValidationException() : Response : " + response, LoggingLevel.ERROR);
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public final ResponseEntity<ErrorResponse>handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
		String name = ex.getName();
	    String type = ex.getRequiredType().getSimpleName();
	    Object value = ex.getValue();
	    String message = String.format("'%s' should be a '%s' value and '%s' isn't  a '%s'", name, type, value, type);
	    ErrorResponse response = new ErrorResponse();
	    response.setMessage(message);
	    response.setResponseCode(ApplicationConstants.RESPONSE_CODE_INVALID_INPUT);
	    LogManager.writeLog(EnrolleeServiceExceptionHandler.class,
				"handleMethodArgumentTypeMismatchException() : Response : " + response, LoggingLevel.ERROR);
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
		ErrorResponse response = new ErrorResponse();
		response.setMessage(ApplicationConstants.RESPONSE_MSG_INTERNAL_SERVER_ERROR);
		response.setResponseCode(ApplicationConstants.RESPONSE_CODE_FAILURE_0001);
		LogManager.writeLog(EnrolleeServiceExceptionHandler.class,
				"handleAllExceptions() : Response : " + response, LoggingLevel.ERROR);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
