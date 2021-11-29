package com.awards.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.awards.entities.AwardProgram;

public interface IAwardProgramRepository extends JpaRepository<AwardProgram, Long>{

	AwardProgram findByProgramId(Long programId);
	
	AwardProgram findByProgramIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long programId, LocalDate beginDate, LocalDate endDate);
	
}
