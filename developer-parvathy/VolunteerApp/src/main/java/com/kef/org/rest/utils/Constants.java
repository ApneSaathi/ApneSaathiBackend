package com.kef.org.rest.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.kef.org.rest.utils.Constants.VolunteerCSVColumns;

public final class Constants {

	private Constants() {
	}

	// TalkedWith enum values
	public static final String SENIOR_CITIZEN = "1";
	public static final String FAMILY_MEMBER_OF_SR_CITIZEN = "2";
	public static final String COMMUNITY_MEMBER = "3";

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
	
	public static final String CSV = "csv";
	public static final String FILE_DOWNLOAD_TYPE_UPLOAD = "Upload";
	public static final String FILE_DOWNLOAD_TYPE_ERROR = "Error";

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
}
