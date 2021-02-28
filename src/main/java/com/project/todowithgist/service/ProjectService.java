package com.project.todowithgist.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.todowithgist.domain.Project;
import com.project.todowithgist.domain.Todo;
import com.project.todowithgist.payload.ProjectPayload;
import com.project.todowithgist.repository.ProjectRepository;
import com.project.todowithgist.repository.TodoRepository;
import com.project.todowithgist.repository.UserRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRespository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TodoRepository todoRepository;

	public Iterable<Todo> addProject(ProjectPayload projectPayload, String userId) {
		ObjectMapper mapper = new ObjectMapper();
		Project project = new Project(projectPayload.getProjectId(), null, projectPayload.getProjectName(), null);
		Project updated = userRepository.findById(userId).map(user -> {
			project.setUser(user);
			return projectRespository.save(project);
		}).orElseThrow();
		List<Todo> todoList = projectPayload.getTodoPayload().stream().map(t -> {
			try {
				Todo todoObj = mapper.readValue(new ObjectMapper().writeValueAsString(t), Todo.class);
				todoObj.setProject(updated);
				return todoObj;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}).collect(Collectors.toList());
		return todoRepository.saveAll(todoList);
	}

	public List<Project> fetchAll(String userEmail) {
		return projectRespository.findAllByUserUserId(userEmail);
	}
}
