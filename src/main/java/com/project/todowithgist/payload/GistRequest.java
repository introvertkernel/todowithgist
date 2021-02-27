package com.project.todowithgist.payload;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GistRequest {
	@JsonProperty("description")
	private String description;

	@JsonProperty("files")
	private Map<String, Map<String, String>> filesMap = new HashMap<>();

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, Map<String, String>> getFilesMap() {
		return filesMap;
	}

	public void setFilesMap(Map<String, Map<String, String>> filesMap) {
		this.filesMap = filesMap;
	}

	public GistRequest(String description, Map<String, Map<String, String>> filesMap) {
		super();
		this.description = description;
		this.filesMap = filesMap;
	}

	@Override
	public String toString() {
		return "GistRequest [description=" + description + ", filesMap=" + filesMap + "]";
	}

}
