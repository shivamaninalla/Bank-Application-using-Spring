package com.monocept.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.monocept.app.entity.FileData;
import com.monocept.app.repository.StorageRepository;

@Service
public class StorageService {
	
	@Autowired
	private StorageRepository repository;
	
	public String uploadFile(MultipartFile file) {
		repository.save(FileData)
		
	}

}
