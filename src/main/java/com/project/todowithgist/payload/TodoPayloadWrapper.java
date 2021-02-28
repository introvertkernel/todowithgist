package com.project.todowithgist.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TodoPayloadWrapper {

	@JsonProperty("ResponseHeader")
	private ResponseHeader responseHeader;
	@JsonProperty("TodoList")
	private List<TodoPayload> todoPayload;

	public ResponseHeader getResponseHeader() {
		return responseHeader;
	}

	public void setResponseHeader(ResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}

	public List<TodoPayload> getTodoPayload() {
		return todoPayload;
	}

	public void setTodoPayload(List<TodoPayload> list) {
		this.todoPayload = list;
	}

	public TodoPayloadWrapper(ResponseHeader responseHeader, List<TodoPayload> todoPayload) {
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
