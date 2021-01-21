package com.healthcare.enrollee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.enrollee.entity.EnrolleeEntity;

@Repository
public interface EnrolleeRepository extends JpaRepository<EnrolleeEntity, Long> {

}
