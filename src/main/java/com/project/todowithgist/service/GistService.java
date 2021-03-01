package com.project.todowithgist.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.todowithgist.domain.Project;
import com.project.todowithgist.domain.Todo;
import com.project.todowithgist.domain.User;
import com.project.todowithgist.payload.GistRequest;
import com.project.todowithgist.payload.GistResponse;
import com.project.todowithgist.repository.ProjectRepository;
import com.project.todowithgist.repository.TodoRepository;
import com.project.todowithgist.repository.UserRepository;

@Service
public class GistService {

	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	public void export(String userId, String projectId) {

		Project project = projectRepository.findByProjectId(projectId);
		List<Todo> todoList = todoRepository.findAllByProjectProjectId(projectId);

		String todoContent = buildGistFile(project.getProjectName(), todoList);

		Map<String, String> content = new HashMap<>();
		content.put("content", todoContent);
		Map<String, Map<String, String>> files = new HashMap<>();
		files.put(project.getProjectName() + ".md", content);

		GistRequest gistRequest = new GistRequest(project.getProjectName(), files);
		String auth = getAuthToken(userId);
		String gistId = "";
		if (checkIfGistExportExist(auth, project.getGistID())) {
			gistId = updateExistingGist(auth, project.getGistID(), gistRequest);
		} else {
			gistId = createGist(auth, gistRequest);
		}

		project.setGistID(gistId);
		projectRepository.save(project);

	}

	private boolean checkIfGistExportExist(String auth, String gistId) {
		try {

			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", auth);
			HttpEntity<HttpHeaders> request = new HttpEntity<HttpHeaders>(headers);
			ResponseEntity<GistResponse> response = restTemplate.exchange("https://api.github.com/gists/" + gistId,
					HttpMethod.GET, request, GistResponse.class);
			if (response.getStatusCode().equals(HttpStatus.OK)) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	private String createGist(String auth, GistRequest gistRequest) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", auth);
		HttpEntity<GistRequest> request = new HttpEntity<GistRequest>(gistRequest, headers);
		ResponseEntity<GistResponse> response = restTemplate.exchange("https://api.github.com/gists", HttpMethod.POST,
				request, GistResponse.class);
		GistResponse gitResponse = response.getBody();
		return gitResponse.getId();
	}

	private String updateExistingGist(String auth, String gistId, GistRequest gistRequest) {

		RestTemplate restTemplate = new RestTemplate();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		restTemplate.setRequestFactory(requestFactory);
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", auth);
		HttpEntity<GistRequest> request = new HttpEntity<GistRequest>(gistRequest, headers);
		ResponseEntity<GistResponse> response = restTemplate.exchange("https://api.github.com/gists/" + gistId,
				HttpMethod.PATCH, request, GistResponse.class);
		return response.getBody().getId();

	}

	private String getAuthToken(String id) {
		String token = null;
		Optional<User> user = userRepository.findById(id);
		return token = user.get().getAuthToken();
	}

	public String buildGistFile(String projectName, List<Todo> todoList) {

		StringBuilder stringBuilder = new StringBuilder();
		StringBuilder pending = new StringBuilder();
		StringBuilder completed = new StringBuilder();
		int completedCount = 0;
		int totalCount = 0;

		stringBuilder.append("# " + projectName + "\n");

		if (todoList != null) {
			totalCount = todoList.size();
			for (Todo t : todoList) {
				if (t.getTodoStatus().equals("C")) {
					completedCount++;
					completed.append("- [x] " + t.getTodoDesc() + "\n");
				} else {
					pending.append("- [ ] " + t.getTodoDesc() + "\n");
				}
			}
		}

		stringBuilder.append("**Summary:** " + completedCount + "/" + totalCount + " todos completed\n");
		if (pending.length() != 0) {
			stringBuilder.append("### Pending\n");
			stringBuilder.append(pending);
		}
		if (completed.length() != 0) {
			stringBuilder.append("### Completed\n");
			stringBuilder.append(completed);
		}

		return stringBuilder.toString();

	}
}
