package com.healthcare.enrollee.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "ENROLLEE_DEPENDENTS")
public class DependentEntity {

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "dependents-id-generator", sequenceName = "ENROLLEE_DEPENDENT_ID_SEQUENCE", allocationSize = 1)
	@GeneratedValue(generator = "dependents-id-generator", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DATE_OF_BIRTH")
	private LocalDate dateOfBirth;

	@Column(name = "ENROLLEE_ID")
	private Long enrolleeId;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DATE", updatable = false)
	private LocalDateTime createdDate = LocalDateTime.now();

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATED_DATE", updatable = true)
	private LocalDateTime updatedDate = LocalDateTime.now();
}
