package com.project.todowithgist.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectPayloadWrapper {

	@JsonProperty("ResponseHeader")
	private ResponseHeader responseHeader;

	@JsonProperty("projectList")
	private List<ProjectPayload> projectPayload;

	public ResponseHeader getResponseHeader() {
		return responseHeader;
	}

	public void setResponseHeader(ResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}

	public List<ProjectPayload> getProjectPayload() {
		return projectPayload;
	}

	public void setProjectPayload(List<ProjectPayload> list) {
		this.projectPayload = list;
	}

	public ProjectPayloadWrapper(ResponseHeader responseHeader, List<ProjectPayload> projectPayload) {
		super();
		this.responseHeader = responseHeader;
		this.projectPayload = projectPayload;
	}

	public ProjectPayloadWrapper() {
		super();
	}

	@Override
	public String toString() {
		return "ProjectPayloadWrapper [responseHeader=" + responseHeader + ", projectPayload=" + projectPayload + "]";
	}

}
