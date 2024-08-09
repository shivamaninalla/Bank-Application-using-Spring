package com.monocept.app.entity;

import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="FileData")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileData {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name="Type")
	private String type;
	
	@Lob
	@Column(name="filedata")
	private byte[] fileData;

	public FileData(long id, String name, String type, byte[] fileData) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.fileData = fileData;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	@Override
	public String toString() {
		return "FileData [id=" + id + ", name=" + name + ", type=" + type + ", fileData=" + Arrays.toString(fileData)
				+ "]";
	}

	public FileData() {
		super();
	}
	
	

}
