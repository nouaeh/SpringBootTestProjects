package com.dev.nou.springjwt.service;

import java.util.Optional;

import com.dev.nou.springjwt.entity.User;

public interface IUserService {

	Integer saveUser(User user);
	Optional<User> findByUsername(String username);
}
