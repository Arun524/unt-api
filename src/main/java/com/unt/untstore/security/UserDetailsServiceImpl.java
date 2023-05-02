package com.unt.untstore.security;

import com.unt.untstore.model.User;
import com.unt.untstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByEmailId(username);

		if (userOptional.isEmpty()) {
			userOptional = userRepository.findByEmailId(username);
		}

		if (userOptional.isPresent()) {
			return new UserDetailsImpl(userOptional.get());
		} else {
			throw new UsernameNotFoundException("Username Not Found");
		}
	}

}
