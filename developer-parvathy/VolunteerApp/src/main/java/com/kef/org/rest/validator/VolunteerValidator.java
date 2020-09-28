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
			failureReasons.add("First name is empty");
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getLastName())) {
			failureReasons.add("Last name is empty");
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getEmail())) {
			failureReasons.add("Email is empty");
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getGender())) {
			failureReasons.add("Gender is empty");
			isValid = false;
		} else if (!(volunteer.getGender().equalsIgnoreCase("M") || volunteer.getGender().equalsIgnoreCase("F"))) {
			failureReasons.add("Gender must be M or F");
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getState())) {
			failureReasons.add("State is empty");
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getDistrict())) {
			failureReasons.add("District is empty");
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getAssignedToFellow())) {
			failureReasons.add("Assigned to fellow is empty");
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getAssignedToFellowContact())) {
			failureReasons.add("Assigned to fellow contact is empty");
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getStatus())) {
			failureReasons.add("Status is empty");
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getBlock())) {
			failureReasons.add("Block is empty");
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getVillage())) {
			failureReasons.add("Village is empty");
			isValid = false;
		}
		if (StringUtils.isEmpty(volunteer.getAddress())) {
			failureReasons.add("Address is empty");
			isValid = false;
		}
		// mobile check has to be at last
		if (StringUtils.isEmpty(volunteer.getMobileNo())) {
			failureReasons.add("Mobile number is empty");
			isValid = false;
		} else if (allMobileNumbers.contains(volunteer.getMobileNo())) {
			failureReasons.add("Mobile number is already present");
			isValid = false;
		} else {
			allMobileNumbers.add(volunteer.getMobileNo());
		}
		volunteer.setFailureReason(failureReasons.stream().collect(Collectors.joining("; ")));
		return isValid;
	}

	public static void validateColumns(String[] columns) {
		List<VolunteerCSVColumns> fromCSV = Arrays.asList(columns).stream().map(VolunteerCSVColumns::fromString).filter(value -> VolunteerCSVColumns.UNMAPPED != value).collect(Collectors.toList());
		if(fromCSV.size() != VolunteerCSVColumns.getColumns().size()) {
			throw new CSVValidationException("Check the columns in the uploaded file. The allowed columns are: " + VolunteerCSVColumns.getColumns());
		}
	}
}
