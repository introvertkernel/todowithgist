package com.project.todowithgist.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.todowithgist.domain.User;
import com.project.todowithgist.payload.ResponseHeader;
import com.project.todowithgist.payload.UserPayload;
import com.project.todowithgist.service.UserService;
import com.project.todowithgist.utils.CommonConstants;

@RestController
@RequestMapping("/user")
public class UserAPI {

	@Autowired
	private UserService userService;

//	@Autowired
//	private String AuthenticationDetails;

	@PostMapping("/add")
	public ResponseEntity<ResponseHeader> addUser(@AuthenticationPrincipal OAuth2User principal, @RequestBody UserPayload userPayload) {
		ResponseHeader responseHeader = new ResponseHeader();
		try {
			userPayload.setUserName(principal.getName());
			userService.addUser(userPayload);
			responseHeader.setResponseCode(CommonConstants.SUC);
		} catch (Exception e) {
			e.printStackTrace();
			responseHeader.setResponseCode(CommonConstants.FAL);
		}
		return new ResponseEntity<>(responseHeader, HttpStatus.OK);
	}

	@GetMapping("/")
	public List<User> fetchAll(@AuthenticationPrincipal OAuth2User principal) {
		return userService.findAll();
	}
}
