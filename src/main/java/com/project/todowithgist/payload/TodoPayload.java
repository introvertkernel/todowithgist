package com.project.todowithgist.payload;

public class TodoPayload {
	private String todoId;

	private String todoDesc;

	private String todoStatus;

	public String getTodoId() {
		return todoId;
	}

	public void setTodoId(String todoId) {
		this.todoId = todoId;
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

	public TodoPayload(String todoId, String todoDesc, String todoStatus) {
		super();
		this.todoId = todoId;
		this.todoDesc = todoDesc;
		this.todoStatus = todoStatus;
	}

	public TodoPayload() {
		super();
	}

	@Override
	public String toString() {
		return "TodoPayload [todoId=" + todoId + ", todoDesc=" + todoDesc + ", todoStatus=" + todoStatus + "]";
	}

}
