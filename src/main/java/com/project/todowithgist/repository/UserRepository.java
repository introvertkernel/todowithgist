package com.project.todowithgist.repository;

import org.springframework.data.repository.CrudRepository;

import com.project.todowithgist.domain.User;

public interface UserRepository extends CrudRepository<User, String>{

}
