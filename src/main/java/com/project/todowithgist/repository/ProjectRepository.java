package com.project.todowithgist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.todowithgist.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, String> {

	List<Project> findAllByUserUserId(String userId);
}
