package com.kef.org.rest.service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.kef.org.rest.config.FileStorageConfig;
import com.kef.org.rest.domain.model.UploadFileResponseVO;
import com.kef.org.rest.domain.model.VolunteerCsvVO;
import com.kef.org.rest.domain.model.VolunteerVO;
import com.kef.org.rest.exception.FileStorageException;
import com.kef.org.rest.interfaces.VolunteerInterface;
import com.kef.org.rest.model.Volunteer;
import com.kef.org.rest.model.VolunteerAssignment;
import com.kef.org.rest.model.VolunteerResponse;
import com.kef.org.rest.repository.VolunteerAssignmentRepository;
import com.kef.org.rest.repository.VolunteerRepository;
import com.kef.org.rest.utils.Constants;
import com.kef.org.rest.utils.FileUtils;
import com.kef.org.rest.validator.VolunteerValidator;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

@Service("volunteerService")

public class VolunteerService implements VolunteerInterface {

	public static final Logger logger = LoggerFactory.getLogger(VolunteerService.class);

	@Autowired
	private VolunteerRepository volunteerRespository;

	@Autowired
	private VolunteerAssignmentRepository volunteerAssignmentRepository;
	@Autowired
	private UploadFilesInfoService filesInfoService;

	private final java.nio.file.Path uploadFileStorageLocation;
	private final java.nio.file.Path errorFileStorageLocation;

	@Autowired
	public VolunteerService(FileStorageConfig fileStorageConfig) {
		this.uploadFileStorageLocation = Paths.get(fileStorageConfig.getUploadFilesDir()).toAbsolutePath().normalize();
		this.errorFileStorageLocation = Paths.get(fileStorageConfig.getErrorFilesDir()).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.uploadFileStorageLocation);
			Files.createDirectories(this.errorFileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	public Integer findvolunteerId(String phoneNo) {

		return volunteerRespository.fetchByphoneNumber(phoneNo);
	}

	public Volunteer findvolunteerDetails(String phoneNo) {
		return volunteerRespository.fetchVolunteerDetails(phoneNo);
	}

	@Override
	public List<Volunteer> findAllVolunteerDetailsByAdminId(Integer adminId) {

		return volunteerRespository.findAllVolunteerDetailsByAdminId(adminId);
	}

	public VolunteerResponse getVolunteerListByQuery(VolunteerVO volunteerStatus) {

		List<VolunteerVO> result = new ArrayList<VolunteerVO>();
		String status = volunteerStatus.getStatus();
		String filterState = volunteerStatus.getFilterState();
		String filterDistrict = volunteerStatus.getFilterDistrict();
		String filterBlock = volunteerStatus.getFilterBlock();
		String sortBy = volunteerStatus.getSortBy();
		String sortType = volunteerStatus.getSortType();
		Integer limit;
		Integer pagenumber;
		List<Object> resultList;
		List<VolunteerVO> excludedVolunteers = new ArrayList<VolunteerVO>();
		VolunteerResponse volResponse = new VolunteerResponse();
		if (volunteerStatus.getLimit() == null && volunteerStatus.getPagenumber() == null) {
			limit = 10;
			pagenumber = 0;

		} else {
			limit = volunteerStatus.getLimit();

			pagenumber = volunteerStatus.getPagenumber();
		}

		Pageable ptry;
		Page<Object> page;
		Integer totalVolunteer = 0;
		if (sortBy != null && sortType != null) {
			Direction direction = sortType.equalsIgnoreCase("DESC") && sortType != null ? Sort.Direction.DESC
					: Sort.Direction.ASC;

			if (sortBy.equalsIgnoreCase("rating") && sortBy != null) {

				ptry = PageRequest.of(pagenumber, limit, JpaSort.unsafe(direction, "(average_rating)"));
			} else if (sortBy.equalsIgnoreCase("assignedSrCitizen") && sortBy != null) {

				ptry = PageRequest.of(pagenumber, limit, JpaSort.unsafe(direction, "(countsr)"));

			} else {
				ptry = PageRequest.of(pagenumber, limit);
			}
		} else {
			ptry = PageRequest.of(pagenumber, limit);
		}
		if (filterState != null && filterDistrict == null && filterBlock == null) {
			page = volunteerRespository.fetchByStatusAndState(status, filterState, ptry);
			resultList = page.getContent();
			totalVolunteer = (int) page.getTotalElements();
		}

		else if (filterState != null && filterDistrict != null && filterBlock == null) {
			page = volunteerRespository.fetchByStatusAndStateAndDistrict(status, filterState, filterDistrict, ptry);
			resultList = page.getContent();
			totalVolunteer = (int) page.getTotalElements();
		}

		else if (filterState != null && filterDistrict != null && filterBlock != null) {

			page = volunteerRespository.fetchByStatusAndStateAndDistrictAndBlock(status, filterState, filterDistrict,
					filterBlock, ptry);
			resultList = page.getContent();
			totalVolunteer = (int) page.getTotalElements();
		} else {
			page = volunteerRespository.queryFunction(status, ptry);
			resultList = page.getContent();
			totalVolunteer = (int) page.getTotalElements();
//    		resultList=volunteerRespository.queryFunction(status, ptry);
		}

		if (resultList != null && !resultList.isEmpty()) {

			for (int i = 0; i < resultList.size(); i++) {
				Object[] row = (Object[]) resultList.get(i);
				VolunteerVO vo = new VolunteerVO();
				vo.setIdvolunteer(Integer.valueOf(String.valueOf(row[0])));
				vo.setFirstName(String.valueOf(row[1]));
				vo.setLastName(String.valueOf(row[2]));
				vo.setphoneNo(String.valueOf(row[3]));
				vo.setEmail(String.valueOf(row[4]));
				vo.setGender(String.valueOf(row[5]));
				vo.setState(String.valueOf(row[6]));
				vo.setDistrict(String.valueOf(row[7]));
				vo.setBlock(String.valueOf(row[8]));
				vo.setAddress(String.valueOf(row[9]));
				vo.setVillage(String.valueOf(row[10]));
				vo.setAssignedtoFellow(String.valueOf(row[11]));
				vo.setAssignedtoFellowContact(String.valueOf(row[12]));
//				vo.setPic((String.valueOf(row[13]).getBytes()));
				vo.setRole(Integer.valueOf(String.valueOf(row[14])));
				vo.setAdminId(Integer.valueOf(String.valueOf(row[15])));
				vo.setStatus(String.valueOf(row[16]));
				vo.setRating(Float.valueOf(String.valueOf(row[17])));
//				vo.setCount_SrCitizen(Integer.valueOf(String.valueOf(row[18])));
				Integer idvolunteer = Integer.valueOf(String.valueOf(row[0]));
				Integer countsr = getSrCitizenCount(idvolunteer);
				vo.setCount_SrCitizen(countsr);
				result.add(vo);
			}
			if (volunteerStatus.getExcludeIds() != null && !volunteerStatus.getExcludeIds().isEmpty()) {
				excludedVolunteers = excludeVolunteer(volunteerStatus.getExcludeIds(), result);
				volResponse.setExcludedVolunteers(excludedVolunteers);
			}
		}
		volResponse.setVolunteers(result);
		volResponse.setTotalVolunteers(totalVolunteer);

		return volResponse;
	}

	public Integer getSrCitizenCount(Integer idvolunteer) {

		List<VolunteerAssignment> vol = new ArrayList<>();
		Integer countsrCitizen = 0;
		vol = volunteerAssignmentRepository.findAllByIdVolunteer(idvolunteer);
		if (vol != null && !vol.isEmpty()) {
			for (VolunteerAssignment va : vol) {
				if (!va.getStatus().equalsIgnoreCase("UnAssigned")) {

					countsrCitizen += 1;
				}
			}

		}
		return countsrCitizen;
	}

	public List<VolunteerVO> excludeVolunteer(List<Integer> exclude, List<VolunteerVO> resultList) {

		List<VolunteerVO> results = new ArrayList<>();

		for (VolunteerVO t : resultList) {
			if (exclude.contains(t.getIdvolunteer())) {

			} else {
				results.add(t);
			}
		}
		return results;
	}

	public UploadFileResponseVO uploadFile(MultipartFile file, Integer adminId, Integer adminRole) throws IOException {
		UploadFileResponseVO responseVO = new UploadFileResponseVO();
		if (!file.isEmpty() && FileUtils.isCsv(file)) {
			// Store uploaded File
			long timeStamp = System.currentTimeMillis();
			String uploadFileName = StringUtils.cleanPath("Volunteer" + "_" + "Upload" + "_" + timeStamp + ".csv");
			FileUtils.storeFile(file, uploadFileStorageLocation, uploadFileName);

			// Load volunteers from file
			List<VolunteerCsvVO> allVolunteers = getVolunteersFromFile(uploadFileName, adminId, adminRole);

			// Get all mobile numbers from db
			Set<String> allMobileNumberSet = volunteerRespository.fetchAllPhoneNumbers().stream()
					.collect(Collectors.toSet());

			// Validate all volunteers
			Map<Boolean, List<VolunteerCsvVO>> validatedVolunteerMap = allVolunteers.stream().collect(
					Collectors.partitioningBy(volunteer -> VolunteerValidator.isValid(volunteer, allMobileNumberSet)));

			// Save valid volunteers
			List<Volunteer> validVolunteers = validatedVolunteerMap.get(true).stream().map(vo -> vo.toVolunteer())
					.collect(Collectors.toList());
			volunteerRespository.saveAll(validVolunteers);

			String errorFileName = null;
			// save error file
			if (CollectionUtils.isNotEmpty(validatedVolunteerMap.get(false))) {
				errorFileName = StringUtils.cleanPath("Volunteer" + "_" + "Error" + "_" + timeStamp + ".csv");
				FileUtils.writeToCSV(validatedVolunteerMap.get(false), errorFileStorageLocation, errorFileName);
			}
			// save upload and error file details in db
			int successCount = validatedVolunteerMap.get(true).size();
			int failureCount = validatedVolunteerMap.get(false).size();
			filesInfoService.saveInfo(uploadFileName, errorFileName, adminId, successCount, failureCount);

			responseVO.setUploadFileName(uploadFileName);
			responseVO.setErrorFileName(errorFileName);
			responseVO.setSuccessCount(successCount);
			responseVO.setFailureCount(failureCount);
			setMessageAndStatusCode(responseVO, successCount, failureCount);
		} else {
			responseVO.setMessage(Constants.FAILURE);
			responseVO.setStatusCode(1);
		}
		return responseVO;
	}

	private void setMessageAndStatusCode(UploadFileResponseVO responseVO, int successCount, int failureCount) {
		String message = null;
		Integer statusCode = null;
		if(successCount == 0) {
			message = Constants.FAILURE;
			statusCode = 1;
		} else if (failureCount == 0) {
			message = Constants.SUCCESS;
			statusCode = 0;
		} else {
			message = Constants.PARTIAL_SUCCESS;
			statusCode = 1;
		}
		responseVO.setMessage(message);
		responseVO.setStatusCode(statusCode);
	}

	private List<VolunteerCsvVO> getVolunteersFromFile(String file, Integer adminId, Integer adminRole)
			throws java.io.FileNotFoundException, IOException {
		List<VolunteerCsvVO> allVolunteers = new ArrayList<>();
		String filePath = FileUtils.loadFileAsResource(file, this.uploadFileStorageLocation).getFile().getPath();
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
			String[] line;
			String[] columns = reader.readNext();
			VolunteerValidator.validateColumns(columns);
			while ((line = reader.readNext()) != null) {
				allVolunteers.add(createVolunteer(columns, line, adminId, adminRole));
			}
		} catch (CsvValidationException e) {
			throw new FileStorageException("Could not read the file. Server error");
		}
		return allVolunteers;
	}

	private VolunteerCsvVO createVolunteer(String[] columns, String[] attributes, Integer adminId, Integer adminRole) {
		VolunteerCsvVO volunteer = new VolunteerCsvVO();
		for (int i = 0; i < columns.length; i++) {
			Constants.VolunteerCSVColumns column = Constants.VolunteerCSVColumns.fromString(columns[i]);
			switch (column) {
			case FIRSTNAME:
				volunteer.setFirstName(attributes[i]);
				break;
			case LASTNAME:
				volunteer.setLastName(attributes[i]);
				break;
			case MOBILE_NO:
				volunteer.setMobileNo(attributes[i]);
				break;
			case EMAIL:
				volunteer.setEmail(attributes[i]);
				break;
			case GENDER:
				volunteer.setGender(attributes[i]);
				break;
			case STATE_NAME:
				volunteer.setState(attributes[i]);
				break;
			case DISTRICT_NAME:
				volunteer.setDistrict(attributes[i]);
				break;
			case ASSIGNED_TO_FELLOW:
				volunteer.setAssignedToFellow(attributes[i]);
				break;
			case ASSIGNED_TO_FELLOW_CONTACT:
				volunteer.setAssignedToFellowContact(attributes[i]);
				break;
			case STATUS:
				volunteer.setStatus(attributes[i]);
				break;
			case ADDRESS:
				volunteer.setAddress(attributes[i]);
				break;
			case BLOCK_NAME:
				volunteer.setBlock(attributes[i]);
				break;
			case VILLAGE:
				volunteer.setVillage(attributes[i]);
				break;
			default:
				break;
			}
		}
		volunteer.setAdminId(adminId);
		volunteer.setRole(adminRole);
		List<String> columnList = new ArrayList<>(Arrays.asList(columns));
		columnList.addAll(Arrays.asList(Constants.VolunteerCSVColumns.ADMIN_ROLE.getColumnName(),
				Constants.VolunteerCSVColumns.ADMIN_ID.getColumnName(),
				Constants.VolunteerCSVColumns.FAILURE_REASON.getColumnName()));
		volunteer.setColumns(columnList.toArray(new String[columnList.size()]));
		return volunteer;
	}

	public Resource loadFileAsResource(String fileName, String fileDownloadType) {
		return fileDownloadType.equals(Constants.FILE_DOWNLOAD_TYPE_UPLOAD)
				? FileUtils.loadFileAsResource(fileName, uploadFileStorageLocation)
				: FileUtils.loadFileAsResource(fileName, errorFileStorageLocation);
	}
}