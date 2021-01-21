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
@Table(name = "ENROLLEE")
public class EnrolleeEntity {

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "enrollee-id-generator", sequenceName = "ENROLLEE_ID_SEQUENCE", allocationSize = 1)
	@GeneratedValue(generator = "enrollee-id-generator", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DATE_OF_BIRTH")
	private LocalDate dateOfBirth;

	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;

	@Column(name = "IS_ACTIVE")
	private String isActive;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DATE", updatable = false)
	private LocalDateTime createdDate = LocalDateTime.now();

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATED_DATE", updatable = true)
	private LocalDateTime updatedDate = LocalDateTime.now();
}
