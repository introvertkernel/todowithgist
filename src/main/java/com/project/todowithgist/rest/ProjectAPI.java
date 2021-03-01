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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.todowithgist.payload.ProjectPayload;
import com.project.todowithgist.payload.ProjectPayloadWrapper;
import com.project.todowithgist.payload.ResponseHeader;
import com.project.todowithgist.service.ProjectService;
import com.project.todowithgist.utils.CommonConstants;

@RestController
@RequestMapping("/user/projects")
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
	public ResponseEntity<ProjectPayloadWrapper> getAllProjects(@AuthenticationPrincipal OAuth2User principal) {
		ResponseHeader responseHeader = new ResponseHeader();
		ProjectPayloadWrapper payloadWrapper = new ProjectPayloadWrapper();
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			payloadWrapper.setProjectPayload(projectService.fetchAll(principal.getName()).stream().map(p -> {
				try {
					return objectMapper.readValue(new ObjectMapper().writeValueAsString(p), ProjectPayload.class);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				return null;
			}).collect(Collectors.toList()));
			responseHeader.setResponseCode(CommonConstants.SUC);
			payloadWrapper.setResponseHeader(responseHeader);
			return new ResponseEntity<>(payloadWrapper, HttpStatus.OK);
		} catch (Exception e) {
			responseHeader.setResponseCode(CommonConstants.FAL);
			e.printStackTrace();
		}
		payloadWrapper.setResponseHeader(responseHeader);
		return new ResponseEntity<>(payloadWrapper, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@DeleteMapping("")
	public ResponseEntity<Object> deleteProject(@AuthenticationPrincipal OAuth2User principal,
			@RequestParam String projectId) {

		try {
			projectService.deleteProject(projectId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
	}
}
