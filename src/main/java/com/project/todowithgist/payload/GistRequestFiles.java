package com.project.todowithgist.payload;

import java.util.HashMap;
import java.util.Map;

public class GistRequestFiles {

	private Map<String, Map<String, String>> filesMap = new HashMap<>();

	public Map<String, Map<String, String>> getFilesMap() {
		return filesMap;
	}

	public void setFilesMap(Map<String, Map<String, String>> filesMap) {
		this.filesMap = filesMap;
	}
	
	

	public GistRequestFiles(Map<String, Map<String, String>> filesMap) {
		super();
		this.filesMap = filesMap;
	}

	@Override
	public String toString() {
		return "GistRequestFiles [filesMap=" + filesMap + "]";
	}

}
