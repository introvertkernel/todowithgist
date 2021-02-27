package com.project.todowithgist.payload;

public class ProjectPayload {

	private String projectName;
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

	public ProjectPayload(String projectName, String projectDescription) {
		super();
		this.projectName = projectName;
		this.projectDescription = projectDescription;
	}

	@Override
	public String toString() {
		return "ProjectPayload [projectName=" + projectName + ", projectDescription=" + projectDescription + "]";
	}

}
