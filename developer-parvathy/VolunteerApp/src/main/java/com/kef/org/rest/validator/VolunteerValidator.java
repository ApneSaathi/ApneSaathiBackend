package com.kef.org.rest.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.kef.org.rest.domain.model.VolunteerCsvVO;
import com.kef.org.rest.exception.CSVValidationException;
import com.kef.org.rest.utils.Constants;
import com.kef.org.rest.utils.Constants.VolunteerCSVColumns;

public final class VolunteerValidator {

	private VolunteerValidator() {
	}

	public static boolean isValid(VolunteerCsvVO volunteer, Set<String> allMobileNumbers) {
		List<String> failureReasons = new ArrayList<>();
		boolean isValid = true;
		if (StringUtils.isEmpty(volunteer.getFirstName())) {
			failureReasons.add(Constants.FIRST_NAME_IS_EMPTY);
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getLastName())) {
			failureReasons.add(Constants.LAST_NAME_IS_EMPTY);
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getEmail())) {
			failureReasons.add(Constants.EMAIL_IS_EMPTY);
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getGender())) {
			failureReasons.add(Constants.GENDER_IS_EMPTY);
			isValid = false;
		} else if (!(volunteer.getGender().equalsIgnoreCase(Constants.MALE)
				|| volunteer.getGender().equalsIgnoreCase(Constants.FEMALE))) {
			failureReasons.add(Constants.GENDER_MUST_BE_M_OR_F);
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getState())) {
			failureReasons.add(Constants.STATE_IS_EMPTY);
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getDistrict())) {
			failureReasons.add(Constants.DISTRICT_IS_EMPTY);
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getAssignedToFellow())) {
			failureReasons.add(Constants.ASSIGNED_TO_FELLOW_IS_EMPTY);
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getAssignedToFellowContact())) {
			failureReasons.add(Constants.ASSIGNED_TO_FELLOW_CONTACT_IS_EMPTY);
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getStatus())) {
			failureReasons.add(Constants.STATUS_IS_EMPTY);
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getBlock())) {
			failureReasons.add(Constants.BLOCK_IS_EMPTY);
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getVillage())) {
			failureReasons.add(Constants.VILLAGE_IS_EMPTY);
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getAddress())) {
			failureReasons.add(Constants.ADDRESS_IS_EMPTY);
			isValid = false;
		}
		// mobile check has to be at last
		if (StringUtils.isEmpty(volunteer.getMobileNo())) {
			failureReasons.add(Constants.MOBILE_NUMBER_IS_EMPTY);
			isValid = false;
		} else if (allMobileNumbers.contains(volunteer.getMobileNo())) {
			failureReasons.add(Constants.MOBILE_NUMBER_IS_ALREADY_PRESENT);
			isValid = false;
		} else {
			allMobileNumbers.add(volunteer.getMobileNo());
		}
		volunteer.setFailureReason(failureReasons.stream().collect(Collectors.joining("; ")));
		return isValid;
	}

	public static void validateColumns(String[] columns) {
		List<VolunteerCSVColumns> fromCSV = Arrays.asList(columns).stream().map(VolunteerCSVColumns::fromString)
				.filter(value -> VolunteerCSVColumns.UNMAPPED != value).collect(Collectors.toList());
		if (fromCSV.size() != VolunteerCSVColumns.getColumns().size()) {
			throw new CSVValidationException(Constants.CHECK_THE_COLUMNS_IN_THE_UPLOADED_FILE_THE_ALLOWED_COLUMNS_ARE
					+ VolunteerCSVColumns.getColumns());
		}
	}
}
