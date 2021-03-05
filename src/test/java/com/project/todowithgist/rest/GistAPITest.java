package com.project.todowithgist.rest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.todowithgist.OAuthUtils;
import com.project.todowithgist.service.GistService;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(GistAPI.class)
public class GistAPITest {

	@MockBean
	private GistService service;

	@Autowired
	private MockMvc mockMvc;

	private OAuth2User principal;

	public static final String PROJECT_ID = "p1234";

	@BeforeEach
	public void setUp() {
		principal = OAuthUtils.createUser("tester", "tester@example.com");

	}

	@Test
	public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("projectId", PROJECT_ID);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/user/gist").params(params).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void givenToken_whenGetSecureRequest_thenAuthorized() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("projectId", PROJECT_ID);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/user/gist")
				.with(authentication(OAuthUtils.getOauthAuthenticationFor(principal))).params(params)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
}
