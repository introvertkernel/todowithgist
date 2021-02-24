package com.project.todowithgist.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.todowithgist.payload.ResponseHeader;
import com.project.todowithgist.payload.TodoPayload;
import com.project.todowithgist.payload.TodoPayloadWrapper;
import com.project.todowithgist.service.TodoService;
import com.project.todowithgist.utils.CommonConstants;

@RestController
@RequestMapping("/user/projects")
public class TodoAPI {

	@Autowired
	private TodoService todoService;

	@PostMapping("/todo/add")
	public ResponseEntity<ResponseHeader> addTodo(@AuthenticationPrincipal OAuth2User principal,
			@RequestBody TodoPayload todoPayload) {
		ResponseHeader responseHeader = new ResponseHeader();
		try {
			todoService.addTodo(todoPayload, todoPayload.getProjectId());
			responseHeader.setResponseCode(CommonConstants.SUC);
		} catch (Exception e) {
			e.printStackTrace();
			responseHeader.setResponseCode(CommonConstants.FAL);
		}
		return new ResponseEntity<>(responseHeader, HttpStatus.OK);
	}

	@GetMapping("/todo/{projectId}")
	public ResponseEntity<TodoPayloadWrapper> fetchTodo(@AuthenticationPrincipal OAuth2User principal,
			@RequestParam String projectId) {
		TodoPayloadWrapper payloadWrapper = new TodoPayloadWrapper();
		ResponseHeader responseHeader = new ResponseHeader();
		try {
			payloadWrapper.setTodoPayload(todoService.fetchAll(projectId));
			responseHeader.setResponseCode(CommonConstants.SUC);
		} catch (Exception e) {
			e.printStackTrace();
			responseHeader.setResponseCode(CommonConstants.FAL);
		}
		payloadWrapper.setResponseHeader(responseHeader);
		return new ResponseEntity<>(payloadWrapper, HttpStatus.OK);
	}

}
