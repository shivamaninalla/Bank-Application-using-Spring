package com.monocept.app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.monocept.app.entity.FileData;
import com.monocept.app.service.StorageService;

@RestController
public class FileController {
	
	@Autowired
	private StorageService storageService;
	
	@PostMapping("/file/upload")
	public ResponseEntity<String> fileUpload(@RequestParam(name="file") MultipartFile multipartFile) throws IOException {
		
		if(multipartFile.isEmpty()) {
			return new ResponseEntity<>("File should not be empty",HttpStatus.BAD_REQUEST);
		}
		
		FileData uploadFile = storageService.uploadFile(multipartFile);
		
		if(uploadFile!=null) {
			return new ResponseEntity<>("File succeess fully uploaded",HttpStatus.ACCEPTED);
		}
		
		return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/download-file")
	public ResponseEntity<byte[]> downloadFile(@RequestParam(name = "fileName") String fileName) throws IOException {
		byte[] resource = storageService.downloadFile(fileName);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg").body(resource);
	}

}
