package com.project.todowithgist.rest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.project.todowithgist.OAuthUtils;
import com.project.todowithgist.domain.Project;
import com.project.todowithgist.domain.Todo;
import com.project.todowithgist.domain.User;
import com.project.todowithgist.service.GistService;
import com.project.todowithgist.service.ProjectService;
import com.project.todowithgist.service.TodoService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(TodoAPI.class)
public class TodoAPITest {

	public static final String PROJECT_ID = "1234";

	@MockBean
	private TodoService service;

	@MockBean
	private GistService gistService;

	@MockBean
	private ProjectService projectService;

	@Autowired
	private MockMvc mockMvc;

	private OAuth2User principal;

	@BeforeEach
	public void setUp() {
		principal = OAuthUtils.createUser("tester", "tester@example.com");
		Mockito.when(service.fetchAll(PROJECT_ID)).thenReturn(populateTodoList());
	}

	@Test
	public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("projectId", PROJECT_ID);
		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/user/projects/todo").params(params).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void givenToken_whenGetSecureRequest_thenOk() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("projectId", PROJECT_ID);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/user/projects/todo")
				.with(authentication(OAuthUtils.getOauthAuthenticationFor(principal))).params(params)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void givenNoToken_whenDeleteSecureRequest_thenUnauthorized() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("todoId", PROJECT_ID);
		this.mockMvc.perform(
				MockMvcRequestBuilders.delete("/user/projects/todo").params(params).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void givenToken_whenDeleteSecureRequest_thenOk() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("todoId", PROJECT_ID);
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/user/projects/todo")
				.with(authentication(OAuthUtils.getOauthAuthenticationFor(principal))).params(params)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	private List<Todo> populateTodoList() {
		User user = new User("213456", "myusername");
		Project project = new Project("p1234", user, "Projectname", "");
		Todo payload1 = new Todo("t1234", project, "Todo1", "C");
		Todo payload2 = new Todo("t1235", project, "Todo2", "P");
		return new ArrayList<Todo>(java.util.Arrays.asList(payload1, payload2));
	}

	public String obtainAccessToken(String userName, String password) throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "password");
		params.add("client_id", "fooClientIdPassword");
		params.add("username", userName);
		params.add("password", password);

		ResultActions result = this.mockMvc.perform(post("/oauth/token").params(params)
				.with(httpBasic("fooClientIdPassword", "secret")).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		String resultString = result.andReturn().getResponse().getContentAsString();

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("access_token").toString();
	}

}
