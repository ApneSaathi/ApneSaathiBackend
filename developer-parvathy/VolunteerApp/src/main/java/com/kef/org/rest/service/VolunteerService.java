package com.kef.org.rest.service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
import com.kef.org.rest.model.VolunteerRating;
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

	@PersistenceContext
	private EntityManager em;

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

	/*
	 * public VolunteerResponse getVolunteerListByQuery(VolunteerVO volunteerFilter)
	 * { CriteriaBuilder builder = em.getCriteriaBuilder(); CriteriaQuery<Tuple>
	 * criteriaQuery = builder.createTupleQuery();
	 * 
	 * // setting up the required joins Root<Volunteer> rootVolunteer =
	 * criteriaQuery.from(Volunteer.class); Join<Volunteer, VolunteerAssignment>
	 * joinVolunteerAssignment = rootVolunteer.join(Volunteer_.volunteercallList,
	 * JoinType.LEFT); Join<Volunteer, VolunteerRating> joinVolunteerRating =
	 * rootVolunteer.join(Volunteer_.volunteerRatingList, JoinType.LEFT);
	 * 
	 * // putting all joins into a map with a dot`ted name Map<String, From<?, ?>>
	 * mapFieldToFrom = getMapOfJoins(rootVolunteer, joinVolunteerAssignment,
	 * joinVolunteerRating);
	 * 
	 * // select setSelect(builder, criteriaQuery, rootVolunteer,
	 * joinVolunteerAssignment, joinVolunteerRating);
	 * 
	 * // where List<Predicate> allPredicates = getPredicates(volunteerFilter,
	 * builder, rootVolunteer, mapFieldToFrom);
	 * criteriaQuery.where(builder.and(allPredicates.toArray(new
	 * Predicate[allPredicates.size()])));
	 * 
	 * // group by criteriaQuery.groupBy(rootVolunteer.get(Volunteer_.idvolunteer));
	 * 
	 * // order setOrderBy(volunteerFilter, builder, criteriaQuery,
	 * joinVolunteerAssignment, joinVolunteerRating);
	 * 
	 * // query TypedQuery<Tuple> query = em.createQuery(criteriaQuery);
	 * 
	 * // pagination query.setFirstResult(volunteerFilter.getPagenumber() == null ?
	 * 0 : volunteerFilter.getPagenumber());
	 * query.setMaxResults(volunteerFilter.getLimit() == null ? 10 :
	 * volunteerFilter.getLimit());
	 * 
	 * VolunteerResponse response = createResponse(volunteerFilter, builder,
	 * allPredicates, query); return response; }
	 */

	private VolunteerResponse createResponse(VolunteerVO volunteerFilter, CriteriaBuilder builder,
			List<Predicate> allPredicates, TypedQuery<Tuple> query) {
		VolunteerResponse response = new VolunteerResponse();
		List<Tuple> resultList = query.getResultList();
		if (resultList != null && !resultList.isEmpty()) {
			response = populateVolunteerResponse(resultList, response);
			response.setTotalVolunteers(getTotalCount(volunteerFilter, builder, allPredicates));
		} else {
			response.setTotalVolunteers(0l);
			response.setMessage(Constants.FAILURE);
			response.setStatusCode(Constants.ONE);
		}
		return response;
	}

	private Map<String, From<?, ?>> getMapOfJoins(Root<Volunteer> rootVolunteer,
			Join<Volunteer, VolunteerAssignment> joinVolunteerAssignment,
			Join<Volunteer, VolunteerRating> joinVolunteerRating) {
		Map<String, From<?, ?>> mapFieldToFrom = new HashMap<>();
		mapFieldToFrom.put("volunteer", rootVolunteer);
		mapFieldToFrom.put("volunteer.volunteerrating", joinVolunteerRating);
		mapFieldToFrom.put("volunteer.volunteerassignment", joinVolunteerAssignment);
		return mapFieldToFrom;
	}

	/*
	 * private void setSelect(CriteriaBuilder builder, CriteriaQuery<Tuple>
	 * criteriaQuery, Root<Volunteer> rootVolunteer, Join<Volunteer,
	 * VolunteerAssignment> joinVolunteerAssignment, Join<Volunteer,
	 * VolunteerRating> joinVolunteerRating) {
	 * criteriaQuery.multiselect(rootVolunteer.get(Volunteer_.idvolunteer),
	 * rootVolunteer.get(Volunteer_.firstName),
	 * rootVolunteer.get(Volunteer_.lastName),
	 * rootVolunteer.get(Volunteer_.phoneNo), rootVolunteer.get(Volunteer_.email),
	 * rootVolunteer.get(Volunteer_.gender), rootVolunteer.get(Volunteer_.state),
	 * rootVolunteer.get(Volunteer_.district), rootVolunteer.get(Volunteer_.block),
	 * rootVolunteer.get(Volunteer_.address), rootVolunteer.get(Volunteer_.Village),
	 * rootVolunteer.get(Volunteer_.assignedtoFellow),
	 * rootVolunteer.get(Volunteer_.assignedtoFellowContact),
	 * rootVolunteer.get(Volunteer_.pic), rootVolunteer.get(Volunteer_.role),
	 * rootVolunteer.get(Volunteer_.adminId), rootVolunteer.get(Volunteer_.status),
	 * builder.avg(builder.coalesce(joinVolunteerRating.get(VolunteerRating_.RATING)
	 * , 0)), builder.count(joinVolunteerAssignment)); }
	 */

	/*
	 * private List<Predicate> getPredicates(VolunteerVO volunteerFilter,
	 * CriteriaBuilder builder, Root<Volunteer> rootVolunteer, Map<String, From<?,
	 * ?>> mapFieldToFrom) { List<Predicate> allPredicates = new ArrayList<>(); for
	 * (Entry<String, Object> currentEntry : getFilters(volunteerFilter).entrySet())
	 * { Predicate currentPredicate = builder.like(
	 * builder.lower(getStringPath(currentEntry.getKey(), mapFieldToFrom)),
	 * builder.lower(builder.literal("%" + String.valueOf(currentEntry.getValue()) +
	 * "%"))); allPredicates.add(currentPredicate); } if
	 * (CollectionUtils.isNotEmpty(volunteerFilter.getExcludeIds())) {
	 * allPredicates.add(excludingVolunteers(builder, rootVolunteer,
	 * volunteerFilter.getExcludeIds())); } return allPredicates; }
	 */

	/*
	 * private void setOrderBy(VolunteerVO volunteerFilter, CriteriaBuilder builder,
	 * CriteriaQuery<Tuple> criteriaQuery, Join<Volunteer, VolunteerAssignment>
	 * joinVolunteerAssignment, Join<Volunteer, VolunteerRating>
	 * joinVolunteerRating) { if (!StringUtils.isEmpty(volunteerFilter.getSortBy()))
	 * { Order orderBy = null; if
	 * (volunteerFilter.getSortBy().equalsIgnoreCase(Constants.ASSIGNED_SR_CITIZEN))
	 * { Expression<Long> orderByCallId = builder
	 * .count(joinVolunteerAssignment.get(VolunteerAssignment_.callid)); orderBy =
	 * volunteerFilter.getSortType() == Sort.Direction.DESC ?
	 * builder.desc(orderByCallId) : builder.asc(orderByCallId); } else if
	 * (volunteerFilter.getSortBy().equalsIgnoreCase(Constants.RATING)) {
	 * Expression<Double> orderByAvgRating = builder
	 * .avg(builder.coalesce(joinVolunteerRating.get(VolunteerRating_.RATING), 0));
	 * orderBy = volunteerFilter.getSortType() == Sort.Direction.DESC ?
	 * builder.desc(orderByAvgRating) : builder.asc(orderByAvgRating); }
	 * criteriaQuery.orderBy(orderBy); } }
	 */

	private Long getTotalCount(VolunteerVO volunteerFilter, CriteriaBuilder builder, List<Predicate> allPredicates) {
		CriteriaQuery<Long> cQuery = builder.createQuery(Long.class);
		Root<Volunteer> from = cQuery.from(Volunteer.class);
		CriteriaQuery<Long> select = cQuery.select(builder.count(from));
		select.where(builder.and(allPredicates.toArray(new Predicate[allPredicates.size()])));
		TypedQuery<Long> typedQuery = em.createQuery(select);
		typedQuery.setFirstResult(volunteerFilter.getPagenumber() == null ? 0 : volunteerFilter.getPagenumber());
		typedQuery.setMaxResults(volunteerFilter.getLimit() == null ? 10 : volunteerFilter.getLimit());
		// here is the size of your query
		return typedQuery.getSingleResult();
	}

	/*
	 * private Predicate excludingVolunteers(CriteriaBuilder builder,
	 * Root<Volunteer> rootVolunteer, List<Integer> excludeIds) { return
	 * builder.not(rootVolunteer.get(Volunteer_.idvolunteer).in(excludeIds)); }
	 */

	private VolunteerResponse populateVolunteerResponse(List<Tuple> resultList, VolunteerResponse response) {
		response.setVolunteers(resultList.stream().map(row -> {
			VolunteerVO vo = new VolunteerVO();
			vo.setIdvolunteer(row.get(0) != null ? Integer.valueOf(String.valueOf(row.get(0))) : null);
			vo.setFirstName(row.get(1) != null ? String.valueOf(row.get(1)) : null);
			vo.setLastName(row.get(2) != null ? String.valueOf(row.get(2)) : null);
			vo.setphoneNo(row.get(3) != null ? String.valueOf(row.get(3)) : null);
			vo.setEmail(row.get(4) != null ? String.valueOf(row.get(4)) : null);
			vo.setGender(row.get(5) != null ? String.valueOf(row.get(5)) : null);
			vo.setState(row.get(6) != null ? String.valueOf(row.get(6)) : null);
			vo.setDistrict(row.get(7) != null ? String.valueOf(row.get(7)) : null);
			vo.setBlock(row.get(8) != null ? String.valueOf(row.get(8)) : null);
			vo.setAddress(row.get(9) != null ? String.valueOf(row.get(9)) : null);
			vo.setVillage(row.get(10) != null ? String.valueOf(row.get(10)) : null);
			vo.setAssignedtoFellow(row.get(11) != null ? String.valueOf(row.get(11)) : null);
			vo.setAssignedtoFellowContact(row.get(12) != null ? String.valueOf(row.get(12)) : null);
			vo.setPic(row.get(13) != null ? String.valueOf(row.get(13)).getBytes() : null);
			vo.setRole(row.get(14) != null ? Integer.valueOf(String.valueOf(row.get(14))) : null);
			vo.setAdminId(row.get(15) != null ? Integer.valueOf(String.valueOf(row.get(15))) : null);
			vo.setStatus(row.get(16) != null ? String.valueOf(row.get(16)) : null);
			vo.setRating(row.get(17) != null ? Float.valueOf(String.valueOf(row.get(17))) : null);
			vo.setCount_SrCitizen(row.get(18) != null ? Integer.valueOf(String.valueOf(row.get(18))) : null);
			return vo;
		}).collect(Collectors.toList()));
		response.setMessage(Constants.SUCCESS);
		response.setStatusCode(Constants.ZERO);
		return response;
	}

	/**
	 * divides the given field at the last dot and takes <br>
	 * - the first part as the key in the map to retrieve the From<?, ?> <br>
	 * - the last part as the name of the column in the entity
	 */
	private Path<String> getStringPath(String field, Map<String, From<?, ?>> mapFieldToFrom) {
		if (!field.matches(".+\\..+")) {
			throw new IllegalArgumentException(String.format(
					Constants.FIELD_S_NEEDS_TO_BE_A_DOTTED_PATH_I_E_VOLUNTEER_VOLUNTEERASSIGNMENT_ASSIGNEDBYMEMBER,
					field));
		}
		String fromPart = field.substring(0, field.lastIndexOf('.'));
		String fieldPart = field.substring(field.lastIndexOf('.') + 1);

		From<?, ?> actualFrom = mapFieldToFrom.get(fromPart);
		if (actualFrom == null) {
			throw new IllegalStateException(String
					.format(Constants.THE_GIVEN_MAP_DOES_NOT_CONTAIN_A_FROM_OR_FOR_THE_VALUE_S_OR_IS_NULL, fromPart));
		}
		return actualFrom.get(fieldPart);
	}

	/*
	 * private Map<String, Object> getFilters(VolunteerVO volunteerFilter) {
	 * Map<String, Object> filterMap = new HashMap<>(); // add status
	 * filterMap.put("volunteer." + Volunteer_.status.getName(),
	 * volunteerFilter.getStatus()); // add state if
	 * (!StringUtils.isEmpty(volunteerFilter.getFilterState())) {
	 * filterMap.put("volunteer." + Volunteer_.state.getName(),
	 * volunteerFilter.getFilterState()); } // add district if
	 * (!StringUtils.isEmpty(volunteerFilter.getFilterDistrict())) {
	 * filterMap.put("volunteer." + Volunteer_.district.getName(),
	 * volunteerFilter.getFilterDistrict()); } // add block if
	 * (!StringUtils.isEmpty(volunteerFilter.getFilterBlock())) {
	 * filterMap.put("volunteer." + Volunteer_.block.getName(),
	 * volunteerFilter.getFilterBlock()); } return filterMap; }
	 */

	public UploadFileResponseVO uploadFile(MultipartFile file, Integer adminId, Integer adminRole) throws IOException {
		UploadFileResponseVO responseVO = new UploadFileResponseVO();
		if (!file.isEmpty() && FileUtils.isCsv(file)) {
			// Store uploaded File
			long timeStamp = System.currentTimeMillis();
			String uploadFileName = StringUtils.cleanPath(Constants.VOLUNTEER + Constants.UNDERSCORE + Constants.UPLOAD
					+ Constants.UNDERSCORE + timeStamp + Constants.CSV_EXTENSION);
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
				errorFileName = StringUtils.cleanPath(Constants.VOLUNTEER + Constants.UNDERSCORE + Constants.ERROR
						+ Constants.UNDERSCORE + timeStamp + Constants.CSV_EXTENSION);
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
		if (successCount == 0) {
			message = Constants.FAILURE;
			statusCode = Constants.ONE;
		} else if (failureCount == 0) {
			message = Constants.SUCCESS;
			statusCode = Constants.ZERO;
		} else {
			message = Constants.PARTIAL_SUCCESS;
			statusCode = Constants.ONE;
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
			throw new FileStorageException(Constants.COULD_NOT_READ_THE_FILE_SERVER_ERROR);
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

	@SuppressWarnings("unchecked")
	public VolunteerResponse getVolunteerListByJPACriteria(VolunteerVO request){
		List<VolunteerVO> volunteerList=new ArrayList<VolunteerVO>();
		/**
		 * calling method for request validation
		 */
		request = validateVolunteerListRequest(request);
		/***
		 * calling method for fetching volunteer list from DB
		 */
		Map<String,Object> resultMap = fetchVolunteerList(request);
		List<Tuple> tupleList = null;
		Long rowCount = 0L;
		VolunteerResponse volResponse=new VolunteerResponse();
		if(resultMap!=null) {
			tupleList = (List<Tuple>) resultMap.get("result");
			rowCount = (Long) resultMap.get("rowCount");
		}
		/***
		 * Constructing the response.
		 */
		if(tupleList!=null && !tupleList.isEmpty()) {
			tupleList.forEach(row->{
				VolunteerVO vo = new VolunteerVO();
				vo.setIdvolunteer(Integer.valueOf(String.valueOf(row.get(0))));
				vo.setFirstName(String.valueOf(row.get(1)));
				vo.setLastName(String.valueOf(row.get(2)));
				vo.setphoneNo(String.valueOf(row.get(3)));
				vo.setEmail(String.valueOf(row.get(4)));
				vo.setGender(String.valueOf(row.get(5)));
				vo.setState(String.valueOf(row.get(6)));
				vo.setDistrict(String.valueOf(row.get(7)));
				vo.setBlock(String.valueOf(row.get(8)));
				vo.setAddress(String.valueOf(row.get(9)));
				vo.setVillage(String.valueOf(row.get(10)));
				vo.setAssignedtoFellow(String.valueOf(row.get(11)));
				vo.setAssignedtoFellowContact(String.valueOf(row.get(12)));
//				vo.setPic((String.valueOf(row[13]).getBytes()));
				vo.setRole(Integer.valueOf(String.valueOf(row.get(14))));
				vo.setAdminId(Integer.valueOf(String.valueOf(row.get(15))));
				vo.setStatus(String.valueOf(row.get(16)));
				vo.setRating(Float.valueOf(String.valueOf(row.get(17))));
				vo.setCount_SrCitizen(Integer.valueOf(String.valueOf(row.get(18))));
				volunteerList.add(vo);
			});
			volResponse.setMessage("Success");
			volResponse.setStatusCode(0);
		}else {
			volResponse.setMessage("Failure");
			volResponse.setStatusCode(1);
		}
		if(!request.getSortBy().isEmpty()) {
			String sortType = (request.getSortType()!=null && request.getSortType().equalsIgnoreCase("asc")) ? "asc" : "desc";
			String sortBy = request.getSortBy();
			if(sortBy.equalsIgnoreCase("rating")) {
				if(sortType.equalsIgnoreCase("asc")) {
					volunteerList.sort(Comparator.comparing(VolunteerVO::getRating));
				}else {
					volunteerList.sort(Comparator.comparing(VolunteerVO::getRating).reversed());
				}
			}else {
				if(sortType.equalsIgnoreCase("asc")) {
					volunteerList.sort(Comparator.comparing(VolunteerVO::getCount_SrCitizen));
				}else {
					volunteerList.sort(Comparator.comparing(VolunteerVO::getCount_SrCitizen).reversed());
				}
			}
		}
		volResponse.setVolunteers(volunteerList);
		volResponse.setTotalVolunteers(rowCount);
		return volResponse;
	}
	
	public VolunteerVO validateVolunteerListRequest(VolunteerVO request) {
		request.setStatus(request.getStatus()!=null ? request.getStatus() : "");
		request.setFilterState(request.getFilterState()!=null ? request.getFilterState():"");
		request.setFilterDistrict(request.getFilterDistrict()!=null ? request.getFilterDistrict():"");
		request.setFilterBlock(request.getFilterBlock()!=null ? request.getFilterBlock():"");
		request.setSortBy(request.getSortBy()!=null?request.getSortBy():"");
		request.setSortType(request.getSortType()!=null ? request.getSortType().toString() : "");
		request.setPagenumber(request.getPagenumber()!=null && request.getPagenumber()!=0 ? request.getPagenumber() : 0);
		request.setLimit(request.getLimit()!=null && request.getLimit()!=0 ? request.getLimit() : 0);
		return request;
	}
	
	public Map<String,Object> fetchVolunteerList(VolunteerVO request) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> cq = builder.createTupleQuery();

		Root<Volunteer> vRoot = cq.from(Volunteer.class);

		List<Predicate> conditions = new ArrayList<>();
		conditions.add(builder.equal(vRoot.get("status"), request.getStatus()));

		if(!request.getFilterState().isEmpty())
			conditions.add(builder.equal(vRoot.get("state"), request.getFilterState()));
		if(!request.getFilterDistrict().isEmpty())
			conditions.add(builder.equal(vRoot.get("district"), request.getFilterDistrict()));
		if(!request.getFilterBlock().isEmpty())
			conditions.add(builder.equal(vRoot.get("block"), request.getFilterBlock()));
		if(request.getExcludeIds()!=null && !request.getExcludeIds().isEmpty())
			conditions.add(builder.not(vRoot.get("idvolunteer").in(request.getExcludeIds())));

		Subquery<Double> avgRatingQuery = cq.subquery(Double.class); 
		Root<VolunteerRating> vrSubQuery = avgRatingQuery.from(VolunteerRating.class);
		Expression<Double> avgRatingExpr = builder.avg(builder.coalesce(vrSubQuery.get("rating"), 0.0));
		avgRatingQuery.select(builder.coalesce(avgRatingExpr,0.0));
		avgRatingQuery.where(builder.equal(vrSubQuery.get("idvolunteer"),vRoot.get("idvolunteer")));

		Subquery<Long> countSrQuery = cq.subquery(Long.class);
		Root<VolunteerAssignment> vaSubQuery = countSrQuery.from(VolunteerAssignment.class);
		countSrQuery.select(builder.countDistinct(vaSubQuery.get("phonenosrcitizen")));
		countSrQuery.where(builder.equal(vaSubQuery.get("idvolunteer"), vRoot.get("idvolunteer")),
				builder.equal(vaSubQuery.get("status"), "Assigned"));

		cq.multiselect(vRoot.get("idvolunteer"),vRoot.get("firstName"),vRoot.get("lastName"),vRoot.get("phoneNo"),vRoot.get("email"),
				vRoot.get("gender"),vRoot.get("state"),vRoot.get("district"),vRoot.get("block"),vRoot.get("address"),
				vRoot.get("Village"), vRoot.get("assignedtoFellow"),vRoot.get("assignedtoFellowContact"),vRoot.get("pic"),
				vRoot.get("role"),vRoot.get("adminId"),vRoot.get("status"),
				avgRatingQuery.getSelection().alias("average_rating"),
				countSrQuery.getSelection().alias("countsr"));

		TypedQuery<Tuple> typedQuery;
		
		typedQuery = em.createQuery(cq
					.where(conditions.toArray(new Predicate[] {}))
					.groupBy(vRoot.get("idvolunteer")));
		
		Long rowCount = (long) typedQuery.getResultList().size();
		if(request.getPagenumber()>0 && request.getLimit()>0) {
			typedQuery.setFirstResult((request.getPagenumber())*request.getLimit());
			typedQuery.setMaxResults(request.getLimit());
		}else if(request.getPagenumber()==0 && request.getLimit()>0) {
			typedQuery.setFirstResult((request.getPagenumber())*request.getLimit());
			typedQuery.setMaxResults(request.getLimit());
		}else if(request.getPagenumber()==0 && request.getLimit()==0) {
			List<Tuple> tupleList = typedQuery.getResultList();
			HashMap<String,Object> map = new HashMap<>();
			map.put("result",tupleList);
			map.put("rowCount", rowCount);
			return map;
		}
		List<Tuple> tupleList = typedQuery.getResultList();
		HashMap<String,Object> map = new HashMap<>();
		map.put("result",tupleList);
		map.put("rowCount", rowCount);
		return map;
	}
}
