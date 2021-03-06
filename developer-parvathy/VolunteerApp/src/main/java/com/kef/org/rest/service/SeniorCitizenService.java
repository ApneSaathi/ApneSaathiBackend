package com.kef.org.rest.service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.kef.org.rest.config.FileStorageConfig;
import com.kef.org.rest.domain.model.InProgressQueryRequestVO;
import com.kef.org.rest.domain.model.InProgressQueryResponseVO;
import com.kef.org.rest.domain.model.InputVO;
import com.kef.org.rest.domain.model.ProgressUpdates;
import com.kef.org.rest.domain.model.SeniorCitizenQueryResponse;
import com.kef.org.rest.domain.model.SrCitizenCsvVO;
import com.kef.org.rest.domain.model.SrCitizenDetailsResponse;
import com.kef.org.rest.domain.model.SrCitizenListResponse;
import com.kef.org.rest.domain.model.SrCitizenQueriesRequestVO;
import com.kef.org.rest.domain.model.SrCitizenQueryResponseVO;
import com.kef.org.rest.domain.model.SrCitizenVO;
import com.kef.org.rest.domain.model.UploadFileResponseVO;
import com.kef.org.rest.domain.model.VolunteerAssignmentVO;
import com.kef.org.rest.exception.FileStorageException;
import com.kef.org.rest.model.GreivanceTracking;
import com.kef.org.rest.model.GreivanceUpdateStatus;
import com.kef.org.rest.model.MedicalandGreivance;
import com.kef.org.rest.model.SeniorCitizen;
import com.kef.org.rest.model.Volunteer;
import com.kef.org.rest.model.VolunteerAssignment;
import com.kef.org.rest.repository.GreivanceTrackingRepository;
import com.kef.org.rest.repository.GreivanceUpdateStatusRepository;
import com.kef.org.rest.repository.SeniorCitizenRepository;
import com.kef.org.rest.repository.VolunteerAssignmentRepository;
import com.kef.org.rest.repository.VolunteerRatingRepository;
import com.kef.org.rest.repository.VolunteerRepository;
import com.kef.org.rest.utils.Constants;
import com.kef.org.rest.utils.FileUtils;
import com.kef.org.rest.validator.SrCitizenValidator;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

@Service("srCitizenService")
public class SeniorCitizenService {

	@Autowired
	private SeniorCitizenRepository srCitizenRespository;

	@Autowired
	private VolunteerAssignmentRepository volunteerassignmentRespository;

	@Autowired
	private VolunteerRepository volunteerRepository;

	@Autowired
	private VolunteerRatingRepository volunteerRatingRepository;

	@Autowired
	private GreivanceTrackingRepository greivanceRepository;

	@Autowired
	private GreivanceUpdateStatusRepository greivanceupdateRepo;

	@Autowired
	private UploadFilesInfoService filesInfoService;

	private final java.nio.file.Path uploadFileStorageLocation;
	private final java.nio.file.Path errorFileStorageLocation;

	@Autowired
	public SeniorCitizenService(FileStorageConfig fileStorageConfig) {
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

	public static final Logger logger = LoggerFactory.getLogger(SeniorCitizenService.class);
	@Autowired
	private EntityManager em;

	public SrCitizenListResponse getSeniorCitizen(SrCitizenVO srCitizenStatus) {
		String status = srCitizenStatus.getStatus();
		Integer limit = null;
		Integer pagenumber = null;
		Integer totalSrCitizen;
		String filterState = srCitizenStatus.getFilterState();
		String filterDistrict = srCitizenStatus.getFilterDistrict();
		String filterBlock = srCitizenStatus.getFilterBlock();
		SrCitizenListResponse SrCitizenresponse = new SrCitizenListResponse();
		List<SeniorCitizen> result;
		Page<SeniorCitizen> page = null;
		List<SrCitizenVO> srCitizenVOList;
		Pageable ptry;
		if (srCitizenStatus.getLimit() == null && srCitizenStatus.getPagenumber() == null) {
			ptry = null;
		} else {
			limit = srCitizenStatus.getLimit();
			pagenumber = srCitizenStatus.getPagenumber();
			ptry = PageRequest.of(pagenumber, limit);
		}

		if (filterState != null && filterDistrict == null && filterBlock == null) {
			page = srCitizenRespository.findByStatusAndStateIgnoreCase(status, filterState, ptry);
			result = page.getContent();
			totalSrCitizen = (int) page.getTotalElements();
		} else if (filterState != null && filterDistrict != null && filterBlock == null) {
			page = srCitizenRespository.findByStatusAndStateAndDistrictIgnoreCase(status, filterState, filterDistrict,
					ptry);
			result = page.getContent();
			totalSrCitizen = (int) page.getTotalElements();
		} else if (filterState != null && filterDistrict != null && filterBlock != null) {
			page = srCitizenRespository.findByStatusAndStateAndDistrictAndBlockNameIgnoreCase(status, filterState,
					filterDistrict, filterBlock, ptry);
			result = page.getContent();
			totalSrCitizen = (int) page.getTotalElements();
		} else {
			page = srCitizenRespository.findAllByStatusIgnoreCase(status, ptry);
			result = page.getContent();
			totalSrCitizen = (int) page.getTotalElements();
		}
//		SrCitizenresponse.setSrCitizenList(result);
		srCitizenVOList = SrCitizenToSrCitizenVO(result);
		if (!srCitizenVOList.isEmpty() && srCitizenVOList != null) {
			SrCitizenresponse.setSrCitizenList(srCitizenVOList);
		} else {
			SrCitizenresponse.setSrCitizenList(null);
		}
		SrCitizenresponse.setTotalSrCitizen(totalSrCitizen);
		return SrCitizenresponse;
	}

	public void updateStatus(String status, Integer id) {
		srCitizenRespository.updateStatus(status, id);
	}

	public List<SeniorCitizen> srCitizenAssignedToVol(Integer idvolunteer) {
		List<VolunteerAssignment> vol = new ArrayList<>();
		Optional<SeniorCitizen> srCitizen;
		SeniorCitizen sr = new SeniorCitizen();
		List<SeniorCitizen> srCitizenList = new ArrayList<>();
		vol = volunteerassignmentRespository.findAllByIdvolunteerAndStatus(idvolunteer, "Assigned");
		if (vol != null && !vol.isEmpty()) {
			for (VolunteerAssignment va : vol) {
				srCitizen = srCitizenRespository.findAllByPhoneNoAndFirstNameIgnoreCase(va.getPhonenosrcitizen(),
						va.getNamesrcitizen());
				if (srCitizen.isPresent()) {
					sr = srCitizen.get();
					srCitizenList.add(sr);
				}
			}
		}
		return srCitizenList;
	}

	public VolunteerAssignmentVO mapVoluntereAssignmentToEntity(VolunteerAssignment vol, Integer srCitizenId) {
		VolunteerAssignmentVO volAss = new VolunteerAssignmentVO();
		volAss.setIdvolunteer(vol.getIdvolunteer());
		volAss.setNamesrcitizen(vol.getNamesrcitizen());
		volAss.setPhonenosrcitizen(vol.getPhonenosrcitizen());
		volAss.setCallid(vol.getCallid());
		volAss.setAgesrcitizen(vol.getAgesrcitizen());
		volAss.setGendersrcitizen(vol.getGendersrcitizen());
		volAss.setAddresssrcitizen(vol.getAddresssrcitizen());
		volAss.setEmailsrcitizen(vol.getEmailsrcitizen());
		volAss.setStatesrcitizen(vol.getStatesrcitizen());
		volAss.setDistrictsrcitizen(vol.getDistrictsrcitizen());
		volAss.setBlocknamesrcitizen(vol.getBlocknamesrcitizen());
		volAss.setVillagesrcitizen(vol.getVillagesrcitizen());
		volAss.setAssignedbyMember(vol.getAssignedbyMember());
		volAss.setCallstatusCode(vol.getCallstatusCode());
		volAss.setRemarks(vol.getRemarks());
		volAss.setTalkedwith(vol.getTalkedwith());
		volAss.setRole(vol.getRole());
		volAss.setAdminId(vol.getAdminId());
		volAss.setStatus(vol.getStatus());
		volAss.setIdSrCitizen(srCitizenId);
		return volAss;
	}

	public SrCitizenDetailsResponse getSrCitizenPersonalInfo(Integer id) {
		Optional<SeniorCitizen> srCitizen = srCitizenRespository.findById(id);
		SeniorCitizen srCiti = new SeniorCitizen();
		Integer idvolunteer;
		Volunteer volunteer;
		SrCitizenDetailsResponse srCitizenResponse = new SrCitizenDetailsResponse();
		List<VolunteerAssignment> volAssigned = new ArrayList<VolunteerAssignment>();
		List<MedicalandGreivance> medicalList = new ArrayList<>();
		if (srCitizen.isPresent()) {
			srCiti = srCitizen.get();
			volAssigned = volunteerassignmentRespository.findByPhonenosrcitizenAndStatusIgnoreCase(srCiti.getPhoneNo(),
					"Assigned");
			if (!volAssigned.isEmpty() && volAssigned != null) {
				idvolunteer = volAssigned.get(0).getIdvolunteer();
				srCitizenResponse.setTalked_with(volAssigned.get(0).getTalkedwith());
				volunteer = volunteerRepository.findbyidvolunteer(idvolunteer);
//					medicalList=medicalRepository.findByCallid(volAssigned.get(0).getCallid());
				medicalList = volAssigned.get(0).getMedicalandgreivance();
				if (!medicalList.isEmpty() && medicalList != null) {
					srCitizenResponse.setMedicalGreivanceList(medicalList);
				}
//					srCitizenResponse.setGreivanceTracking();
//					volunteer.getVolunteercallList();
				srCitizenResponse.setVolunteerId(idvolunteer);
				srCitizenResponse.setVolunteerFirstName(volunteer.getFirstName());
				srCitizenResponse.setVolunteerLastName(volunteer.getLastName());
				srCitizenResponse.setVolunteerContact(volunteer.getphoneNo());
				srCitizenResponse.setVolunteerRating(volunteerRatingRepository.getAvgRating(idvolunteer));
			}
			srCitizenResponse.setSrCitizenId(srCiti.getSrCitizenId());
			srCitizenResponse.setFirstName(srCiti.getFirstName());
			srCitizenResponse.setAge(srCiti.getAge());
			srCitizenResponse.setPhoneNo(srCiti.getPhoneNo());
			srCitizenResponse.setState(srCiti.getState());
			srCitizenResponse.setDistrict(srCiti.getDistrict());
			srCitizenResponse.setBlockName(srCiti.getBlockName());
			srCitizenResponse.setAddress(srCiti.getAddress());
			srCitizenResponse.setVillage(srCiti.getVillage());
			srCitizenResponse.setEmailID(srCiti.getEmailID());
			srCitizenResponse.setStatus(srCiti.getStatus());
			srCitizenResponse.setGender(srCiti.getGender());
			srCitizenResponse.setLastName(srCiti.getLastName());
		}
		return srCitizenResponse;
	}

	public List<SrCitizenVO> SrCitizenToSrCitizenVO(List<SeniorCitizen> srCitizen) {
		List<SrCitizenVO> result = new ArrayList<>();
		List<VolunteerAssignment> volAssigned = new ArrayList<VolunteerAssignment>();
		Volunteer volunteer;
		if (srCitizen != null && !srCitizen.isEmpty()) {
			for (SeniorCitizen sr : srCitizen) {
				SrCitizenVO seniorCitizen = new SrCitizenVO();
				seniorCitizen.setSrCitizenId(sr.getSrCitizenId());
				seniorCitizen.setStatus(sr.getStatus());
				seniorCitizen.setDeboardedOn(sr.getDeboardedOn());
				seniorCitizen.setReasons(sr.getReasons());
				seniorCitizen.setFirstName(sr.getFirstName());
				seniorCitizen.setAge(sr.getAge());
				seniorCitizen.setGender(sr.getGender());
				seniorCitizen.setPhoneNo(sr.getPhoneNo());
				seniorCitizen.setState(sr.getState());
				seniorCitizen.setDistrict(sr.getDistrict());
				seniorCitizen.setAddress(sr.getAddress());
				seniorCitizen.setBlockName(sr.getBlockName());
				seniorCitizen.setVillage(sr.getVillage());
				seniorCitizen.setEmailID(sr.getEmailID());
				seniorCitizen.setLastName(sr.getLastName());
				volAssigned = volunteerassignmentRespository.findByPhonenosrcitizenAndStatusIgnoreCase(sr.getPhoneNo(),
						"Assigned");
				if (!volAssigned.isEmpty() && volAssigned != null) {
					volunteer = volunteerRepository.findbyidvolunteer(volAssigned.get(0).getIdvolunteer());

					seniorCitizen.setAssignedVolunteer(volunteer.getIdvolunteer());
					seniorCitizen.setAssignedVolunteerName(volunteer.getFirstName() + " " + volunteer.getLastName());
				}
				result.add(seniorCitizen);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<SrCitizenQueryResponseVO> getSeniorCitizenQueries(SrCitizenQueriesRequestVO request) {
		/**
		 * Validating the request
		 */
		request = validateSrCitizenQueriesRequest(request);
		/***
		 * fetching the data using criteria
		 */
		Map<String, Object> resultMap = fetchSrCitizenQueries(request);
		List<Tuple> tupleList = null;
		Integer rowCount = 0;
		if (resultMap != null) {
			tupleList = (List<Tuple>) resultMap.get("result");
			rowCount = (Integer) resultMap.get("rowCount");
		}
		/**
		 * setting the response
		 */
		SrCitizenQueryResponseVO responseVO = new SrCitizenQueryResponseVO();
		List<SeniorCitizenQueryResponse> responseList = new ArrayList<>();
		HttpStatus httpStatus;
		if (tupleList != null && !tupleList.isEmpty()) {
			tupleList.forEach(row -> {
				SeniorCitizenQueryResponse response = new SeniorCitizenQueryResponse();
				response.setName(row.get(0) != null ? String.valueOf(row.get(0)) : "");
				response.setIssueId(row.get(1) != null ? Integer.valueOf(String.valueOf(row.get(1))) : null);
				response.setIssuesRaised(row.get(2) != null ? String.valueOf(row.get(2)) : "");
				response.setState(row.get(3) != null ? String.valueOf(row.get(3)) : "");
				response.setDistrict(row.get(4) != null ? String.valueOf(row.get(4)) : "");
				response.setBlock(row.get(5) != null ? String.valueOf(row.get(5)) : "");
				response.setCreatedOn(row.get(6) != null ? String.valueOf(row.get(6)) : "");
				response.setPriority(row.get(7) != null ? String.valueOf(row.get(7)) : "");
				response.setLastUpdatedOn(row.get(8) != null ? String.valueOf(row.get(8)) : "");
				response.setResolvedOn(row.get(9) != null ? String.valueOf(row.get(9)) : "");
				responseList.add(response);
			});
			responseVO.setQueries(responseList);
			responseVO.setTotalQueriesCount(rowCount);
			responseVO.setMessage("Success");
			responseVO.setStatusCode("0");
			httpStatus = HttpStatus.OK;
		} else {
			responseVO.setTotalQueriesCount(0);
			responseVO.setMessage("Failure");
			responseVO.setStatusCode("1");
			httpStatus = HttpStatus.CONFLICT;
		}

		return new ResponseEntity<SrCitizenQueryResponseVO>(responseVO, httpStatus);
	}

	/***
	 * RequestVO validation for SrCitizenQueries API
	 * 
	 * @param request
	 * @return
	 */
	public SrCitizenQueriesRequestVO validateSrCitizenQueriesRequest(SrCitizenQueriesRequestVO request) {
		request.setQueryType(request.getQueryType() != null ? request.getQueryType().trim() : "");
		request.setState(request.getState() != null ? request.getState().trim() : "");
		request.setDistrict(request.getDistrict() != null ? request.getDistrict().trim() : "");
		request.setBlock(request.getBlock() != null ? request.getBlock().trim() : "");
		request.setSortBy(request.getSortBy() != null ? request.getSortBy() : "");
		request.setSortType(request.getSortType() != null ? request.getSortType().trim() : "");
		request.setPageNumber(
				request.getPageNumber() != null && request.getPageNumber() != 0 ? request.getPageNumber() : 1);
		request.setLimit(request.getLimit() != null && request.getLimit() != 0 ? request.getLimit() : 10);

		if (request.getQueryType().equalsIgnoreCase("pending"))
			request.setQueryType("RAISED");
		else if (request.getQueryType().equalsIgnoreCase("In progress"))
			request.setQueryType("UNDER REVIEW");
		else if (request.getQueryType().equalsIgnoreCase("Resolved"))
			request.setQueryType("RESOLVED");
		return request;
	}

	/***
	 * fetch data for SrCitizenQueries API
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Object> fetchSrCitizenQueries(SrCitizenQueriesRequestVO request) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> gtQuery = builder.createTupleQuery();

		Root<GreivanceTracking> gtRoot = gtQuery.from(GreivanceTracking.class);
		Root<SeniorCitizen> scRoot = gtQuery.from(SeniorCitizen.class);

		List<Predicate> conditions = new ArrayList<>();
		conditions.add(builder.equal(gtRoot.get("phoneNo"), scRoot.get("phoneNo")));
		conditions.add(builder.equal(gtRoot.get("status"), request.getQueryType()));

		if (!request.getState().isEmpty())
			conditions.add(builder.equal(scRoot.get("state"), request.getState()));
		if (!request.getDistrict().isEmpty())
			conditions.add(builder.equal(scRoot.get("district"), request.getDistrict()));
		if (!request.getBlock().isEmpty())
			conditions.add(builder.equal(scRoot.get("blockName"), request.getBlock()));

		gtQuery.multiselect(gtRoot.get("namesrcitizen"), gtRoot.get("trackingId"), gtRoot.get("greivanceType"),
				scRoot.get("state"), scRoot.get("district"), scRoot.get("blockName"), gtRoot.get("createdDate"),
				gtRoot.get("priority"), gtRoot.get("lastUpdatedOn"), gtRoot.get("resolvedDate"));

		TypedQuery<Tuple> typedQuery;

		if (!request.getSortBy().isEmpty()) {
			String sortType = request.getSortType().isEmpty() ? "asc" : request.getSortType();
			String sortBy = request.getSortBy();

			if (sortType.equalsIgnoreCase("asc")) {
				typedQuery = em.createQuery(gtQuery.where(conditions.toArray(new Predicate[] {})).orderBy(builder.asc(
						sortBy.equalsIgnoreCase("priority") ? gtRoot.get("priority") : gtRoot.get("createdDate"))));
			} else {
				typedQuery = em.createQuery(gtQuery.where(conditions.toArray(new Predicate[] {})).orderBy(builder.desc(
						sortBy.equalsIgnoreCase("priority") ? gtRoot.get("priority") : gtRoot.get("createdDate"))));
			}
		} else {
			typedQuery = em.createQuery(gtQuery.where(conditions.toArray(new Predicate[] {})));
		}

		Integer rowCount = typedQuery.getResultList().size();
		typedQuery.setFirstResult((request.getPageNumber() - 1) * request.getLimit());
		typedQuery.setMaxResults(request.getLimit());
		List<Tuple> tupleList = typedQuery.getResultList();
		HashMap<String, Object> map = new HashMap<>();
		map.put("result", tupleList);
		map.put("rowCount", rowCount);
		return map;
	}

	public Boolean updateGreivancestatus(GreivanceTracking greivanceTracking) {
		GreivanceTracking greivanceTrack = new GreivanceTracking();
		Boolean flag = false;
		greivanceTrack = greivanceRepository.findbytrackingid(greivanceTracking.getTrackingId());
		if (null != greivanceTrack) {
			flag = true;
			greivanceTrack.setStatus(greivanceTracking.getStatus());
			// update greivance based on status
			if (greivanceTracking.getStatus().equalsIgnoreCase("RAISED")) {
				greivanceTrack.setDescription(greivanceTracking.getDescription());
				greivanceTrack.setRaisedby(greivanceTracking.getRaisedby());
				greivanceTrack.setCreatedDate(LocalDateTime.now());
			}
			if (greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW")) {
				greivanceTrack.setReviewedby(greivanceTracking.getReviewedby());
				greivanceTrack.setUnderReviewRemarks(greivanceTracking.getDescription());
				greivanceTrack.setUnderReviewDate(LocalDateTime.now());
			}
			if (greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED")) {
				greivanceTrack.setResolvedby(greivanceTracking.getReviewedby());
				greivanceTrack.setResolvedRemarks(greivanceTracking.getDescription());
				greivanceTrack.setResolvedDate(LocalDateTime.now());
			}
			greivanceTrack.setLastUpdatedOn(LocalDateTime.now());
			greivanceRepository.save(greivanceTrack);
			// insert updated data in greivance table
			insertToGreivanceupdate(greivanceTrack);
		}
		return flag;
	}

	// insert data in greivance update table
	private void insertToGreivanceupdate(GreivanceTracking greivanceTrack) {
		// TODO Auto-generated method stub
		GreivanceUpdateStatus greivanceupdate = new GreivanceUpdateStatus();
		greivanceupdate.setAdminId(greivanceTrack.getAdminId());
		greivanceupdate.setCallid(greivanceTrack.getCallid());
		greivanceupdate.setCreatedBy(greivanceTrack.getCreatedBy());
		greivanceupdate.setCreatedDate(LocalDateTime.now());
		greivanceupdate.setDescription(greivanceTrack.getDescription());
		greivanceupdate.setGreivanceType(greivanceTrack.getGreivanceType());
		greivanceupdate.setIdgrevance(greivanceTrack.getIdgrevance());
		greivanceupdate.setLastUpdatedOn(greivanceTrack.getLastUpdatedOn());
		greivanceupdate.setPriority(greivanceTrack.getPriority());
		greivanceupdate.setRaisedby(greivanceTrack.getRaisedby());
		greivanceupdate.setResolvedby(greivanceTrack.getResolvedby());
		greivanceupdate.setResolvedDate(greivanceTrack.getResolvedDate());
		greivanceupdate.setResolvedRemarks(greivanceTrack.getResolvedRemarks());
		greivanceupdate.setReviewedby(greivanceTrack.getReviewedby());
		greivanceupdate.setRole(greivanceTrack.getRole());
		greivanceupdate.setStatus(greivanceTrack.getStatus());
		greivanceupdate.setTrackingId(greivanceTrack.getTrackingId());
		greivanceupdate.setUnderReviewDate(greivanceTrack.getUnderReviewDate());
		greivanceupdate.setUnderReviewRemarks(greivanceTrack.getUnderReviewRemarks());
		greivanceupdateRepo.save(greivanceupdate);
	}

	public Boolean deboardSrCitizen(InputVO inputVO) {
		// TODO Auto-generated method stub
		Boolean flag = false;
		Optional<SeniorCitizen> srCitizen;
		SeniorCitizen senior = new SeniorCitizen();
		srCitizen = srCitizenRespository.findById(inputVO.getId());
		List<VolunteerAssignment> volAssignment;
		if (srCitizen.isPresent()) {
			flag = true;
			senior = srCitizen.get();
			senior.setStatus("Deboarded");
			senior.setReasons(inputVO.getDeboardReasons());
			senior.setDeboardedOn(LocalDateTime.now());
			srCitizenRespository.save(senior);
			volAssignment = volunteerassignmentRespository.findByPhonenosrcitizen(inputVO.getPhoneNo());
			for (VolunteerAssignment va : volAssignment) {
				va.setStatus("Deboarded");
			}
			volunteerassignmentRespository.saveAll(volAssignment);
		}
		return flag;
	}

	public ResponseEntity<InProgressQueryResponseVO> getInProgressQueryDetails(InProgressQueryRequestVO request) {
		InProgressQueryResponseVO response = new InProgressQueryResponseVO();
		request.setIssueId(request.getIssueId() != null ? request.getIssueId() : 0);
		String status = request.getStatus() != null ? request.getStatus() : "UNDER REVIEW";
		if (status.equalsIgnoreCase("In Progress")) {
			status = "UNDER REVIEW";
		}
		Optional<GreivanceTracking> gtOpt = greivanceRepository.findById(request.getIssueId());
		Integer volunteerId;
		String srCitizenPhoneNo = null;
		HttpStatus httpStatus;
		if (gtOpt.isPresent()) {
			GreivanceTracking gtEntity = gtOpt.get();
			volunteerId = gtEntity.getIdvolunteer();
			response.setGreivancedesc(gtEntity.getDescription());
			srCitizenPhoneNo = gtEntity.getPhoneNo();
			Optional<Volunteer> volunteerOpt = volunteerRepository.findById(volunteerId);
			if (volunteerOpt.isPresent()) {
				Volunteer volunteerEntity = volunteerOpt.get();
				response.setVolunteerFirstName(volunteerEntity.getFirstName());
				response.setVolunteerLastName(volunteerEntity.getLastName());
			}
			SeniorCitizen srCitizen = srCitizenRespository.findByPhoneNo(srCitizenPhoneNo);
			response.setSrCitizenFirstName(srCitizen.getFirstName());
			response.setSrCitizenLastName(srCitizen.getLastName());
			List<ProgressUpdates> progressUpdates = new ArrayList<>();
			Optional<List<GreivanceUpdateStatus>> grvUpdateStatusList = greivanceupdateRepo
					.findByTrackingIdAndStatusOrderByUnderReviewDateDesc(request.getIssueId(), status);
			if (grvUpdateStatusList.isPresent()) {
				grvUpdateStatusList.get().forEach(list -> {
					ProgressUpdates updates = new ProgressUpdates();
					updates.setUnderReviewRemarks(list.getUnderReviewRemarks());
					updates.setUnderReviewDate(list.getUnderReviewDate());
					updates.setReviewedBy(list.getReviewedby());
					updates.setResolvedRemarks(list.getResolvedRemarks());
					updates.setResolvedDate(list.getResolvedDate());
					updates.setResolvedBy(list.getResolvedby());
					progressUpdates.add(updates);
				});
			}
			response.setProgressUpdates(progressUpdates);
			response.setMessage(Constants.SUCCESS);
			response.setStatusCode(Constants.ZERO);
			httpStatus = HttpStatus.OK;
		} else {
			response.setStatusCode(Constants.ONE);
			response.setMessage(Constants.FAILURE);
			httpStatus = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<InProgressQueryResponseVO>(response, httpStatus);
	}

	public UploadFileResponseVO uploadFile(MultipartFile file) throws IOException {
		UploadFileResponseVO responseVO = new UploadFileResponseVO();
		if (!file.isEmpty() && FileUtils.isCsv(file)) {
			// Store uploaded File
			long timeStamp = System.currentTimeMillis();
			String uploadFileName = StringUtils.cleanPath(Constants.SR_CITIZEN + Constants.UNDERSCORE + Constants.UPLOAD
					+ Constants.UNDERSCORE + timeStamp + Constants.CSV_EXTENSION);
			FileUtils.storeFile(file, uploadFileStorageLocation, uploadFileName);

			// Load volunteers from file
			List<SrCitizenCsvVO> allSeniorCitizens = getSrCitizensFromFile(uploadFileName);

			// Get all mobile numbers from db
			Set<String> allMobileNumberSet = srCitizenRespository.fetchAllPhoneNumbers().stream()
					.collect(Collectors.toSet());

			// Validate all volunteers
			Map<Boolean, List<SrCitizenCsvVO>> validatedSrCitizensMap = allSeniorCitizens.stream().collect(
					Collectors.partitioningBy(citizen -> SrCitizenValidator.isValid(citizen, allMobileNumberSet)));

			// Save valid volunteers
			List<SeniorCitizen> validSrCitizens = validatedSrCitizensMap.get(true).stream().map(vo -> vo.toSeniorCitizen())
					.collect(Collectors.toList());
			srCitizenRespository.saveAll(validSrCitizens);

			String errorFileName = null;
			// save error file
			if (CollectionUtils.isNotEmpty(validatedSrCitizensMap.get(false))) {
				errorFileName = StringUtils.cleanPath(Constants.SR_CITIZEN + Constants.UNDERSCORE + Constants.ERROR
						+ Constants.UNDERSCORE + timeStamp + Constants.CSV_EXTENSION);
				FileUtils.writeToCSV(validatedSrCitizensMap.get(false), errorFileStorageLocation, errorFileName);
			}
			// save upload and error file details in db
			int successCount = validatedSrCitizensMap.get(true).size();
			int failureCount = validatedSrCitizensMap.get(false).size();
			filesInfoService.saveInfo(uploadFileName, errorFileName, null, successCount, failureCount);

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

	private List<SrCitizenCsvVO> getSrCitizensFromFile(String file)
			throws java.io.FileNotFoundException, IOException {
		List<SrCitizenCsvVO> allVolunteers = new ArrayList<>();
		String filePath = FileUtils.loadFileAsResource(file, this.uploadFileStorageLocation).getFile().getPath();
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
			String[] line;
			String[] columns = reader.readNext();
			SrCitizenValidator.validateColumns(columns);
			while ((line = reader.readNext()) != null) {
				allVolunteers.add(createSrCitizen(columns, line));
			}
		} catch (CsvValidationException e) {
			throw new FileStorageException(Constants.ERROR_COULD_NOT_READ_THE_FILE_SERVER);
		}
		return allVolunteers;
	}

	private SrCitizenCsvVO createSrCitizen(String[] columns, String[] attributes) {
		SrCitizenCsvVO srCitizenCsvVO = new SrCitizenCsvVO();
		for (int i = 0; i < columns.length; i++) {
			Constants.SrCitizenCSVColumns column = Constants.SrCitizenCSVColumns.fromString(columns[i]);
			switch (column) {
			case FIRSTNAME:
				srCitizenCsvVO.setFirstName(attributes[i]);
				break;
			case LASTNAME:
				srCitizenCsvVO.setLastName(attributes[i]);
				break;
			case MOBILE_NO:
				srCitizenCsvVO.setMobileNo(attributes[i]);
				break;
			case EMAIL:
				srCitizenCsvVO.setEmail(attributes[i]);
				break;
			case GENDER:
				srCitizenCsvVO.setGender(attributes[i]);
				break;
			case AGE:
				srCitizenCsvVO.setAge(attributes[i]);
				break;
			case STATE_NAME:
				srCitizenCsvVO.setState(attributes[i]);
				break;
			case DISTRICT_NAME:
				srCitizenCsvVO.setDistrict(attributes[i]);
				break;
			case BLOCK_NAME:
				srCitizenCsvVO.setBlock(attributes[i]);
				break;
			case ADDRESS:
				srCitizenCsvVO.setAddress(attributes[i]);
				break;
			case VILLAGE:
				srCitizenCsvVO.setVillage(attributes[i]);
				break;
			case STATUS:
				srCitizenCsvVO.setStatus(attributes[i]);
				break;
			case REFERRED_BY:
				srCitizenCsvVO.setReferredBy(attributes[i]);
				break;
			case CREATED_BY_VOLUNTEER_ID:
				srCitizenCsvVO.setVolunteerId(attributes[i]);
				break;
			default:
				break;
			}
		}
		List<String> columnList = new ArrayList<>(Arrays.asList(columns));
		columnList.add(Constants.VolunteerCSVColumns.FAILURE_REASON.getColumnName());
		srCitizenCsvVO.setColumns(columnList.toArray(new String[columnList.size()]));
		return srCitizenCsvVO;
	}

	public Resource loadFileAsResource(String fileName, String fileDownloadType) {
		return fileDownloadType.equals(Constants.FILE_DOWNLOAD_TYPE_UPLOAD)
				? FileUtils.loadFileAsResource(fileName, uploadFileStorageLocation)
				: FileUtils.loadFileAsResource(fileName, errorFileStorageLocation);
	}

}
