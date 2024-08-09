package com.monocept.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.app.entity.FileData;

public interface StorageRepository extends JpaRepository<FileData, Long>{
	
	FileData findByName(String fileName);

}
