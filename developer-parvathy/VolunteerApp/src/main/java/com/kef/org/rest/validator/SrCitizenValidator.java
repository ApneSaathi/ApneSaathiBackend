package com.kef.org.rest.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.kef.org.rest.domain.model.SrCitizenCsvVO;
import com.kef.org.rest.exception.CSVValidationException;
import com.kef.org.rest.utils.Constants;
import com.kef.org.rest.utils.Constants.SrCitizenCSVColumns;

public class SrCitizenValidator {

	private SrCitizenValidator() {
	}

	public static boolean isValid(SrCitizenCsvVO srCitizen, Set<String> allMobileNumbers) {
		List<String> failureReasons = new ArrayList<>();
		boolean isValid = true;
		if (StringUtils.isEmpty(srCitizen.getFirstName())) {
			failureReasons.add(Constants.FIRST_NAME_IS_EMPTY);
			isValid = false;
		}
		if (StringUtils.isEmpty(srCitizen.getLastName())) {
			failureReasons.add(Constants.LAST_NAME_IS_EMPTY);
			isValid = false;
		}
		if (StringUtils.isEmpty(srCitizen.getEmail())) {
			failureReasons.add(Constants.EMAIL_IS_EMPTY);
			isValid = false;
		}
		if (StringUtils.isEmpty(srCitizen.getGender())) {
			failureReasons.add(Constants.GENDER_IS_EMPTY);
			isValid = false;
		} else if (srCitizen.getGender().length() != 1 || !(srCitizen.getGender().equalsIgnoreCase(Constants.MALE)
				|| srCitizen.getGender().equalsIgnoreCase(Constants.FEMALE))) {
			failureReasons.add(Constants.GENDER_MUST_BE_M_OR_F);
			isValid = false;
		}
		if (StringUtils.isEmpty(srCitizen.getAge())) {
			failureReasons.add(Constants.AGE_IS_EMPTY);
			isValid = false;
		} else if (!StringUtils.isNumeric(srCitizen.getAge())) {
			failureReasons.add(Constants.AGE_SHOULD_BE_NUMERIC);
			isValid = false;
		}
		if (StringUtils.isEmpty(srCitizen.getState())) {
			failureReasons.add(Constants.STATE_IS_EMPTY);
			isValid = false;
		}
		if (StringUtils.isEmpty(srCitizen.getDistrict())) {
			failureReasons.add(Constants.DISTRICT_IS_EMPTY);
			isValid = false;
		}
		if (StringUtils.isEmpty(srCitizen.getBlock())) {
			failureReasons.add(Constants.BLOCK_IS_EMPTY);
			isValid = false;
		}
		if (StringUtils.isEmpty(srCitizen.getAddress())) {
			failureReasons.add(Constants.ADDRESS_IS_EMPTY);
			isValid = false;
		}
		if (StringUtils.isEmpty(srCitizen.getVillage())) {
			failureReasons.add(Constants.VILLAGE_IS_EMPTY);
			isValid = false;
		}
		if (StringUtils.isEmpty(srCitizen.getStatus())) {
			failureReasons.add(Constants.STATUS_IS_EMPTY);
			isValid = false;
		}
		if (StringUtils.isEmpty(srCitizen.getReferredBy())) {
			failureReasons.add(Constants.REFERRED_BY_IS_EMPTY);
			isValid = false;
		}
		if (StringUtils.isEmpty(srCitizen.getVolunteerId())) {
			failureReasons.add(Constants.CREATED_BY_VOLUNTEER_ID_IS_EMPTY);
			isValid = false;
		} else if (!StringUtils.isNumeric(srCitizen.getVolunteerId())) {
			failureReasons.add(Constants.CREATED_BY_VOLUNTEER_ID_SHOULD_BE_NUMERIC);
			isValid = false;
		}
		// mobile check has to be at last
		if (StringUtils.isEmpty(srCitizen.getMobileNo())) {
			failureReasons.add(Constants.MOBILE_NUMBER_IS_EMPTY);
			isValid = false;
		} else if (allMobileNumbers.contains(srCitizen.getMobileNo())) {
			failureReasons.add(Constants.MOBILE_NUMBER_IS_ALREADY_PRESENT);
			isValid = false;
		} else {
			allMobileNumbers.add(srCitizen.getMobileNo());
		}
		srCitizen.setFailureReason(failureReasons.stream().collect(Collectors.joining("; ")));
		return isValid;
	}

	public static void validateColumns(String[] columns) {
		List<SrCitizenCSVColumns> fromCSV = Arrays.asList(columns).stream().map(SrCitizenCSVColumns::fromString)
				.filter(value -> SrCitizenCSVColumns.UNMAPPED != value).collect(Collectors.toList());
		if (fromCSV.size() != SrCitizenCSVColumns.getColumns().size()) {
			throw new CSVValidationException(Constants.CHECK_THE_COLUMNS_IN_THE_UPLOADED_FILE_THE_ALLOWED_COLUMNS_ARE
					+ SrCitizenCSVColumns.getColumns());
		}
	}
}
