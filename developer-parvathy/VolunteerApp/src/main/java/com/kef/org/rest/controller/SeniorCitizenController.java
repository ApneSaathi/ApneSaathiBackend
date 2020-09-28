package com.kef.org.rest.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kef.org.rest.domain.model.InProgressQueryRequestVO;
import com.kef.org.rest.domain.model.InProgressQueryResponseVO;
import com.kef.org.rest.domain.model.InputVO;
import com.kef.org.rest.domain.model.SrCitizenDetailsResponse;
import com.kef.org.rest.domain.model.SrCitizenQueriesRequestVO;
import com.kef.org.rest.domain.model.SrCitizenQueryResponseVO;
import com.kef.org.rest.domain.model.UploadFileResponseVO;
import com.kef.org.rest.model.GreivanceTracking;
import com.kef.org.rest.service.SeniorCitizenService;
import com.kef.org.rest.utils.Constants;

@RestController
public class SeniorCitizenController {

	public static final Logger logger = LoggerFactory.getLogger(SeniorCitizenController.class);

	@Autowired
	SeniorCitizenService srCitizenService;

	@RequestMapping(value = "/srCitizenPersonalinfo", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<SrCitizenDetailsResponse> getSrCitizenPersonalInfo(@RequestBody InputVO inputVo) {

		SrCitizenDetailsResponse response = new SrCitizenDetailsResponse();
		response = srCitizenService.getSrCitizenPersonalInfo(inputVo.getId());
		if (response.getSrCitizenId() != null) {

			response.setStatusCode("0");
			response.setMessage("Success");
			;
		} else {

			response.setStatusCode("1");
			response.setMessage("Failure");
		}

		return new ResponseEntity<SrCitizenDetailsResponse>(response,
				response.getStatusCode().equals("0") ? HttpStatus.OK : HttpStatus.CONFLICT);
	}

	/****
	 * API for Senior Citizen Queries
	 * 
	 * @param requestJson
	 * @return
	 */
	@PostMapping(value = "/srCitizenQueries")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ResponseEntity<SrCitizenQueryResponseVO> getSeniorCitizenQueries(
			@RequestBody SrCitizenQueriesRequestVO requestJson) {

		return srCitizenService.getSeniorCitizenQueries(requestJson);
	}

	@RequestMapping(value = "/updateGreivanceStatus", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<SrCitizenQueryResponseVO> updateGreivanceStatus(
			@RequestBody GreivanceTracking greivanceTracking) {

		SrCitizenQueryResponseVO response = new SrCitizenQueryResponseVO();
		Boolean flag;
		flag = srCitizenService.updateGreivancestatus(greivanceTracking);
		if (flag) {

			response.setStatusCode("0");
			response.setMessage("Success");
			;
		} else {

			response.setStatusCode("1");
			response.setMessage("Failure");
		}

		return new ResponseEntity<SrCitizenQueryResponseVO>(response,
				response.getStatusCode().equals("0") ? HttpStatus.OK : HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/deboardSrCitizen", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<SrCitizenQueryResponseVO> deboardSrCitizen(@RequestBody InputVO inputVO) {

		SrCitizenQueryResponseVO response = new SrCitizenQueryResponseVO();
		Boolean flag;
		flag = srCitizenService.deboardSrCitizen(inputVO);
		if (flag) {

			response.setStatusCode("0");
			response.setMessage("Success");
			;
		} else {

			response.setStatusCode("1");
			response.setMessage("Failure");
		}

		return new ResponseEntity<SrCitizenQueryResponseVO>(response,
				response.getStatusCode().equals("0") ? HttpStatus.OK : HttpStatus.CONFLICT);
	}

	/***
	 * API to fetch InProgress Query details
	 * 
	 * @param requestJson
	 * @return
	 */
	@PostMapping("/getInProgressQueryDetails")
	public @ResponseBody ResponseEntity<InProgressQueryResponseVO> getInProgressQueryDetails(
			@RequestBody InProgressQueryRequestVO requestJson) {

		return srCitizenService.getInProgressQueryDetails(requestJson);
	}
	
	@RequestMapping(value = "/importSrCitizen")
	@ResponseBody
	public ResponseEntity<UploadFileResponseVO> importSrCitizen(@RequestParam("file") MultipartFile file) throws IOException {
		UploadFileResponseVO responseVO = srCitizenService.uploadFile(file);

		// create download url for upload
		String uploadFileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadSrCitizenUploadFile/").path(responseVO.getUploadFileName()).toUriString();
		responseVO.setUploadFileDownloadUrl(uploadFileDownloadUri);

		// create download url for error
		if (!StringUtils.isEmpty(responseVO.getErrorFileName())) {
			String errorFileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/downloadSrCitizenErrorFile/").path(responseVO.getErrorFileName()).toUriString();
			responseVO.setErrorFileDownloadUrl(errorFileDownloadUri);
		}

		HttpStatus status = responseVO.getStatusCode() == Constants.ONE ? HttpStatus.CONFLICT : HttpStatus.OK;
		return new ResponseEntity<UploadFileResponseVO>(responseVO, status);
	}

	@GetMapping("/downloadSrCitizenErrorFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadSeniorCitizenErrorFile(@PathVariable String fileName,
			HttpServletRequest request) {
		return loadFileAsResource(request, fileName, Constants.FILE_DOWNLOAD_TYPE_ERROR);
	}

	@GetMapping("/downloadSrCitizenUploadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadSeniorCitizenUploadFile(@PathVariable String fileName,
			HttpServletRequest request) {
		return loadFileAsResource(request, fileName, Constants.FILE_DOWNLOAD_TYPE_UPLOAD);
	}

	private ResponseEntity<Resource> loadFileAsResource(HttpServletRequest request, String fileName,
			String downloadType) {
		Resource resource = srCitizenService.loadFileAsResource(fileName, downloadType);
		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.info(Constants.COULD_NOT_DETERMINE_FILE_TYPE);
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
