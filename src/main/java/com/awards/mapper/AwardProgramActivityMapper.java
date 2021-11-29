package com.awards.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.awards.dto.AwardProgramActivityDto;
import com.awards.entities.AwardProgramActivity;
import com.googlecode.jmapper.JMapper;

@Service
public class AwardProgramActivityMapper {

	private final JMapper<AwardProgramActivityDto, AwardProgramActivity> awardProgramActivityDtoMapper = new JMapper<>(
			AwardProgramActivityDto.class, AwardProgramActivity.class);

	private final JMapper<AwardProgramActivity, AwardProgramActivityDto> awardProgramActivityEntityMapper = new JMapper<>(
			AwardProgramActivity.class, AwardProgramActivityDto.class);

	public AwardProgramActivityDto getAwardProgramActivityDto(AwardProgramActivity awardProgramActivityEntity) {
		return awardProgramActivityDtoMapper.getDestination(awardProgramActivityEntity);
	}

	public List<AwardProgramActivityDto> getAwardProgramActivityDtos(
			List<AwardProgramActivity> awardProgramActivities) {
		return awardProgramActivities.stream().map(this::getAwardProgramActivityDto)
				.collect(Collectors.toList());
	}

	public AwardProgramActivity getCustomerEntity(AwardProgramActivityDto awardProgramActivityDto) {
		return awardProgramActivityEntityMapper.getDestination(awardProgramActivityDto);
	}

}
