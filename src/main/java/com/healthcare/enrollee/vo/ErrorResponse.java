package com.healthcare.enrollee.vo;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String responseCode;

	private String message;

	private Map<String, String> errors;
}
