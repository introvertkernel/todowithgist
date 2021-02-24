package com.project.todowithgist.payload;

import java.util.List;

import com.project.todowithgist.domain.Project;

public class ProjectPayloadWrapper {

	private ResponseHeader responseHeader;
	private List<Project> projectPayload;
	public ResponseHeader getResponseHeader() {
		return responseHeader;
	}
	public void setResponseHeader(ResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}
	public List<Project> getProjectPayload() {
		return projectPayload;
	}
	public void setProjectPayload(List<Project> list) {
		this.projectPayload = list;
	}
	@Override
	public String toString() {
		return "ProjectPayloadWrapper [responseHeader=" + responseHeader + ", projectPayload=" + projectPayload + "]";
	}
	
	
	
}
