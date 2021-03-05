package com.project.todowithgist.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TodoPayload {
	private String todoId;

	private String todoDesc;

	private String todoStatus;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date createTs;

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

	public Date getCreateTs() {
		return createTs;
	}

	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}

	public TodoPayload(String todoId, String todoDesc, String todoStatus, Date createTs) {
		super();
		this.todoId = todoId;
		this.todoDesc = todoDesc;
		this.todoStatus = todoStatus;
		this.createTs = createTs;
	}

	public TodoPayload() {
		super();
	}

	@Override
	public String toString() {
		return "TodoPayload [todoId=" + todoId + ", todoDesc=" + todoDesc + ", todoStatus=" + todoStatus + "]";
	}

}
