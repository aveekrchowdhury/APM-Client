/* Generated by JavaFromJSON */
/*http://javafromjson.dashingrocket.com*/

package com.ge.apm.ingestor.beans;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TagElement {
	 @JsonProperty("tagId")
	private java.lang.String tagid;

 	public void setTagid(java.lang.String tagid) {
		this.tagid = tagid;
	}

	public java.lang.String getTagid() {
		return tagid;
	}
	 @JsonProperty("errorMessage")
	private java.lang.Object errormessage;

 	public void setErrormessage(java.lang.Object errormessage) {
		this.errormessage = errormessage;
	}

	public java.lang.Object getErrormessage() {
		return errormessage;
	}

	
	private List<DataElement> data = new ArrayList<DataElement>();
	
 	public void setData(List<DataElement> data) {
		this.data = data;
	}

	public List<DataElement> getData() {
		return data;
	}
	 @JsonProperty("errorCode")
	private java.lang.Object errorcode;

 	public void setErrorcode(java.lang.Object errorcode) {
		this.errorcode = errorcode;
	}

	public java.lang.Object getErrorcode() {
		return errorcode;
	}

}