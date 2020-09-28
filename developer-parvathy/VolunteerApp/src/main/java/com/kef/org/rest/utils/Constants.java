package com.kef.org.rest.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Constants {

	private Constants() {
	}

	// TalkedWith enum values
	public static final String SENIOR_CITIZEN = "1";
	public static final String FAMILY_MEMBER_OF_SR_CITIZEN = "2";
	public static final String COMMUNITY_MEMBER = "3";

	// Gender
	public static final String FEMALE = "F";
	public static final String MALE = "M";

	// InputEnum values
	public static final String Y = "1";
	public static final String N = "2";

	// related_info_talked_about enum values
	public static final String PREVENTION = "1";
	public static final String ACCESS = "2";
	public static final String DETECION = "3";

	// behavioural_change_noticed enum values
	public static final String YES = "1";
	public static final String NO = "2";
	public static final String MAY_BE = "3";
	public static final String NOT_APPLICABLE = "4";

	// Response Messages
	public static final String SUCCESS = "Success";
	public static final String FAILURE = "Failure";
	public static final String PARTIAL_SUCCESS = "Partial Success";

	// Response Status
	public static final Integer ONE = 1;
	public static final Integer ZERO = 0;

	// Errors
	public static final String FIELD_S_NEEDS_TO_BE_A_DOTTED_PATH_I_E_VOLUNTEER_VOLUNTEERASSIGNMENT_ASSIGNEDBYMEMBER = "Field '%s' needs to be a dotted path (i.e. volunteer.volunteerassignment.assignedbymember)";
	public static final String THE_GIVEN_MAP_DOES_NOT_CONTAIN_A_FROM_OR_FOR_THE_VALUE_S_OR_IS_NULL = "The given map does not contain a from or for the value '%s' or is null";
	public static final String COULD_NOT_READ_THE_FILE_SERVER_ERROR = "Could not read the file. Server error";
	public static final String ERROR_COULD_NOT_READ_THE_FILE_SERVER = "Could not read the file. Server error";
	public static final String FILE_NOT_FOUND_S = "File not found : %s";
	public static final String COULD_NOT_STORE_FILE_S_PLEASE_TRY_AGAIN = "Could not store file %s. Please try again!";
	public static final String SORRY_FILENAME_CONTAINS_INVALID_PATH_SEQUENCE = "Sorry! Filename contains invalid path sequence : %s";
	public static final String CHECK_THE_COLUMNS_IN_THE_UPLOADED_FILE_THE_ALLOWED_COLUMNS_ARE = "Check the columns in the uploaded file. The allowed columns are: ";
	public static final String MOBILE_NUMBER_IS_ALREADY_PRESENT = "Mobile number is already present";
	public static final String MOBILE_NUMBER_IS_EMPTY = "Mobile number is empty";
	public static final String ADDRESS_IS_EMPTY = "Address is empty";
	public static final String VILLAGE_IS_EMPTY = "Village is empty";
	public static final String BLOCK_IS_EMPTY = "Block is empty";
	public static final String STATUS_IS_EMPTY = "Status is empty";
	public static final String ASSIGNED_TO_FELLOW_CONTACT_IS_EMPTY = "Assigned to fellow contact is empty";
	public static final String ASSIGNED_TO_FELLOW_IS_EMPTY = "Assigned to fellow is empty";
	public static final String DISTRICT_IS_EMPTY = "District is empty";
	public static final String STATE_IS_EMPTY = "State is empty";
	public static final String GENDER_MUST_BE_M_OR_F = "Gender must be M or F";
	public static final String GENDER_IS_EMPTY = "Gender is empty";
	public static final String EMAIL_IS_EMPTY = "Email is empty";
	public static final String LAST_NAME_IS_EMPTY = "Last name is empty";
	public static final String FIRST_NAME_IS_EMPTY = "First name is empty";
	public static final String AGE_IS_EMPTY = "Age is empty";
	public static final String AGE_SHOULD_BE_NUMERIC = "Age should be numeric";
	public static final String REFERRED_BY_IS_EMPTY = "Referred by is empty";
	public static final String CREATED_BY_VOLUNTEER_ID_IS_EMPTY = "Created by volunteer id is empty";
	public static final String CREATED_BY_VOLUNTEER_ID_SHOULD_BE_NUMERIC = "Created by volunteer id should be numeric";
	public static final String COULD_NOT_DETERMINE_FILE_TYPE = "Could not determine file type.";

	// Generic
	public static final String CSV = "csv";
	public static final String CSV_EXTENSION = ".csv";
	public static final String FILE_DOWNLOAD_TYPE_UPLOAD = "Upload";
	public static final String FILE_DOWNLOAD_TYPE_ERROR = "Error";
	public static final String UNDERSCORE = "_";
	public static final String VOLUNTEER = "Volunteer";
	public static final String SR_CITIZEN = "SrCitizen";
	public static final String ERROR = "Error";
	public static final String UPLOAD = "Upload";
	public static final String ASSIGNED_SR_CITIZEN = "assignedSrCitizen";
	public static final String RATING = "rating";

	public static enum VolunteerCSVColumns {
		FIRSTNAME("FIRSTNAME"), LASTNAME("LASTNAME"), MOBILE_NO("MOBILENO"), EMAIL("EMAIL"), GENDER("GENDER"),
		STATE_NAME("STATE_NAME"), DISTRICT_NAME("DISTRICT_NAME"), BLOCK_NAME("BLOCK_NAME"), ADDRESS("ADDRESS"),
		VILLAGE("VILLAGE"), ASSIGNED_TO_FELLOW("ASSIGNED_TO_FELLOW"),
		ASSIGNED_TO_FELLOW_CONTACT("ASSIGNED_TO_FELLOW_CONTACT"), STATUS("STATUS"), FAILURE_REASON("FAILURE_REASON"),
		ADMIN_ROLE("ADMIN_ROLE"), ADMIN_ID("ADMIN_ID"), UNMAPPED("UNMAPPED");

		private String columnName;

		VolunteerCSVColumns(String columnName) {
			this.columnName = columnName;
		}

		public String getColumnName() {
			return columnName;
		}

		public static VolunteerCSVColumns fromString(String value) {
			for (VolunteerCSVColumns column : VolunteerCSVColumns.values()) {
				if (column.columnName.equalsIgnoreCase(value)) {
					return column;
				}
			}
			return VolunteerCSVColumns.UNMAPPED;
		}

		public static List<String> getColumns() {
			final List<String> columnsToSkip = Arrays.asList(VolunteerCSVColumns.UNMAPPED.getColumnName(),
					VolunteerCSVColumns.ADMIN_ID.getColumnName(), VolunteerCSVColumns.ADMIN_ROLE.getColumnName(),
					VolunteerCSVColumns.FAILURE_REASON.getColumnName());
			return Arrays.asList(VolunteerCSVColumns.values()).stream().map(VolunteerCSVColumns::getColumnName)
					.filter(value -> !columnsToSkip.contains(value)).collect(Collectors.toList());
		}

	}

	public static enum SrCitizenCSVColumns {
		FIRSTNAME("FIRSTNAME"), LASTNAME("LASTNAME"), MOBILE_NO("MOBILENO"), EMAIL("EMAIL"), GENDER("GENDER"),
		AGE("AGE"), STATE_NAME("STATE_NAME"), DISTRICT_NAME("DISTRICT_NAME"), BLOCK_NAME("BLOCK_NAME"),
		ADDRESS("ADDRESS"), VILLAGE("VILLAGE"), STATUS("STATUS"), REFERRED_BY("REFERRED_BY"),
		CREATED_BY_VOLUNTEER_ID("CREATED_BY_VOLUNTEER_ID"), FAILURE_REASON("FAILURE_REASON"), UNMAPPED("UNMAPPED");

		private String columnName;

		SrCitizenCSVColumns(String columnName) {
			this.columnName = columnName;
		}

		public String getColumnName() {
			return columnName;
		}

		public static SrCitizenCSVColumns fromString(String value) {
			for (SrCitizenCSVColumns column : SrCitizenCSVColumns.values()) {
				if (column.columnName.equalsIgnoreCase(value)) {
					return column;
				}
			}
			return SrCitizenCSVColumns.UNMAPPED;
		}

		public static List<String> getColumns() {
			final List<String> columnsToSkip = Arrays.asList(SrCitizenCSVColumns.UNMAPPED.getColumnName(),
					SrCitizenCSVColumns.FAILURE_REASON.getColumnName());
			return Arrays.asList(SrCitizenCSVColumns.values()).stream().map(SrCitizenCSVColumns::getColumnName)
					.filter(value -> !columnsToSkip.contains(value)).collect(Collectors.toList());
		}

	}
}
