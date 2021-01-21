package com.healthcare.enrollee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.enrollee.entity.DependentEntity;

@Repository
public interface DependentRepository extends JpaRepository<DependentEntity, Long> {

	public List<DependentEntity> findByEnrolleeId(Long id);
	
}
