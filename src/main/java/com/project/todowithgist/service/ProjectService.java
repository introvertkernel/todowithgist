package com.project.todowithgist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.todowithgist.domain.Project;
import com.project.todowithgist.payload.ProjectPayload;
import com.project.todowithgist.repository.ProjectRepository;
import com.project.todowithgist.repository.UserRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRespository;

	@Autowired
	private UserRepository userRepository;

	public Project addProject(ProjectPayload projectPayload, String userEmail) {
		Project project = new Project(null, null, projectPayload.getProjectName(),
				projectPayload.getProjectDescription());
		return userRepository.findById(userEmail).map(user -> {
			project.setUser(user);
			return projectRespository.save(project);
		}).orElseThrow();
	}

	public List<Project> fetchAll(String userEmail) {
		return projectRespository.findAllByUserUserId(userEmail);
	}
}
