package com.project.todowithgist.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.todowithgist.payload.ResponseHeader;
import com.project.todowithgist.payload.TodoPayload;
import com.project.todowithgist.service.TodoService;
import com.project.todowithgist.utils.CommonConstants;

@RestController
@RequestMapping("/user/projects")
public class TodoAPI {

	@Autowired
	private TodoService todoService;

	@GetMapping("/todo")
	public ResponseEntity<List<TodoPayload>> fetchTodo(@AuthenticationPrincipal OAuth2User principal,
			@RequestParam String projectId) {
		List<TodoPayload> payload = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			payload = todoService.fetchAll(projectId).stream().map(p -> {
				try {
					return objectMapper.readValue(new ObjectMapper().writeValueAsString(p), TodoPayload.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}).collect(Collectors.toList());
			return new ResponseEntity<>(payload, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(payload, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@DeleteMapping("/todo")
	public ResponseEntity<Object> deleteTodo(@AuthenticationPrincipal OAuth2User principal,
			@RequestParam String todoId) {
		ResponseHeader responseHeader = new ResponseHeader();
		try {
			todoService.deleteTodo(todoId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseHeader.setErrorCode(CommonConstants.FAL);
			responseHeader.setErrorMessage(e.getMessage());

		}
		return new ResponseEntity<>(responseHeader, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
