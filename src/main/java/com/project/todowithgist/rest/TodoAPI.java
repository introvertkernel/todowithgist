package com.project.todowithgist.rest;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
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

//	@PostMapping("/todo/add")
//	public ResponseEntity<ResponseHeader> addTodo(@AuthenticationPrincipal OAuth2User principal,
//			@RequestBody TodoPayload todoPayload) {
//		ResponseHeader responseHeader = new ResponseHeader();
//		try {
//			todoService.addTodo(todoPayload, todoPayload.getProjectId());
//			responseHeader.setResponseCode(CommonConstants.SUC);
//		} catch (Exception e) {
//			e.printStackTrace();
//			responseHeader.setResponseCode(CommonConstants.FAL);
//		}
//		return new ResponseEntity<>(responseHeader, HttpStatus.OK);
//	}

	@GetMapping("/todo")
	public ResponseEntity<TodoPayloadWrapper> fetchTodo(@AuthenticationPrincipal OAuth2User principal,
			@RequestParam String projectId) {
		TodoPayloadWrapper payloadWrapper = new TodoPayloadWrapper();
		ResponseHeader responseHeader = new ResponseHeader();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			payloadWrapper.setTodoPayload(todoService.fetchAll(projectId).stream().map(p -> {
				try {
					return objectMapper.readValue(new ObjectMapper().writeValueAsString(p), TodoPayload.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}).collect(Collectors.toList()));
			responseHeader.setResponseCode(CommonConstants.SUC);
		} catch (Exception e) {
			e.printStackTrace();
			responseHeader.setResponseCode(CommonConstants.FAL);
		}
		payloadWrapper.setResponseHeader(responseHeader);
		return new ResponseEntity<>(payloadWrapper, HttpStatus.OK);
	}

	@DeleteMapping("/todo")
	public ResponseEntity<Object> deleteTodo(@AuthenticationPrincipal OAuth2User principal,
			@RequestParam String todoId) {

		try {
			todoService.deleteTodo(todoId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
	}

}
