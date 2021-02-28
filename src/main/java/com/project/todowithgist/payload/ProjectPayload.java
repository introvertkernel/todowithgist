package com.project.todowithgist.payload;

import java.util.List;

public class ProjectPayload {

	private String projectId;
	private String projectName;
	private List<TodoPayload> todoPayload;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public List<TodoPayload> getTodoPayload() {
		return todoPayload;
	}

	public void setTodoPayload(List<TodoPayload> todoPayload) {
		this.todoPayload = todoPayload;
	}

	public ProjectPayload(String projectId, String projectName, String projectDescription) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
	}

	public ProjectPayload(String projectId, String projectName, List<TodoPayload> todoPayload) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.todoPayload = todoPayload;
	}

	public ProjectPayload() {
		super();
	}

	@Override
	public String toString() {
		return "ProjectPayload [projectId=" + projectId + ", projectName=" + projectName + ", todoPayload="
				+ todoPayload + "]";
	}

}
