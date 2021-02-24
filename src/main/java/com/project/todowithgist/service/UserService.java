package com.project.todowithgist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.todowithgist.domain.User;
import com.project.todowithgist.payload.UserPayload;
import com.project.todowithgist.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User addUser(UserPayload userPayload) throws JsonMappingException, JsonProcessingException {

		User user = new User();
		ObjectMapper mapper = new ObjectMapper();
		user = mapper.readValue(new ObjectMapper().writeValueAsString(userPayload), User.class);
		return userRepository.save(user);
	}

	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

}
