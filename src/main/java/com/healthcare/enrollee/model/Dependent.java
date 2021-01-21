package com.healthcare.enrollee.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Dependent implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long enrolleeId;
	
	private String name;

	private LocalDate dateOfBirth;

	private String createdBy;

	private LocalDateTime createdDate;

	private String updatedBy;

	private LocalDateTime updatedDate;
}
