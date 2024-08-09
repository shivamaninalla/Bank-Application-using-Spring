package com.monocept.app.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.monocept.app.entity.FileData;
import com.monocept.app.entity.ImageUtil;
import com.monocept.app.repository.StorageRepository;

@Service
public class StorageService {
	
	
	
	@Autowired
	private StorageRepository repository;
	
	public FileData uploadFile(MultipartFile file) throws IOException {
		
		 FileData fileData=new FileData(0,file.getOriginalFilename(),file.getContentType(),ImageUtil.compressImage(file.getBytes()));
		
		 FileData save = repository.save(fileData);
		 
		 return save;
	}

	public byte[] downloadFile(String fileName) {
		FileData data = repository.findByName(fileName);
		byte[] dataReturned=ImageUtil.decompressImage(data.getFileData());
		return dataReturned;
	}

}
