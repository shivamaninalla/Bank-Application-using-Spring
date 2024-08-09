package com.monocept.app.exception;

import java.time.LocalDateTime;

public class StudentErrorResponse {
	
	private int status;
	private String message;
	private LocalDateTime timeStamp;
	public int getStatus() {
		return status;
	}
	public void setStatus(int i) {
		this.status = i;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(LocalDateTime localDateTime) {
		this.timeStamp = localDateTime;
	}
	public StudentErrorResponse(int status, String message, LocalDateTime timeStamp) {
		super();
		this.status = status;
		this.message = message;
		this.timeStamp = timeStamp;
	}
	public StudentErrorResponse() {
		super();
	}
	@Override
	public String toString() {
		return "StudentErrorResponse [status=" + status + ", message=" + message + ", timeStamp=" + timeStamp + "]";
	}
	
	
	
}

