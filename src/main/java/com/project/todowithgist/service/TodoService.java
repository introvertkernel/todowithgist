package com.project.todowithgist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.todowithgist.domain.Todo;
import com.project.todowithgist.payload.TodoPayload;
import com.project.todowithgist.repository.ProjectRepository;
import com.project.todowithgist.repository.TodoRepository;

@Service
public class TodoService {

	@Autowired
	private TodoRepository todoRepository;
	
	@Autowired
	private ProjectRepository projectRepository;

	public Todo addTodo(TodoPayload todoPayload, String projectId) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Todo todo = mapper.readValue(new ObjectMapper().writeValueAsString(todoPayload), Todo.class);
		return projectRepository.findById(projectId).map( project -> {
			todo.setProject(project);
			return todoRepository.save(todo);
		}).orElseThrow();
	}

	public List<Todo> fetchAll(String projectId) {
		return todoRepository.findAllByProjectProjectId(projectId);
	}

}
