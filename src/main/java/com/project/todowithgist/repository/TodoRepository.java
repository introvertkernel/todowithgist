package com.project.todowithgist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.todowithgist.domain.Todo;

@Repository
public interface TodoRepository extends CrudRepository<Todo, String> {

//	@EntityGraph(value = "Project.projectId", type = EntityGraphType.FETCH)
	List<Todo> findAllByProjectProjectId(String projectId);
}
