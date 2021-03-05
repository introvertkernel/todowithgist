package com.project.todowithgist.rest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.todowithgist.OAuthUtils;
import com.project.todowithgist.domain.Project;
import com.project.todowithgist.domain.Todo;
import com.project.todowithgist.domain.User;
import com.project.todowithgist.payload.ProjectPayload;
import com.project.todowithgist.payload.TodoPayload;
import com.project.todowithgist.service.ProjectService;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(ProjectAPI.class)
public class ProjectAPITest {

	private static ProjectPayload payload;

	@MockBean
	private ProjectService projectService;

	@Autowired
	private MockMvc mockMvc;

	private OAuth2User principal;

	private static final String JSON_STR = "{\"projectId\":\"p1234\",\"projectName\":\"Test project\",\"todoPayload\":[{\"todoId\":\"t1234\",\"todoDesc\":\"Todo1\",\"todoStatus\":\"C\",\"createTs\":\"05-03-2021\"},{\"todoId\":\"t1235\",\"todoDesc\":\"Todo2\",\"todoStatus\":\"P\",\"createTs\":\"05-03-2021\"}]}";

	@BeforeAll
	public static void init() {
		TodoPayload payload1 = new TodoPayload("t1234", "Todo1", "C", new Date().from(Instant.now()));
		TodoPayload payload2 = new TodoPayload("t1235", "Todo2", "P", new Date().from(Instant.now()));

		payload = new ProjectPayload("p1234", "Test project",
				new ArrayList<>(java.util.Arrays.asList(payload1, payload2)));
	}

	@BeforeEach
	public void setUp() throws Exception {
		principal = OAuthUtils.createUser("tester", "tester@example.com");

		Mockito.when(projectService.addProject(payload, "1234567890")).thenReturn(populateTodo());
		Mockito.when(projectService.addProject(payload, "1234")).thenThrow(UnsupportedOperationException.class);
		Mockito.when(projectService.fetchAll("1234567890")).thenReturn(populateProjectList());
		Mockito.when(projectService.fetchAll("1234")).thenThrow(NullPointerException.class);
	}

	@Test
	public void givenNoToken_whenPostSecureRequest_thenUnauthorized() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/user/projects/add")
						.content(new ObjectMapper().writeValueAsString(payload)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void givenToken_whenPostSecureRequest_thenAuthorized() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(payload));
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/user/projects/add")
						.with(authentication(OAuthUtils.getOauthAuthenticationFor(principal)))
						.contentType(MediaType.APPLICATION_JSON).content(JSON_STR).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void givenToken_whenPostSecureRequest_thenAuthorizedAndException() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(payload));
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/user/projects/add")
						.with(authentication(OAuthUtils.getOauthAuthenticationFor(
								OAuthUtils.createUser("1234", "tester@example.com", "1234"))))
						.contentType(MediaType.APPLICATION_JSON).content(JSON_STR).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/user/projects").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void givenToken_whenGetSecureRequest_thenAuthorized() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/user/projects")
				.with(authentication(OAuthUtils.getOauthAuthenticationFor(principal)))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void givenToken_whenGetSecureRequest_thenAuthorizedAndException() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/user/projects")
				.with(authentication(OAuthUtils
						.getOauthAuthenticationFor(OAuthUtils.createUser("1234", "tester@example.com", "1234"))))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
	}

	@Test
	public void givenNoToken_whenDeleteSecureRequest_thenUnauthorized() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("projectId", "p1234");
		this.mockMvc.perform(
				MockMvcRequestBuilders.delete("/api/user/projects").params(params).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void givenToken_whenDeleteSecureRequest_thenAuthorized() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("projectId", "p1234");
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/projects")
				.with(authentication(OAuthUtils.getOauthAuthenticationFor(principal))).params(params)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	private Iterable<Todo> populateTodo() {
		User user = new User("213456", "myusername");
		Project project = new Project("p1234", user, "Projectname", "");
		Todo payload1 = new Todo("t1234", project, "Todo1", "C");
		Todo payload2 = new Todo("t1235", project, "Todo2", "P");
		return new ArrayList<Todo>(java.util.Arrays.asList(payload1, payload2));

	}

	private List<Project> populateProjectList() {
		User user = new User("213456", "myusername");
		Project project1 = new Project("p1234", user, "Projectname", "");
		Project project2 = new Project("p1235", user, "Projectname", "");
		return Arrays.asList(project1, project2);
	}
}
