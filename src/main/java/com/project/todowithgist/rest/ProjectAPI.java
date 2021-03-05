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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.todowithgist.payload.ProjectPayload;
import com.project.todowithgist.payload.ResponseHeader;
import com.project.todowithgist.service.ProjectService;
import com.project.todowithgist.utils.CommonConstants;

@RestController
@RequestMapping("/api/user/projects")
public class ProjectAPI {

	@Autowired
	private ProjectService projectService;

	@PostMapping("/add")
	public ResponseEntity<ResponseHeader> addProject(@AuthenticationPrincipal OAuth2User principal,
			@RequestBody ProjectPayload projectPayload) {
		ResponseHeader responseHeader = new ResponseHeader();
		try {
			projectService.addProject(projectPayload, principal.getName());
			responseHeader.setResponseCode(CommonConstants.SUC);
			return new ResponseEntity<>(responseHeader, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseHeader.setResponseCode(CommonConstants.FAL);
		}
		return new ResponseEntity<>(responseHeader, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("")
	public ResponseEntity<List<ProjectPayload>> getAllProjects(@AuthenticationPrincipal OAuth2User principal) {
		List<ProjectPayload> projectPayload = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			projectPayload = projectService.fetchAll(principal.getName()).stream().map(p -> {
				try {
					return objectMapper.readValue(new ObjectMapper().writeValueAsString(p), ProjectPayload.class);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				return null;
			}).collect(Collectors.toList());
			return new ResponseEntity<>(projectPayload, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(projectPayload, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@DeleteMapping("")
	public ResponseEntity<Object> deleteProject(@AuthenticationPrincipal OAuth2User principal,
			@RequestParam String projectId) {
		ResponseHeader responseHeader = new ResponseHeader();
		try {
			projectService.deleteProject(projectId);
			responseHeader.setResponseCode(CommonConstants.SUC);
			return new ResponseEntity<>(responseHeader, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

		}
		responseHeader.setResponseCode(CommonConstants.FAL);
		return new ResponseEntity<>(responseHeader, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
