package com.ge.apm.ingestor.beans;

import java.util.Arrays;
import java.util.List;

public class IngestReponse {
	private String errorCode;
	private String errorMessage ;
	private String tagList;
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getTagList() {
		return tagList;
	}
	public void setTagList(String tagList) {
		this.tagList = tagList;
	}
	
}
