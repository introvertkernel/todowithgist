package com.project.todowithgist.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.todowithgist.domain.Project;
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
		} catch (Exception e) {
			e.printStackTrace();
			responseHeader.setResponseCode(CommonConstants.FAL);
		}
		return new ResponseEntity<>(responseHeader, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/")
	public ResponseEntity<ProjectPayloadWrapper> getAllProjects(@AuthenticationPrincipal OAuth2User principal) {
		ResponseHeader responseHeader = new ResponseHeader();
		ProjectPayloadWrapper payloadWrapper = new ProjectPayloadWrapper();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			payloadWrapper.setProjectPayload((List<Project>) objectMapper.readValue(
					new ObjectMapper().writeValueAsString(projectService.fetchAll(principal.getName())), ProjectPayload.class));
			responseHeader.setResponseCode(CommonConstants.SUC);
		} catch (Exception e) {
			responseHeader.setResponseCode(CommonConstants.SUC);
			e.printStackTrace();
		}
		payloadWrapper.setResponseHeader(responseHeader);
		return new ResponseEntity<>(payloadWrapper, HttpStatus.OK);

	}
}
