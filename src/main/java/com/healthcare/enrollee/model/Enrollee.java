package com.healthcare.enrollee.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Enrollee implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private LocalDate dateOfBirth;

	private String phoneNumber;

	private boolean activationStatus;

	private String createdBy;

	private LocalDateTime createdDate;

	private String updatedBy;

	private LocalDateTime updatedDate;

	private List<Dependent> dependents;
}
