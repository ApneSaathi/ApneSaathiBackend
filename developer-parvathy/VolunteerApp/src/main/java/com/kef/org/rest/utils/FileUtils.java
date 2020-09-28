package com.kef.org.rest.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.kef.org.rest.domain.model.CSVCompatible;
import com.kef.org.rest.exception.FileNotFoundException;
import com.kef.org.rest.exception.FileStorageException;
import com.opencsv.CSVWriter;

public final class FileUtils {
	public static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

	private FileUtils() {
	}

	public static Boolean isCsv(MultipartFile file) {
		String extension = file.getOriginalFilename().split("\\.")[1];
		return !StringUtils.isEmpty(extension) && extension.equals(Constants.CSV) ? true : false;
	}

	public static String storeFile(MultipartFile file, Path uploadFileStorageLocation, String fileName) {
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = uploadFileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return fileName;
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public static Resource loadFileAsResource(String fileName, Path fileStorageLocation) {
		try {
			Path filePath = fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new FileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new FileNotFoundException("File not found " + fileName, ex);
		}
	}

	public static String writeToCSV(List<? extends CSVCompatible> rowList, Path fileStorageLocation,
			String fileName) {
		Path targetLocation = fileStorageLocation.resolve(fileName);
		try (CSVWriter writer = new CSVWriter(Files.newBufferedWriter(targetLocation, StandardCharsets.UTF_8),
				CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER,
				CSVWriter.DEFAULT_LINE_END)) {
			// adding header to csv
			String[] header = rowList.get(0).getColumns();
			writer.writeNext(header);
			rowList.stream().forEach(row -> writer.writeNext(row.toArray()));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new FileStorageException(e.getMessage());
		}
		return fileName;
	}

}
