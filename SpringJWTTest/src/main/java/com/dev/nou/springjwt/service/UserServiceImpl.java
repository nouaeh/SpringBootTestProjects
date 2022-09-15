package com.dev.nou.springjwt.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dev.nou.springjwt.entity.User;
import com.dev.nou.springjwt.repo.UserRepository;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> existingUser=userRepo.findByUsername(username);
		org.springframework.security.core.userdetails.User springUser=null;
		
		if(existingUser.isEmpty()) {
			throw new UsernameNotFoundException("User "+username+" does not exist");
		}else {
			User user=existingUser.get();
			Set<String> roles=user.getRoles();
			Set<GrantedAuthority> userAuthorities=new HashSet<>();
			for(String role:roles) {
				userAuthorities.add(new SimpleGrantedAuthority(role));
			}
			springUser=new org.springframework.security.core.userdetails.User(username, user.getPassword(), userAuthorities);
		}
		return springUser;
	}

	@Override
	public Integer saveUser(User user) {
		// Encode password and save to DB
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepo.save(user).getId();
	}

	@Override
	public Optional<User> findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepo.findByUsername(username);
	}

}
