package com.awards.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awards.dto.AwardProgramActivityDto;
import com.awards.dto.CustomerProgramActivityTotalAndMonthlyDto;
import com.awards.dto.CustomerProgramDto;
import com.awards.dto.IAwardProgramActivityGroupDto;
import com.awards.dto.TransactionDto;
import com.awards.entities.AwardProgram;
import com.awards.entities.AwardProgramRule;
import com.awards.mapper.AwardProgramActivityMapper;
import com.awards.repository.IAwardProgramActivityRepository;
import com.awards.repository.IAwardProgramRepository;
import com.awards.repository.ICustomerProgramRepository;

@Service
public class AwardService {

	@Autowired
	ICustomerProgramRepository repo;

	@Autowired
	IAwardProgramRepository awardProgramRepository;

	@Autowired
	CustomerService customerService;

	@Autowired
	TransactionService transactionService;

	@Autowired
	AwardProgramActivityMapper awardProgramActivityMapper;

	@Autowired
	IAwardProgramActivityRepository awardProgramActivityRepository;

	/**
	 * 
	 * @param customerId
	 * @param awardRules
	 * @param transaction
	 * @return
	 * 
	 * Calculate awards for a particular transaction using all the award rules
	 * present in rules table against loyalty program
	 * It creates an award activity for each earning per transaction.
	 * 
	 * Note: it can be more flexible/may be use a certain rule engine.
	 */

	public BigDecimal calculateAwardPoints(Long customerId, List<AwardProgramRule> awardRules,
			TransactionDto transaction) {
		Function<AwardProgramRule, BigDecimal> totalAwardsMapper = awardProgramRule -> {
			if (transaction.getSubtotal().compareTo(awardProgramRule.getMinimumAmt()) >= 0) {
				BigDecimal calculatedAwards = BigDecimal.valueOf(awardProgramRule.getMultiplier())
						.multiply(transaction.getSubtotal().subtract(awardProgramRule.getMinimumAmt()));
				calculatedAwards = calculatedAwards.setScale(2, RoundingMode.HALF_EVEN);
				if (awardProgramRule.getMaximumPts().intValue() != -1
						&& calculatedAwards.compareTo(awardProgramRule.getMaximumPts()) > 0) {
					return awardProgramRule.getMaximumPts();
				} else {
					return calculatedAwards;
				}
			}
			return BigDecimal.ZERO;
		};
		BigDecimal totalPts = awardRules.stream().map(totalAwardsMapper).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		if (totalPts.compareTo(BigDecimal.ZERO) > 0) {
			AwardProgramActivityDto awardProgramActivityDto = new AwardProgramActivityDto();
			awardProgramActivityDto.setCustomerId(customerId);
			awardProgramActivityDto.setPointsEarned(totalPts);
			awardProgramActivityDto.setProgramId(awardRules.get(0).getProgramId());
			awardProgramActivityDto.setCreateUser("Admin");
			awardProgramActivityDto.setCreateDate(transaction.getCreateDate());
			awardProgramActivityMapper.getAwardProgramActivityDto(awardProgramActivityRepository
					.save(awardProgramActivityMapper.getCustomerEntity(awardProgramActivityDto)));
		}
		
		transactionService.markTransactionAsPosted(transaction);
		return totalPts;
	}

	/**
	 * 
	 * @param customerPrograms
	 * @return
	 * 
	 * Returns a list of all the rules from all the associated loyalty programs
	 * 
	 */
	public List<AwardProgramRule> getAwardRules(List<CustomerProgramDto> customerPrograms) {
		List<AwardProgramRule> rules = new ArrayList<>();
		LocalDate currentDate = LocalDate.now();
		for (CustomerProgramDto customerProgram : customerPrograms) {
			AwardProgram awardProgram = getAwardProgram(customerProgram.getProgramId(), currentDate, currentDate);
			if (awardProgram != null && !awardProgram.getRules().isEmpty()) {
				rules.addAll(awardProgram.getRules());
			}
		}
		return rules;
	}

	public Map<Long, CustomerProgramActivityTotalAndMonthlyDto> getAwardsGroupByMonth(Long customerId) {
		List<IAwardProgramActivityGroupDto> awardProgramActivityGroupDtos = this.awardProgramActivityRepository
				.groupAwardProgramActivityByMonthYear(customerId);
		return getAggregatedAwards(awardProgramActivityGroupDtos);
	}
	
	public Map<Long, CustomerProgramActivityTotalAndMonthlyDto> getAllAwardsGroupByMonth() {
		List<IAwardProgramActivityGroupDto> awardProgramActivityGroupDtos = this.awardProgramActivityRepository
				.groupAwardProgramActivityByMonthYearAllCustomers();
		return getAggregatedAwards(awardProgramActivityGroupDtos);
	}

	/**
	 * 
	 * @param awardProgramActivityGroupDtos
	 * @return
	 * 
	 * First it groups all award activities by customer and then individual customer
	 * awards are grouped by month of award creation
	 */
	
	private Map<Long, CustomerProgramActivityTotalAndMonthlyDto> getAggregatedAwards(
			List<IAwardProgramActivityGroupDto> awardProgramActivityGroupDtos) {

		Map<Long, CustomerProgramActivityTotalAndMonthlyDto> result = new LinkedHashMap<>();

		Map<Long, List<IAwardProgramActivityGroupDto>> groupByCustomer = awardProgramActivityGroupDtos.stream()
				.collect(Collectors.groupingBy(IAwardProgramActivityGroupDto::getcustomerId, Collectors.toList()));

		groupByCustomer.entrySet().stream().forEach(customerActivity -> {

			Map<YearMonth, BigDecimal> monthGroup = customerActivity.getValue().stream().collect(Collectors.groupingBy(
					IAwardProgramActivityGroupDto::getYearMonth, LinkedHashMap::new,
					Collectors.reducing(BigDecimal.ZERO, IAwardProgramActivityGroupDto::getPoints, BigDecimal::add)));

			BigDecimal total = customerActivity.getValue().stream().map(IAwardProgramActivityGroupDto::getPoints)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			result.put(customerActivity.getKey(),
					new CustomerProgramActivityTotalAndMonthlyDto(customerActivity.getKey(), total, monthGroup));

		});
		return result;
	}

	/**
	 * 
	 * @param programId
	 * @param beginDate
	 * @param endDate
	 * @return
	 * 
	 * Return the loyalty program if any only if
	 * program has started already and have not expired
	 */
	private AwardProgram getAwardProgram(Long programId, LocalDate beginDate, LocalDate endDate) {
		return awardProgramRepository.findByProgramIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(programId,
				beginDate, endDate);
	}
}




















