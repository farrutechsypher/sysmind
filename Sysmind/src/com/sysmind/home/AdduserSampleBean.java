


package com.sysmind.home;

import java.io.Serializable;

public class AdduserSampleBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String value;
//	private String extension;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
//	public String getExtension() {
//		return extension;
//	}
//	public void setExtension(String extension) {
//		this.extension = extension;
//	}
}
