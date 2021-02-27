package com.project.todowithgist.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProjectPayload {

	private String projectId;
	private String projectName;

	@JsonIgnore
	private String projectDescription;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

//	public ProjectPayload(String projectName, String projectDescription) {
//		super();
//		this.projectName = projectName;
//		this.projectDescription = projectDescription;
//	}
	
	

	public ProjectPayload(String projectId, String projectName, String projectDescription) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectDescription = projectDescription;
	}

	public ProjectPayload() {
		super();
	}

	@Override
	public String toString() {
		return "ProjectPayload [projectId=" + projectId + ", projectName=" + projectName + ", projectDescription="
				+ projectDescription + "]";
	}

}
