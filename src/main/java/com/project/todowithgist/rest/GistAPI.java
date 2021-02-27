package com.project.todowithgist.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.todowithgist.payload.ResponseHeader;
import com.project.todowithgist.service.GistService;
import com.project.todowithgist.utils.CommonConstants;

@RestController
@RequestMapping("/user/gist")
public class GistAPI {

	@Autowired
	private GistService gistService;

	@GetMapping("/{projectId}")
	public ResponseEntity<ResponseHeader> exportAsGist(@AuthenticationPrincipal OAuth2User principal,
			@RequestParam String projectId) {
		ResponseHeader responseHeader = new ResponseHeader();
		try {
			gistService.export(principal.getName(), projectId);
			responseHeader.setResponseCode(CommonConstants.SUC);
		} catch (Exception e) {
			e.printStackTrace();
			responseHeader.setResponseCode(CommonConstants.FAL);
		}
		return new ResponseEntity<>(responseHeader, HttpStatus.OK);
	}

}
