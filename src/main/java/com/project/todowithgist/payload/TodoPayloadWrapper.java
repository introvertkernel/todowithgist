package com.project.todowithgist.payload;

import java.util.List;

import com.project.todowithgist.domain.Todo;

public class TodoPayloadWrapper {

	private ResponseHeader responseHeader;
	private List<Todo> todoPayload;

	public ResponseHeader getResponseHeader() {
		return responseHeader;
	}

	public void setResponseHeader(ResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}

	public List<Todo> getTodoPayload() {
		return todoPayload;
	}

	public void setTodoPayload(List<Todo> list) {
		this.todoPayload = list;
	}

	public TodoPayloadWrapper(ResponseHeader responseHeader, List<Todo> todoPayload) {
		super();
		this.responseHeader = responseHeader;
		this.todoPayload = todoPayload;
	}

	public TodoPayloadWrapper() {
		super();
	}

	@Override
	public String toString() {
		return "TodoPayloadWrapper [responseHeader=" + responseHeader + ", todoPayload=" + todoPayload + "]";
	}

}
