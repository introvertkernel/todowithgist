package com.project.todowithgist.payload;

public class TodoPayload {
	private String todoId;

	private String projectId;

	private String todoDesc;

	private String todoStatus;

	public String getTodoId() {
		return todoId;
	}

	public void setTodoId(String todoId) {
		this.todoId = todoId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTodoDesc() {
		return todoDesc;
	}

	public void setTodoDesc(String todoDesc) {
		this.todoDesc = todoDesc;
	}

	public String getTodoStatus() {
		return todoStatus;
	}

	public void setTodoStatus(String todoStatus) {
		this.todoStatus = todoStatus;
	}

	public TodoPayload(String todoId, String projectId, String todoDesc, String todoStatus) {
		super();
		this.todoId = todoId;
		this.projectId = projectId;
		this.todoDesc = todoDesc;
		this.todoStatus = todoStatus;
	}

	@Override
	public String toString() {
		return "TodoPayload [todoId=" + todoId + ", projectId=" + projectId + ", todoDesc=" + todoDesc + ", todoStatus="
				+ todoStatus + "]";
	}

}
